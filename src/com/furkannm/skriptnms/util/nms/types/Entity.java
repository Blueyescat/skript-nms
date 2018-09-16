package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class Entity {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class Entity = null;
		try {
			Entity = Class.forName("net.minecraft.server."+Core.getVer()+".Entity");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Entity;
	}
}
