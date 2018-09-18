package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagByte extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagByte = null;
		try {
			NBTTagByte = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagByte");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTTagByte;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
