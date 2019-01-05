package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagInt extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagInt = null;
		try {
			NBTTagInt = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagInt");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = NBTTagInt;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
