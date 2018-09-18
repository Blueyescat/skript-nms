package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class IBlockData extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class IBlockData = null;
		try {
			IBlockData = Class.forName("net.minecraft.server."+Core.getVer()+".IBlockData");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = IBlockData;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
