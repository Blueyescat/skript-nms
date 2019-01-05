package com.furkannm.skriptnms.nms.versions;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import net.minecraft.server.v1_10_R1.BlockPosition;
import net.minecraft.server.v1_10_R1.IBlockData;
import net.minecraft.server.v1_10_R1.MojangsonParser;
import net.minecraft.server.v1_10_R1.NBTBase;
import net.minecraft.server.v1_10_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_10_R1.NBTTagByte;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagDouble;
import net.minecraft.server.v1_10_R1.NBTTagFloat;
import net.minecraft.server.v1_10_R1.NBTTagInt;
import net.minecraft.server.v1_10_R1.NBTTagLong;
import net.minecraft.server.v1_10_R1.NBTTagShort;
import net.minecraft.server.v1_10_R1.NBTTagString;
import net.minecraft.server.v1_10_R1.TileEntity;
import net.minecraft.server.v1_10_R1.World;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.util.slot.Slot;

import com.furkannm.skriptnms.nms.DatFile;
import com.furkannm.skriptnms.nms.NMS;

public class v1_10_R1 extends NMS{

	@Override
	public Object[] getNBT(Object tar) {
		if (tar instanceof Entity) {
			return new Object[] { (NBTTagCompound) getEntityNBT((Entity) tar) };
		}else if (tar instanceof Block) {
			return new Object[] { (NBTTagCompound) getTileNBT((Block) tar) };
		}else if (tar instanceof ItemStack) {
			return new Object[] { (NBTTagCompound) getItemNBT((ItemStack) tar) };
		}else if (tar instanceof Slot) {
			return new Object[] { (NBTTagCompound) getItemNBT(((Slot) tar).getItem()) };
		}
		return null;
	}

	@Override
	public void addTargetsNBT(Object tar, Object nbt) {
		if (tar instanceof Entity) {
			Object entNBT = (NBTTagCompound) getEntityNBT((Entity) tar);
			removeFromCompound((NBTTagCompound) nbt, "UUIDMost", "UUIDLeast", "WorldUUDMost", "WorldUUIDLeast", "Bukkit.updateLevel");
			addToCompound(entNBT, (NBTTagCompound) nbt);
			setEntityNBT((Entity) tar, entNBT);
		}else if (tar instanceof Block) {
			Object blockNBT = (NBTTagCompound) getTileNBT((Block) tar);
			removeFromCompound((NBTTagCompound) nbt, "x", "y", "z", "id");
			addToCompound(blockNBT, (NBTTagCompound) nbt);
			setTileNBT((Block) tar, blockNBT);
		}else if (tar instanceof ItemStack) {
			Skript.warning("Failed to change the NBT of an item: Itemstack didn't have any slot attached to it.");
		}else if (tar instanceof Slot) {
			ItemStack slotItem = ((Slot) tar).getItem();
			Object itemNBT = (NBTTagCompound) getItemNBT(slotItem);
			addToCompound(itemNBT, (NBTTagCompound) nbt);
			ItemStack newItem = getItemWithNBT(slotItem, itemNBT);
			((Slot) tar).setItem(newItem);
		}
	}

	@Override
	public void removeTargetsNBT(Object tar, Object[] nbt) {
		if (tar instanceof Entity) {
			Object entNBT = (NBTTagCompound) (getEntityNBT((Entity) tar));
			for (Object s : nbt) {
				if (s != "UUIDMost" || s != "UUIDLeast" || s != "WorldUUIDMost" || s != "WorldUUIDLeast" || s != "Bukkit.updateLevel") { 
					removeFromCompound((NBTTagCompound) (entNBT), (String) s);
				}
			}
			setEntityNBT((Entity) tar, (NBTTagCompound) (entNBT));
		}else if (tar instanceof Block) {
			Object blockNBT = (NBTTagCompound) (getTileNBT((Block) tar));
			for (Object s : nbt) {
				if (s != "x" || s != "y" || s != "z" || s != "id") {
					removeFromCompound((NBTTagCompound) (blockNBT), (String) s);
				}
			}
			setTileNBT((Block) tar, (NBTTagCompound) (blockNBT));
		}else if (tar instanceof ItemStack) {
			Skript.warning("Failed to change the NBT of an item: Itemstack didn't have any slot attached to it.");
		}else if (tar instanceof Slot) {
			ItemStack slotItem = ((Slot) tar).getItem();
			Object itemNBT = (NBTTagCompound) (getItemNBT(slotItem));
			String[] toRemove = Arrays.copyOf(nbt, nbt.length, String[].class);
			removeFromCompound(itemNBT, toRemove);
			ItemStack newItem = getItemWithNBT(slotItem, (NBTTagCompound) (itemNBT));
			((Slot) tar).setItem(newItem);
		}
	}

	@Override
	public void deleteTargetsNBT(Object tar) {
		if (!(tar instanceof Slot)) return;
		ItemStack slotItem = ((Slot) tar).getItem();
		ItemStack newItem = getItemWithNBT(slotItem, (NBTTagCompound) (null));
		((Slot) tar).setItem(newItem);		
	}

	@Override
	public Object getNBTTag(Object compound, String tag) {
		if(compound instanceof NBTTagCompound) {
			return ((NBTTagCompound) compound).get(tag);
		}
		return null;
	}

