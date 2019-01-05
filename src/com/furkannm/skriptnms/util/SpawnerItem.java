package com.furkannm.skriptnms.util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SpawnerItem {	
	
	private Material m;
	private EntityType et;
	public SpawnerItem(Material m,EntityType et) {
		this.m = m;
		this.et = et;
	}
		
	public Material getMaterial() {
		return m;
	}
	public EntityType getEntityType() {
		return et;
	}
	@Override
	public String toString() {
		return (m==null) ? ((et==null)? "unknown" : et.name()) : m.name() ;
	}
}