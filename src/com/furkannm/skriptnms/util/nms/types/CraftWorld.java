package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class CraftWorld {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class CraftWorld = null;
		try {
			CraftWorld = Class.forName("org.bukkit.craftbukkit."+Core.getVer()+".CraftWorld");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CraftWorld;
	}
}
