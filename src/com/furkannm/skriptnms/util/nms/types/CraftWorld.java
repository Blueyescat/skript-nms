package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class CraftWorld extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class CraftWorld = null;
		try {
			CraftWorld = Class.forName("org.bukkit.craftbukkit."+Core.getVer()+".CraftWorld");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = CraftWorld;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
