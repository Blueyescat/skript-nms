package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class ItemStack {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class ItemStack = null;
		try {
			ItemStack = Class.forName("net.minecraft.server."+Core.getVer()+".ItemStack");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ItemStack;
	}
}
