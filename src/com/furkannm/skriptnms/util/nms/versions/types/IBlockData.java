package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class IBlockData extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class IBlockData = null;
		try {
			IBlockData = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".IBlockData");
		} catch (SecurityException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		nmsClass = IBlockData;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
