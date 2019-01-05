package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagShort extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagShort = null;
		try {
			NBTTagShort = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagShort");
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		nmsClass = NBTTagShort;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
