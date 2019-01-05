package com.furkannm.skriptnms.util;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class SpawnerType {
	
	private SpawnerItem spawnerItem;
	private SpawnerTypes st;
	public SpawnerType(SpawnerTypes st,SpawnerItem spawnerItem) {
		this.spawnerItem = spawnerItem;
		this.st = st;
	}
	
	public String getType() {
		return st.getName();
	}
	
	public Material getMaterial() {
		return spawnerItem.getMaterial();
	}
	
	public EntityType getEntityType() {
		return spawnerItem.getEntityType();
	}
	
	public SpawnerItem getSpawnerItem() {
		return spawnerItem;
	}
	
	@Override
	public String toString() {
		return (spawnerItem.getMaterial()==null) ? ((spawnerItem.getEntityType()==null) ? SpawnerTypes.UNKNOWN.getName() : SpawnerTypes.ENTITY.getName()) : SpawnerTypes.ITEM.getName();
	}
	
	
	
	public enum SpawnerTypes {
		ITEM("item"),ENTITY("entity"),UNKNOWN("unknown");
		
		String name;
		SpawnerTypes(String name) {
			this.name = name;
		}
		
		String getName() {
			return name;
		}
	}
}
