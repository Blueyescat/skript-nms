package com.furkannm.skriptnms.util.nms;

import java.util.ArrayList;

public abstract class NMSClasses {

	public static ArrayList<NMSClasses> classes = new ArrayList<NMSClasses>();
	public NMSClasses() {
		classes.add(this);
	}
	
	public abstract void set();
	
	public static void registerNMS(NMSClasses nms) {
		nms.set();
	}
}
