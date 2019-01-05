package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class CraftItemStack extends NMSClasses{
	
	private static Class nmsClass;
	
	@Override
	public void set() {
		Class CraftItemStack = null;
		try {
			CraftItemStack = Class.forName("org.bukkit.craftbukkit."+SkriptNMS.getVer()+".inventory.CraftItemStack");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = CraftItemStack;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
