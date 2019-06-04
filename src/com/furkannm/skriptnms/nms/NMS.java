package com.furkannm.skriptnms.nms;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.versions.v1_10_R1;
import com.furkannm.skriptnms.nms.versions.v1_11_R1;
import com.furkannm.skriptnms.nms.versions.v1_12_R1;
import com.furkannm.skriptnms.nms.versions.v1_13_R1;
import com.furkannm.skriptnms.nms.versions.v1_13_R2;
import com.furkannm.skriptnms.nms.versions.v1_8_R3;
import com.furkannm.skriptnms.nms.versions.v1_9_R1;
import com.furkannm.skriptnms.nms.versions.reflection.NMSReflection;

public abstract class NMS {
	public static NMS defaultNMS;
	
	public static NMS getNMS(String version) {
		if (version.equalsIgnoreCase("v1_8_R3")) return new v1_8_R3();
		if (version.equalsIgnoreCase("v1_9_R1")) return new v1_9_R1();
		if (version.equalsIgnoreCase("v1_10_R1")) return new v1_10_R1();
		if (version.equalsIgnoreCase("v1_11_R1")) return new v1_11_R1();
		if (version.equalsIgnoreCase("v1_12_R1")) return new v1_12_R1();
		if (version.equalsIgnoreCase("v1_13_R1")) return new v1_13_R1();
		if (version.equalsIgnoreCase("v1_13_R2")) return new v1_13_R2();
		return defaultNMS;
	}
	
	/*public static Class<?> getMCClass(String classStr) {
		String ver = SkriptNMS.getVer();
		Class<?> nmsClass;
		try {
			if (!(getNMS(ver) instanceof NMSReflection)) {
				nmsClass = Class.forName("net.minecraft.server."+ver+"."+classStr);
			} else {
				nmsClass = Class.forName("com.furkannm.skriptnms.nms.versions.reflection.types."+classStr);
				return nmsClass.getMethod("get", Class.class).invoke(nmsClass).getClass();
			}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			if(getNMS(ver) instanceof NMSReflection) Bukkit.getLogger().warning("Unknown " + classStr +" nms class for "+ver+"!");
			else Bukkit.getLogger().warning("Unknown " + classStr +" bukkit class for "+ver+"!");
			return null;
		}
		return nmsClass;
	}*/
	
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
	
	public abstract Object[] getContents(Object nbtList);
	public abstract void addToList(Object nbtList, Object toAdd);
	public abstract void removeFromList(Object nbtList, int index);
	public abstract void setIndex(Object nbtList, int index, Object toSet);
	public abstract Object getIndex(Object nbtList, int index);

	public abstract Class getCompoundClass();
	public abstract Class getBaseClass();
	
	public abstract void registerNbtCompound();
}
