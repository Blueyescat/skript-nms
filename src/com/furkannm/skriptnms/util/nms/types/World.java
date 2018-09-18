package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class World extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class World = null;
		try {
			World = Class.forName("net.minecraft.server."+Core.getVer()+".World");
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
