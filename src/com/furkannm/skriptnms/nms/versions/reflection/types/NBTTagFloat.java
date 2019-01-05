package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagFloat extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagFloat = null;
		try {
			NBTTagFloat = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagFloat");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagFloat;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
