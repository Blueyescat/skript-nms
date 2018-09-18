package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class BlockPosition extends NMSClasses{

	private static Class nmsClass;
		
	@Override
	public void set() {
		Class BlockPosition = null;
		try {
			BlockPosition = Class.forName("net.minecraft.server."+Core.getVer()+".BlockPosition");
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		nmsClass = BlockPosition;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
