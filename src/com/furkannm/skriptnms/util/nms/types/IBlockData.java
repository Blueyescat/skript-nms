package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class IBlockData {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class IBlockData = null;
		try {
			IBlockData = Class.forName("net.minecraft.server."+Core.getVer()+".IBlockData");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return IBlockData;
	}
}
