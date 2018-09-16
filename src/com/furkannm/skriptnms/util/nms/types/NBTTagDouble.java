package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagDouble {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagDouble = null;
		try {
			NBTTagDouble = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagDouble");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagDouble;
	}
}
