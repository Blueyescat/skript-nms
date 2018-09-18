package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagFloat extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagFloat = null;
		try {
			NBTTagFloat = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagFloat");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagFloat;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
