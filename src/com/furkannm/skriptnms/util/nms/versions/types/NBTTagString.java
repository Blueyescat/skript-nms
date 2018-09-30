package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTTagString extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTTagString = null;
		try {
			NBTTagString = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTTagString");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nmsClass = NBTTagString;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
