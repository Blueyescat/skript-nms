package com.furkannm.skriptnms.util;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.furkannm.skriptnms.SkriptNMS;

public class BlockEvents implements Listener{
	
	@EventHandler
	public void onBreak(org.bukkit.event.block.BlockBreakEvent e) {
		if(e.getBlock() == null || e.getBlock().getState() == null) return;
		if(e.getBlock().getState() instanceof CreatureSpawner) {
			Object obj = SkriptNMS.getNMS().getTileNBT(e.getBlock());
			String type = SkriptNMS.getNMS().getNBTTag(obj, "EntityId")+"";
			if(type!=null) {
				type = type.replace("\"", "");
				if(type.equalsIgnoreCase("Item")) {
					
					Object sdata = SkriptNMS.getNMS().getNBTTag(obj, "SpawnData");
					Object item = SkriptNMS.getNMS().getNBTTag(sdata, "Item");
					String itemid = SkriptNMS.getNMS().getNBTTag(item, "id")+"";
					itemid = itemid.replace("\"", "").replace("minecraft:", "").toUpperCase(Locale.ENGLISH);
					Material m = Material.getMaterial(itemid);
					Bukkit.getServer().getPluginManager().callEvent(new MobSpawnerBreakEvent(e.getPlayer(), e.getBlock().getLocation(), m, null));
				}else{
					String name = type.toUpperCase(Locale.ENGLISH);
					EntityType et = EntityType.valueOf(name);
					Bukkit.getServer().getPluginManager().callEvent(new MobSpawnerBreakEvent(e.getPlayer(), e.getBlock().getLocation(), null, et));
				}
			}
		}
	}
	
	@EventHandler
	public void onPlace(org.bukkit.event.block.BlockPlaceEvent e) {
		if(e.getBlock() == null || e.getBlock().getState() == null) return;
		if(e.getBlock().getState() instanceof CreatureSpawner) {
			Object obj = SkriptNMS.getNMS().getTileNBT(e.getBlock());
			String type = SkriptNMS.getNMS().getNBTTag(obj, "EntityId")+"";
			if(type!=null) {
				type = type.replace("\"", "");
				if(type.equalsIgnoreCase("Item")) {
					Object sdata = SkriptNMS.getNMS().getNBTTag(obj, "SpawnData");
					Object item = SkriptNMS.getNMS().getNBTTag(sdata, "Item");
					String itemid = SkriptNMS.getNMS().getNBTTag(item, "id")+"";
					itemid = itemid.replace("\"", "").replace("minecraft:", "").toUpperCase(Locale.ENGLISH);
					Material m = Material.getMaterial(itemid);
					Bukkit.getServer().getPluginManager().callEvent(new MobSpawnerPlaceEvent(e.getPlayer(), e.getBlock().getLocation(), m, null));
				}else{
					String name = type.toUpperCase(Locale.ENGLISH);
					EntityType et = EntityType.valueOf(name);
					Bukkit.getServer().getPluginManager().callEvent(new MobSpawnerPlaceEvent(e.getPlayer(), e.getBlock().getLocation(), null, et));
				}
			}
		}
	}
}
