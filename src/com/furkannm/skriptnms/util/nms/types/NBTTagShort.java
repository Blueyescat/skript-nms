package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagShort extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagShort = null;
		try {
			NBTTagShort = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagShort");
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		nmsClass = NBTTagShort;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
