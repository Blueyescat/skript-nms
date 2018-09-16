package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class World {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class World = null;
		try {
			World = Class.forName("net.minecraft.server."+Core.getVer()+".World");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return World;
	}
}
