package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagShort {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagShort = null;
		try {
			NBTTagShort = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagShort");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagShort;
	}
}
