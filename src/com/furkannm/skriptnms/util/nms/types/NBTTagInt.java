package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagInt extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagInt = null;
		try {
			NBTTagInt = Class.forName("net.minecraft.server."+Core.getVer()+".NBTTagInt");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = NBTTagInt;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
