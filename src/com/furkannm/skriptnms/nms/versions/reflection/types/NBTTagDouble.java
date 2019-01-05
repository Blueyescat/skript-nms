package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagDouble extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagDouble = null;
		try {
			NBTTagDouble = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagDouble");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagDouble;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
