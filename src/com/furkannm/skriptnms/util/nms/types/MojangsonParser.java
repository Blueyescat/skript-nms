package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class MojangsonParser extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class MojangsonParser = null;
		try {
			MojangsonParser = Class.forName("net.minecraft.server."+Core.getVer()+".MojangsonParser");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = MojangsonParser;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
