package com.furkannm.skriptnms.nms;

import java.io.File;

public class DatFile {

	public static File file = null;
	public static Object datContent = null;
	
	public static void loadDatFile(File f,Object nbtComp) {
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
