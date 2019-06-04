package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagList extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NbtCompound = null;
		try {
			NbtCompound = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagList");
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		nmsClass = NbtCompound;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
