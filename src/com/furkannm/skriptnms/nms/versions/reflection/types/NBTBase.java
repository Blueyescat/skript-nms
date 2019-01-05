package com.furkannm.skriptnms.nms.versions.reflection.types;


import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTBase extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NbtBase = null;
		try {
			NbtBase = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTBase");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nmsClass = NbtBase;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
