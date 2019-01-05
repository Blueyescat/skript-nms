package com.furkannm.skriptnms.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.furkannm.skriptnms.util.SpawnerType.SpawnerTypes;

public class MobSpawnerPlaceEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private EntityType entityType;
	private Material material;
	private Location location;
	private Player player;
	private SpawnerType spawnerType;
	
	public MobSpawnerPlaceEvent(Player player,Location location,Material material,EntityType entityType) {
		this.player = player;
		this.location = location;
		this.material = material;
		this.entityType = entityType;
		this.spawnerType = new SpawnerType((material==null) ? ((entityType==null) ? null : SpawnerTypes.ENTITY) : SpawnerTypes.ITEM, new SpawnerItem(material, entityType));
	}
	
	public Player getPlayer() {
		return player;
	}
	public Location getLocation() {
		return location;
	}
	public Material getMaterial() {
		return material;
	}
	public EntityType getEntityType() {
		return entityType;
	}
	public SpawnerType getSpawnerType() {
		return spawnerType;
	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
