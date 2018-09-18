package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class ItemStack extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class ItemStack = null;
		try {
			ItemStack = Class.forName("net.minecraft.server."+Core.getVer()+".ItemStack");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = ItemStack;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
