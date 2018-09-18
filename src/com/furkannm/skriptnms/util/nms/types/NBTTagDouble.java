package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagDouble extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagDouble = null;
		try {
			NBTTagDouble = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagDouble");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagDouble;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
