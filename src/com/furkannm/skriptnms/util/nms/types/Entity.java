package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class Entity extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class Entity = null;
		try {
			Entity = Class.forName("net.minecraft.server."+Core.getVer()+".Entity");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = Entity;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
