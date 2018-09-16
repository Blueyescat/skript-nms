package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class BlockPosition {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class BlockPosition = null;
		try {
			BlockPosition = Class.forName("net.minecraft.server."+Core.getVer()+".BlockPosition");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BlockPosition;
	}
}
