package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class BlockPosition extends NMSClasses{

	private static Class nmsClass;
		
	@Override
	public void set() {
		Class BlockPosition = null;
		try {
			BlockPosition = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".BlockPosition");
		} catch (SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		nmsClass = BlockPosition;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
