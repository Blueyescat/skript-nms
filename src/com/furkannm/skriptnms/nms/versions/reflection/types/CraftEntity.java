package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class CraftEntity extends NMSClasses{
	
	private static Class nmsClass;
	
	@Override
	public void set() {
		Class CraftEntity = null;
		try {
			CraftEntity = Class.forName("org.bukkit.craftbukkit."+SkriptNMS.getVer()+".entity.CraftEntity");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = CraftEntity;
	}
		
	public static Class get() {
		return nmsClass;
	}
}
