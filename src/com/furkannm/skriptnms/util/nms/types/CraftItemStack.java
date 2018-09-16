package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class CraftItemStack {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class CraftItemStack = null;
		try {
			CraftItemStack = Class.forName("org.bukkit.craftbukkit."+Core.getVer()+".inventory.CraftItemStack");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CraftItemStack;
	}
}
