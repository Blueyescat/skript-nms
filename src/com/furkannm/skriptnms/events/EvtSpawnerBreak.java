package com.furkannm.skriptnms.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;

import com.furkannm.skriptnms.util.MobSpawnerBreakEvent;
import com.furkannm.skriptnms.util.SpawnerItem;
import com.furkannm.skriptnms.util.SpawnerType;

public class EvtSpawnerBreak extends SkriptEvent{

	static{
		Skript.registerEvent("Spawner Break", EvtSpawnerBreak.class, MobSpawnerBreakEvent.class, "spawner break")
						.description("Called when a spawner is breaked.")
						.examples("on spawner break:")
						.since("0.1.2");
		EventValues.registerEventValue(MobSpawnerBreakEvent.class, SpawnerType.class, new Getter<SpawnerType, MobSpawnerBreakEvent>() {
			@Override
			public SpawnerType get(MobSpawnerBreakEvent e) {
				return e.getSpawnerType();
			}		
		}, 0);
		EventValues.registerEventValue(MobSpawnerBreakEvent.class, SpawnerItem.class, new Getter<SpawnerItem, MobSpawnerBreakEvent>() {
			@Override
			public SpawnerItem get(MobSpawnerBreakEvent e) {
				return e.getSpawnerType().getSpawnerItem();
			}	
		}, 0);
		EventValues.registerEventValue(MobSpawnerBreakEvent.class, Location.class, new Getter<Location, MobSpawnerBreakEvent>() {
			@Override
			public Location get(MobSpawnerBreakEvent e) {
				return e.getLocation();
			}			
		}, 0);
		EventValues.registerEventValue(MobSpawnerBreakEvent.class, Player.class, new Getter<Player, MobSpawnerBreakEvent>() {
			@Override
			public Player get(MobSpawnerBreakEvent e) {
				return e.getPlayer();
			}	
		}, 0);
	}
	
	@Override
	public String toString(Event e, boolean bol) {
		return "spawner break";
	}

	@Override
	public boolean check(Event e) {
		return e instanceof MobSpawnerBreakEvent;
	}

	@Override
	public boolean init(Literal<?>[] lit, int i, ParseResult pr) {
		return true;
	}

	
}
