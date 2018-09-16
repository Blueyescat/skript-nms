package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTTagString {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTTagString = null;
		try {
			NBTTagString = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagString");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTTagString;
	}
}
