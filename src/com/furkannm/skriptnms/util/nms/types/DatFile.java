package com.furkannm.skriptnms.util.nms.types;

import java.io.File;

public class DatFile {

	public static File file = null;
	public static Object datContent = null;
	
	public static void setLastData(File f,Object nbtComp) {
		file = f;
		datContent = nbtComp;
	}
	
	public static File getFile() {
		return file;
	}
	
	public static Object getNbt() {
		return datContent;
	}
}
