package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagLong {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagLong = null;
		try {
			NBTTagLong = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagLong");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagLong;
	}
}
