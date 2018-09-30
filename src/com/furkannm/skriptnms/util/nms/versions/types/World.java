package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class World extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class World = null;
		try {
			World = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".World");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nmsClass = World;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
