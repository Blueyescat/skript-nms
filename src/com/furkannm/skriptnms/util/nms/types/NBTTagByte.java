package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagByte {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagByte = null;
		try {
			NBTTagByte = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagByte");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagByte;
	}
}
