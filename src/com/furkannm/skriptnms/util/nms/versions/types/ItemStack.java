package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class ItemStack extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class ItemStack = null;
		try {
			ItemStack = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".ItemStack");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = ItemStack;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
