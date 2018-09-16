package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagFloat {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagFloat = null;
		try {
			NBTTagFloat = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagFloat");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagFloat;
	}
}
