package com.furkannm.skriptnms.util.nms.types;

import com.furkannm.skriptnms.Core;

public class NBTCompressedStreamTools {
	
	@SuppressWarnings("rawtypes")
	public static Class get() {
		Class NBTCompressedStreamTools = null;
		try {
			NBTCompressedStreamTools = Class.forName("net.minecraft.server."+Core.getVer()+".NBTCompressedStreamTools");
		} catch (SecurityException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NBTCompressedStreamTools;
	}
}
