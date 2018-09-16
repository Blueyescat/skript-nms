package com.furkannm.skriptnms.util.nms;

import com.furkannm.skriptnms.Core;

public class NBTTagCompound {

	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NbtCompound = null;
		try {
			NbtCompound = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagCompound");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NbtCompound;
	}
}
