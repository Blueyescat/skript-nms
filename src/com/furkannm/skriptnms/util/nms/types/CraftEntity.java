package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class CraftEntity extends NMSClasses{
	
	private static Class nmsClass;
	
	@Override
	public void set() {
		Class CraftEntity = null;
		try {
			CraftEntity = Class.forName("org.bukkit.craftbukkit."+Core.getVer()+".entity.CraftEntity");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = CraftEntity;
	}
		
	public static Class get() {
		return nmsClass;
	}
}
