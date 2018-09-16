package com.furkannm.skriptnms.util.nms;


import com.furkannm.skriptnms.Core;

public class NBTBase {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NbtBase = null;
		try {
			NbtBase = Class.forName("net.minecraft.server."+Core.getVer()+".NBTBase");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NbtBase;
	}
}
