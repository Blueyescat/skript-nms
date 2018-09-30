package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagByte extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagByte = null;
		try {
			NBTTagByte = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagByte");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagByte;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
