package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class TileEntity {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class TileEntity = null;
		try {
			TileEntity = Class.forName("net.minecraft.server."+Core.getVer()+".TileEntity");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return TileEntity;
	}
}
