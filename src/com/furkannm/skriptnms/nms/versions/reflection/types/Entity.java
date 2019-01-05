package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class Entity extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class Entity = null;
		try {
			Entity = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".Entity");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = Entity;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
