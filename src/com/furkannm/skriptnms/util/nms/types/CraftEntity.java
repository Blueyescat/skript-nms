package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class CraftEntity {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class CraftEntity = null;
		try {
			CraftEntity = Class.forName("org.bukkit.craftbukkit."+Core.getVer()+".entity.CraftEntity");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CraftEntity;
	}
}
