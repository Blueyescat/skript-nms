package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagLong extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagLong = null;
		try {
			NBTTagLong = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagLong");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagLong;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
