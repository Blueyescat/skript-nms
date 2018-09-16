package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagInt {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagInt = null;
		try {
			NBTTagInt = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagInt");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagInt;
	}
}
