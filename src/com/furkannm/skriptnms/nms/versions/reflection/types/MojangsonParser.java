package com.furkannm.skriptnms.nms.versions.reflection.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class MojangsonParser extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class MojangsonParser = null;
		try {
			MojangsonParser = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".MojangsonParser");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = MojangsonParser;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