	@Override
	public void setNBTTag(Object compound, String tag, Object toSet) {
		if(compound instanceof NBTTagCompound) {
			NBTBase base = null;
			if(toSet instanceof Number) {
				base = (NBTBase) convertToNBT((Number) toSet);
			}else if(toSet instanceof String) {
				base = (NBTBase) convertToNBT((String) toSet);
			}else{
				base = (NBTBase) toSet;
			}
			((NBTTagCompound) compound).set(tag, base);
		}
	}

	@Override
	public void removeNBTTag(Object compound, String tag) {
		if (compound instanceof NBTTagCompound) {
			((NBTTagCompound) compound).remove(tag);			
		}
	}

	@Override
	public void addToCompound(Object compound, Object toAdd) {
		if (compound instanceof NBTTagCompound && toAdd instanceof NBTTagCompound) {
			((NBTTagCompound) (compound)).a((NBTTagCompound) toAdd);			
		}
	}

	@Override
	public void removeFromCompound(Object compound, String... toRemove) {
		if (compound instanceof NBTTagCompound) {
			for (String s : toRemove) {		
				((NBTTagCompound) (compound)).remove(s);	
			}		
		}
	}

	@Override
	public Object getTileNBT(Block block) {
		NBTTagCompound NBT = new NBTTagCompound();
		World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
		TileEntity tileEntity = nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
		if (tileEntity == null)
			return null;
		tileEntity.save(NBT);
		return NBT;
	}

	@Override
	public Object getEntityNBT(Entity entity) {
		net.minecraft.server.v1_10_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound NBT = new NBTTagCompound();
		nmsEntity.c(NBT);
		return NBT;
	}

	@Override
	public Object getItemNBT(ItemStack itemStack) {
		if (itemStack == null || itemStack.getType() == Material.AIR)
			return null;
		NBTTagCompound itemNBT = CraftItemStack.asNMSCopy(itemStack).getTag();
		if (itemNBT == null)
			itemNBT = new NBTTagCompound();
		return itemNBT;
	}

	@Override
	public Object parseRawNBT(String rawNBT) {
		NBTTagCompound parsedNBT = null;
		try {
			parsedNBT = MojangsonParser.parse(rawNBT);
		} catch (Exception e) {
			Skript.warning("Error when parsing NBT - " + e.getMessage());
			return null;
		}
		return parsedNBT;
	}

	@Override
	public void setTileNBT(Block block, Object newCompound) {
		if (newCompound instanceof NBTTagCompound) {
			World nmsWorld = ((CraftWorld) block.getWorld()).getHandle();
			TileEntity tileEntity = nmsWorld.getTileEntity(new BlockPosition(block.getX(), block.getY(), block.getZ()));
			if (tileEntity == null)
				return;
			tileEntity.a((NBTTagCompound) newCompound);
			tileEntity.update();
			IBlockData tileEntType = nmsWorld.getType(new BlockPosition(block.getX(),block.getY(),block.getZ()));
			nmsWorld.notify(tileEntity.getPosition(), tileEntType, tileEntType, 3);
		}
	}

	@Override
	public ItemStack getItemWithNBT(ItemStack itemStack, Object compound) {
		net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
		if (compound instanceof NBTTagCompound && itemStack != null) {
			if (itemStack.getType() == Material.AIR)
				return null;
			if (((NBTTagCompound) compound).isEmpty())
				return itemStack;
			nmsItem.setTag((NBTTagCompound) compound);
			ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
			return newItem;
		} else if (compound == null) {
			nmsItem.setTag(null);
			ItemStack newItem = CraftItemStack.asBukkitCopy(nmsItem);
			return newItem;
		}
		return itemStack;
	}

	@Override
	public void setEntityNBT(Entity entity, Object newCompound) {
		if (newCompound instanceof NBTTagCompound) {
			net.minecraft.server.v1_10_R1.Entity nmsEntity = ((CraftEntity) entity).getHandle();
			nmsEntity.f((NBTTagCompound) newCompound);
		}
	}

	@Override
	public Object convertToNBT(Number number) {
		if (number instanceof Byte) {
			return new NBTTagByte((Byte) number);
		} else if (number instanceof Short) {
			return new NBTTagShort((Short) number);
		} else if (number instanceof Integer) {
			return new NBTTagInt((Integer) number);
		} else if (number instanceof Long) {
			return new NBTTagLong((Long) number);
		} else if (number instanceof Float) {
			return new NBTTagFloat((Float) number);
		} else if (number instanceof Double) {
			return new NBTTagDouble((Double) number);
		}	
		return null;
	}

	@Override
	public Object convertToNBT(String string) {
		return new NBTTagString(string);
	}

	@Override
	public void loadFileNbt(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			Skript.warning("Error when loading file NBT - " + e.getMessage());
		}
		Object fileNBT = null;
		try {
			fileNBT = NBTCompressedStreamTools.a(fis);
			fis.close();
		} catch (IOException ex) {
			if (ex instanceof EOFException) {
				;
			} else {
				ex.printStackTrace();
			}
		}
		DatFile.loadDatFile(file, fileNBT);
	}

	@Override
	public void setFileNBT(File file, Object newCompound) {
		if (newCompound instanceof NBTTagCompound) {
			OutputStream os = null;
			try {
				os = new FileOutputStream(file);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			try {
				NBTCompressedStreamTools.a((NBTTagCompound) newCompound,os);
				os.close();
			} catch (IOException | IllegalArgumentException | SecurityException e) {
				if (!(e instanceof EOFException)) {
					e.printStackTrace();
				}
			}
		}
	}

}
