package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class TileEntity extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class TileEntity = null;
		try {
			TileEntity = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".TileEntity");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nmsClass = TileEntity;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
