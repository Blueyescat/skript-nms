package com.furkannm.skriptnms.util.nms;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.types.BlockPosition;
import com.furkannm.skriptnms.util.nms.types.CraftEntity;
import com.furkannm.skriptnms.util.nms.types.CraftItemStack;
import com.furkannm.skriptnms.util.nms.types.CraftWorld;
import com.furkannm.skriptnms.util.nms.types.DatFile;
import com.furkannm.skriptnms.util.nms.types.IBlockData;
import com.furkannm.skriptnms.util.nms.types.MojangsonParser;
import com.furkannm.skriptnms.util.nms.types.NBTCompressedStreamTools;
import com.furkannm.skriptnms.util.nms.types.NBTTagByte;
import com.furkannm.skriptnms.util.nms.types.NBTTagDouble;
import com.furkannm.skriptnms.util.nms.types.NBTTagFloat;
import com.furkannm.skriptnms.util.nms.types.NBTTagInt;
import com.furkannm.skriptnms.util.nms.types.NBTTagLong;
import com.furkannm.skriptnms.util.nms.types.NBTTagShort;
import com.furkannm.skriptnms.util.nms.types.NBTTagString;
import com.furkannm.skriptnms.util.nms.types.TileEntity;
import com.furkannm.skriptnms.util.nms.types.World;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NMS {
	
	public static Object getNBTTag(Object compound, String tag) {
		try {
			Class<?> NbtCompound = NBTTagCompound.get();
			
			if (NbtCompound.isInstance(compound)) {
				Method m = (NbtCompound.cast(compound)).getClass().getMethod("get", String.class);
				return m.invoke(compound,tag);
			}
		} catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			Skript.warning("Error when get tag NBT - " + e.getMessage());
			return null;
		}
		return null;
	}
	
	public static void setNBTTag(Object compound, String tag, Object toSet) {
		Class<?> NbtCompound = NBTTagCompound.get();
		Class<?> NbtBase = NBTBase.get();
		if (NbtCompound.isInstance(compound) && (NbtBase.isInstance(toSet) || toSet instanceof Number || toSet instanceof String)) {
			Object converted = null;
			if (toSet instanceof Number) {
				converted = NBTBase.get().cast(convertToNBT((Number) toSet));
			} else if (toSet instanceof String) {
				converted = NBTBase.get().cast(convertToNBT((String) toSet));
			} else { 
				converted = NBTBase.get().cast(toSet); 
			}
			try{
				Method m =(NBTTagCompound.get().cast(compound)).getClass().getMethod("set", String.class,NBTBase.get());
				m.invoke(compound,tag, converted);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				Skript.warning("Error when set tag NBT - " + e.getMessage());
			}
		}
	}
	
	public static void removeNBTTag(Object compound, String tag) {
		if (NBTTagCompound.get().isInstance(compound)) {
			try {
				Method m =(NBTTagCompound.get().cast(compound)).getClass().getMethod("remove", String.class);			
				m.invoke(compound,tag);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Skript.warning("Error when remove tag NBT - " + e.getMessage());
			}
		}
	}
	
	public static void addToCompound(Object compound, Object toAdd) {
		Class NBTTagComp = NBTTagCompound.get();
		if (NBTTagComp.isInstance(compound) && NBTTagComp.isInstance(toAdd)) {
			try {
				Method m =(NBTTagComp.cast(compound)).getClass().getMethod("a", NBTTagComp);			
				m.invoke(compound,NBTTagCompound.get().cast(toAdd));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Skript.warning("Error when add to compound NBT - " + e.getMessage());
			}
		}
	}
	
	public static void removeFromCompound(Object compound, String ... toRemove) {
		Class NBTTagComp = NBTTagCompound.get();
		if (NBTTagComp.isInstance(compound)) {
			try {
				for (String s : toRemove) {		
					Bukkit.getServer().broadcastMessage(s);
					Method m =(NBTTagComp.cast(compound)).getClass().getMethod("remove", String.class);			
					m.invoke(NBTTagComp.cast(compound),s);
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				Skript.warning("Error when remove from compound NBT - " + e.getMessage());
			}
		}
	}
	
	//NBTTagCompound
	public static Object getTileNBT(Block block) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			Class world = World.get();
			Class craftWorld = CraftWorld.get();
			Class tileEnt = TileEntity.get();
			Class blockPos = BlockPosition.get();
			Object NBT = NBTTagComp.getConstructor().newInstance();
			/*Object nmsWorld = craftWorld.cast(block.getWorld()).getClass().getMethod("getHandle").invoke(craftWorld.cast(block.getWorld()));
			Object tileEntity = tileEnt.cast(nmsWorld.getClass().getMethod("getTileEntity", blockPos).invoke(nmsWorld,blockPos.getConstructor(double.class,double.class,double.class).newInstance(block.getX(), block.getY(), block.getZ())));*/
			Object nmsWorld = craftWorld.getMethod("getHandle").invoke(craftWorld.cast(block.getWorld()));
			Object tileEntity = tileEnt.cast(world.getMethod("getTileEntity", blockPos).invoke(nmsWorld, blockPos.getConstructor(int.class,int.class,int.class).newInstance(block.getX(),block.getY(),block.getZ())));
			if (tileEntity == null)
				return null;
			if(Core.getVer().startsWith("v1_8")) {
				tileEnt.cast(tileEntity).getClass().getMethod("b", NBTTagComp).invoke(tileEntity,NBTTagComp.cast(NBT));
			}else{
				tileEnt.cast(tileEntity).getClass().getMethod("save", NBTTagComp).invoke(tileEntity,NBTTagComp.cast(NBT));
			}
			return NBT;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			Skript.warning("Error when getting tile NBT - " + e.getMessage());
			return null;
		}
	}
	
	//NBTTagCompound
	public static Object getEntityNBT(Entity entity) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			Class ent = com.furkannm.skriptnms.util.nms.types.Entity.get();
			Class CraftEnt = CraftEntity.get();
			Object nmsEntity = ent.cast(CraftEnt.cast(entity).getClass().getMethod("getHandle").invoke(entity));
			Object NBT = NBTTagComp.cast(NBTTagComp.getConstructor().newInstance());
			if(Core.getVer().startsWith("v1_8")) {
				ent.cast(nmsEntity).getClass().getMethod("e", NBTTagComp).invoke(nmsEntity,NBTTagComp.cast(NBT));
			}else{
				ent.cast(nmsEntity).getClass().getMethod("c", NBTTagComp).invoke(nmsEntity,NBTTagComp.cast(NBT));
			}
			return NBT;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			Skript.warning("Error when getting entity NBT - " + e.getMessage());
			return null;
		}
	}
	
	//NBTTagCompound
	public static Object getItemNBT(ItemStack itemStack) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			Class CraftIS = CraftItemStack.get();
			Class IS = com.furkannm.skriptnms.util.nms.types.ItemStack.get();
			if (itemStack == null || itemStack.getType() == Material.AIR)
				return null;
			Object item = IS.cast(CraftIS.getMethod("asNMSCopy", ItemStack.class).invoke(itemStack,itemStack));
			Object itemNBT = NBTTagComp.cast(IS.cast(item).getClass().getMethod("getTag").invoke(item));
			if (itemNBT == null)
				itemNBT = NBTTagComp.cast(NBTTagComp.getConstructor().newInstance());
			return itemNBT;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			Skript.warning("Error when getting item NBT - " + e.getMessage());
			return null;
		}
	}

	//NBTTagCompound
	public static Object parseRawNBT(String rawNBT) {
		Object parsedNBT = null;
		try {
			Class MojangsonPrsr = MojangsonParser.get();
			parsedNBT = MojangsonPrsr.getMethod("parse", String.class).invoke(new NMS(), rawNBT);
		} catch (Exception e) {
			Skript.warning("Error when parsing NBT - " + e.getMessage());
			return null;
		}
		return parsedNBT;
	}
	
	public static void setTileNBT(Block block, Object newCompound) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			if (NBTTagComp.isInstance(newCompound)) {
				Class world = World.get();
				Class craftWorld = CraftWorld.get();
				Class tileEnt = TileEntity.get();
				Class blockPos = BlockPosition.get();
				Class BlockData = IBlockData.get();
				Object nmsWorld = craftWorld.getMethod("getHandle").invoke(craftWorld.cast(block.getWorld()));
				Object tileEntity = tileEnt.cast(world.getMethod("getTileEntity", blockPos).invoke(nmsWorld, blockPos.getConstructor(int.class,int.class,int.class).newInstance(block.getX(),block.getY(),block.getZ())));
				//Object nmsWorld = wrld.cast(CraftWorld.get().cast(block.getWorld()).getClass().getMethod("getHandle").invoke(block.getWorld()));
				//Object tileEntity = tileEnt.cast(wrld.cast(nmsWorld).getClass().getMethod("getTileEntity", BlockPosition.get()).invoke(nmsWorld,BlockPosition.get().getConstructor(Integer.class,Integer.class,Integer.class).newInstance(block.getX(), block.getY(), block.getZ())));
				if (tileEntity == null)
					return;
				if(Core.getVer().startsWith("v1_12")) {
					tileEntity.getClass().getMethod("load", NBTTagComp).invoke(tileEntity,NBTTagComp.cast(newCompound));
					tileEntity.getClass().getMethod("update").invoke(tileEntity);
					Object iblockdata = world.cast(nmsWorld).getClass().getMethod("getType", blockPos).invoke(world.cast(nmsWorld), blockPos.getConstructor(int.class,int.class,int.class).newInstance(block.getX(),block.getY(),block.getZ()));
					world.cast(nmsWorld).getClass().getMethod("notify", blockPos, BlockData, BlockData, int.class).invoke(world.cast(nmsWorld),tileEntity.getClass().getMethod("getPosition").invoke(tileEntity), BlockData.cast(iblockdata), BlockData.cast(iblockdata), 3);
				}else{
					tileEntity.getClass().getMethod("a", NBTTagComp).invoke(tileEntity,NBTTagComp.cast(newCompound));
					tileEntity.getClass().getMethod("update").invoke(tileEntity,tileEnt.cast(tileEntity));
					world.cast(nmsWorld).getClass().getMethod("notify", tileEntity.getClass()).invoke(nmsWorld,tileEntity.getClass().getMethod("getPosition").invoke(tileEntity,tileEnt.cast(tileEntity)));
					
				}
				
				
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			Skript.warning("Error when setting tile NBT - " + e.getMessage());
		}
	}

	//ItemStack
	public static ItemStack getItemWithNBT(ItemStack itemStack, Object compound) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			Class is = com.furkannm.skriptnms.util.nms.types.ItemStack.get();
			Class CraftIS = CraftItemStack.get();
			Object nmsItem = is.cast(CraftIS.getMethod("asNMSCopy", ItemStack.class).invoke(itemStack,itemStack));
			if (NBTTagComp.isInstance(compound) && itemStack != null) {
				if (itemStack.getType() == Material.AIR)
					return null;
				if ((boolean) (NBTTagComp.cast(compound).getClass().getMethod("isEmpty").invoke(compound)))
					return itemStack;
				is.cast(nmsItem).getClass().getMethod("setTag", NBTTagCompound.get()).invoke(nmsItem,NBTTagComp.cast(compound));
				ItemStack newItem = (ItemStack) CraftItemStack.get().getMethod("asBukkitCopy", is).invoke(nmsItem,is.cast(nmsItem));
				return newItem;
			} else if (compound == null) {
				is.cast(nmsItem).getClass().getMethod("setTag", NBTTagComp).invoke(nmsItem,NBTTagComp.cast(null));
				ItemStack newItem = (ItemStack) CraftItemStack.get().getMethod("asBukkitCopy", is).invoke(nmsItem,is.cast(nmsItem));
				return newItem;
			}
			return itemStack;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			Skript.warning("Error when getting item NBT - " + e.getMessage());
			return null;
		}
	}
	
	public static void setEntityNBT(Entity entity, Object newCompound) {
		try{
			Class NBTTagComp = NBTTagCompound.get();
			Class ent = com.furkannm.skriptnms.util.nms.types.Entity.get();
			if (NBTTagComp.isInstance(newCompound)) {
				Object nmsEntity = ent.cast(CraftEntity.get().cast(entity).getClass().getMethod("getHandle").invoke(entity));
				ent.cast(nmsEntity).getClass().getMethod("f", NBTTagCompound.get()).invoke(nmsEntity,NBTTagCompound.get().cast(newCompound));				
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			Skript.warning("Error when setting entity NBT - " + e.getMessage());
		}
	}
	
	public static Object convertToNBT(Number number) {
		try {
			if (number instanceof Byte) {
				Class nbttagbyte = NBTTagByte.get();
				Constructor c;	
				c = nbttagbyte.getConstructor(Byte.class);
				return c.newInstance(number);
			} else if (number instanceof Short) {
				Class nbttagbyte = NBTTagShort.get();
				Constructor c = nbttagbyte.getConstructor(Short.class);
				return c.newInstance(number);
			} else if (number instanceof Integer) {
				Class nbttagbyte = NBTTagInt.get();
				Constructor c = nbttagbyte.getConstructor(Integer.class);
				return c.newInstance(number);
			} else if (number instanceof Long) {
				Class nbttagbyte = NBTTagLong.get();
				Constructor c = nbttagbyte.getConstructor(Long.class);
				return c.newInstance(number);
			} else if (number instanceof Float) {
				Class nbttagbyte = NBTTagFloat.get();
				Constructor c = nbttagbyte.getConstructor(Float.class);
				return c.newInstance(number);
			} else if (number instanceof Double) {
				Class nbttagbyte = NBTTagDouble.get();
				Constructor c = nbttagbyte.getConstructor(Double.class);
				return c.newInstance(number);
			}	
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Skript.warning("Error when converting NBT - " + e.getMessage());
			return null;
		}
		return null;
	}
	public static Object convertToNBT(String string) {
		try{
			Class nbttagbyte = NBTTagString.get();
			Constructor c = nbttagbyte.getConstructor(String.class);
			return (NBTTagString.get().cast(c.newInstance(string)));
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Skript.warning("Error when converting NBT - " + e.getMessage());
			return null;
		}
	}
	
	public static void loadFileNbt(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			Skript.warning("Error when loading file NBT - " + e.getMessage());
		}
		Object fileNBT = null;
		try {
			fileNBT = NBTCompressedStreamTools.get().getMethod("a", InputStream.class).invoke(NBTCompressedStreamTools.get(),fis);
			fis.close();
		} catch (IOException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
			if (ex instanceof EOFException) {
				;
			} else {
				ex.printStackTrace();
			}
		}
		DatFile.setLastData(file, fileNBT);
	}
	
	public static void setFileNBT(File file, Object newCompound) {
		if (NBTTagCompound.get().isInstance(newCompound)) {
			OutputStream os = null;
			try {
				os = new FileOutputStream(file);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			try {
				NBTCompressedStreamTools.get().getMethod("a", NBTTagCompound.get(), OutputStream.class).invoke(NBTCompressedStreamTools.get(),NBTTagCompound.get().cast(newCompound), os);
				os.close();
			} catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				if (!(e instanceof EOFException)) {
					e.printStackTrace();
				}
			}
		}
	}

}
