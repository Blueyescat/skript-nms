package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class MojangsonParser {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class MojangsonParser = null;
		try {
			MojangsonParser = Class.forName("net.minecraft.server."+Core.getVer()+".MojangsonParser");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return MojangsonParser;
	}
}
