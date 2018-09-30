package com.furkannm.skriptnms.util.nms.versions.types;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.util.nms.NMSClasses;

@SuppressWarnings("rawtypes")
public class NBTCompressedStreamTools extends NMSClasses{

	private static Class nmsClass;
	
	@Override
	public void set() {
		Class NBTCompressedStreamTools = null;
		try {
			NBTCompressedStreamTools = Class.forName("net.minecraft.server."+SkriptNMS.getVer()+".NBTCompressedStreamTools");
		} catch (SecurityException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		nmsClass = NBTCompressedStreamTools;
	}
	
	public static Class get() {
		return nmsClass;
	}
}
