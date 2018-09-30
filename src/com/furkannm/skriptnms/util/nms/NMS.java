package com.furkannm.skriptnms.util.nms;

import java.io.File;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public abstract class NMS {
	public static HashMap<String,NMS> nmsVersions = new HashMap<>();
	public static NMS defaultNMS;
	
	public static void registerNMSVersion(String version,NMS nms) {
		nmsVersions.put(version, nms);
	}
	
	public static NMS getNMSClass(String version) {
		if(nmsVersions != null && nmsVersions.containsKey(version)) return nmsVersions.get(version);
		return defaultNMS;
	}
	
	public static void setDefaultNMS(NMS defaultClass) {
		defaultNMS = defaultClass;
	}
	
	public abstract Object[] getNBT(Object tar);	
	public abstract void addTargetsNBT(Object tar,Object nbt);
	public abstract void removeTargetsNBT(Object tar,Object[] nbt);
	public abstract void deleteTargetsNBT(Object tar);
	public abstract Object getNBTTag(Object compound, String tag);
	public abstract void setNBTTag(Object compound, String tag, Object toSet);
	public abstract void removeNBTTag(Object compound, String tag);
	public abstract void addToCompound(Object compound, Object toAdd);
	public abstract void removeFromCompound(Object compound, String ... toRemove);
	public abstract Object getTileNBT(Block block);
	public abstract Object getEntityNBT(Entity entity);
	public abstract Object getItemNBT(ItemStack itemStack);
	public abstract Object parseRawNBT(String rawNBT);
	public abstract void setTileNBT(Block block, Object newCompound);
	public abstract ItemStack getItemWithNBT(ItemStack itemStack, Object compound);
	public abstract void setEntityNBT(Entity entity, Object newCompound);
	public abstract Object convertToNBT(Number number);
	public abstract Object convertToNBT(String string);
	public abstract void loadFileNbt(File file);
	public abstract void setFileNBT(File file, Object newCompound);
}
