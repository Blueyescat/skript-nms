package com.furkannm.skriptnms.nms.versions;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import ch.njol.skript.classes.Changer;
import ch.njol.util.coll.CollectionUtils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.slot.Slot;

import com.furkannm.skriptnms.nms.DatFile;
import com.furkannm.skriptnms.nms.NMS;

public class v1_8_R3 extends NMS{

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
		tileEntity.b(NBT);
		return NBT;
	}

	@Override
	public Object getEntityNBT(Entity entity) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
		NBTTagCompound NBT = new NBTTagCompound();
		nmsEntity.e(NBT);
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
			nmsWorld.notify(tileEntity.getPosition());
		}
	}

	@Override
	public ItemStack getItemWithNBT(ItemStack itemStack, Object compound) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(itemStack);
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
			net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) entity).getHandle();
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
	
	@Override
	public Object[] getContents(Object nbtList) {
		if (nbtList instanceof NBTTagList) {
			Object[] contents = new Object[((NBTTagList) nbtList).size()];
			for (int i = 0; i < ((NBTTagList) nbtList).size(); i++) {
				if (getIndex(nbtList, i) != null) {
					contents[i] = getIndex(nbtList, i);
				}
			}
			return contents;
		}
		return null;
	}

	@Override
	public void addToList(Object nbtList, Object toAdd) {
		if (nbtList instanceof NBTTagList && toAdd instanceof NBTBase) {
			((NBTTagList) nbtList).add((NBTBase) toAdd);
		}
	}

	@Override
	public void removeFromList(Object nbtList, int index) {
		if (nbtList instanceof NBTTagList && index >= 0 && index < ((NBTTagList) nbtList).size()) {
			((NBTTagList) nbtList).a(index);
		}
	}

	@Override
	public void setIndex(Object nbtList, int index, Object toSet) {
		if (nbtList instanceof NBTTagList && index >= 0 && index < ((NBTTagList) nbtList).size()) {
			if (toSet instanceof NBTBase) {
				((NBTTagList) nbtList).a(index, (NBTBase) toSet);
			} else if (toSet instanceof Number) {
				((NBTTagList) nbtList).a(index, (NBTBase) convertToNBT((Number) toSet));
			} else if (toSet instanceof String) {
				((NBTTagList) nbtList).a(index, (NBTBase) convertToNBT((String) toSet));
			}
		}
	}

	@Override
	public Object getIndex(Object nbtList, int index) {
		if (nbtList instanceof NBTTagList && index >= 0 && index < ((NBTTagList) nbtList).size()) {
			NBTBase value = ((NBTTagList) nbtList).g(index);
			if (value instanceof NBTTagByte) {
				return ((NBTTagByte) value).f(); 
			} else if (value instanceof NBTTagShort) {
				return ((NBTTagShort) value).e(); 
			} else if (value instanceof NBTTagInt) {
				return ((NBTTagInt) value).d(); 
			} else if (value instanceof NBTTagLong) {
				return ((NBTTagLong) value).c(); 
			} else if (value instanceof NBTTagFloat) {
				return ((NBTTagFloat) value).h();
			} else if (value instanceof NBTTagDouble) {
				return ((NBTTagDouble) value).g(); 
			} else if (value instanceof NBTTagString) {
				return ((NBTTagString) value).a_(); 
			} else if (value instanceof NBTBase) {
				return value;
			}
		}
		return null;
	}

	@Override
	public Class getCompoundClass() {
		return NBTTagCompound.class;
	}

	@Override
	public Class getBaseClass() {
		return NBTBase.class;
	}

	@Override
	public void registerNbtCompound() {
		Classes.registerClass(new ClassInfo<NBTTagCompound>(NBTTagCompound.class, "compound")
			.user("nbt ?(compound)?")
			.name("NBT Compound")
			.since("0.1.3")
			.parser(new Parser<NBTTagCompound>() {

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}
				
				@Override
				@javax.annotation.Nullable
				public NBTTagCompound parse(String s, ParseContext context) {
					if (s.startsWith("nbt:{") && s.endsWith("}")) {
						NBTTagCompound nbt = (NBTTagCompound) parseRawNBT(s.substring(4));
						return nbt;
					}
					return null;
				}

				@Override
				public String toString(NBTTagCompound nbt, int arg1) {
					return nbt.toString();
				}

				@Override
				public String toVariableNameString(NBTTagCompound nbt) {		
					return nbt.toString();
				}	
			}).changer(new Changer<NBTTagCompound>() {
					@Override
					public Class<?>[] acceptChange(ChangeMode mode) {
						if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
							return CollectionUtils.array(String.class, NBTTagCompound.class);
						}
						return null;
					}

					@Override
					public void change(NBTTagCompound[] compounds, Object[] values, ChangeMode mode) {
						if (mode == ChangeMode.ADD) {
							if (values[0] instanceof String) {
								Object parsedNBT = parseRawNBT((String) values[0]);
								addToCompound(compounds[0], parsedNBT);
							} else {
								addToCompound(compounds[0], values[0]);
							}
						} else if (mode == ChangeMode.REMOVE) {
							if (values[0] instanceof NBTTagCompound)
								return;
							for (Object s : values) {
								compounds[0].remove((String) s);
							}
						}
					}
				})
		);
	}

}
