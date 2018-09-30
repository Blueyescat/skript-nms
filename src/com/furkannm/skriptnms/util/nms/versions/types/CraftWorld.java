package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class CraftWorld extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class CraftWorld = null;
		try {
			CraftWorld = Class.forName("org.bukkit.craftbukkit."+SkriptNMS.getVer()+".CraftWorld");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = CraftWorld;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
