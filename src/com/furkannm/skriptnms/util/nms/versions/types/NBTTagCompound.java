package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagCompound extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NbtCompound = null;
		try {
			NbtCompound = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagCompound");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nmsClass = NbtCompound;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
