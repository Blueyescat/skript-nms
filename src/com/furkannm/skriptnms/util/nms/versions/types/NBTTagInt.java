package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

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
