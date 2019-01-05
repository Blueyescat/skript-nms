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

import com.furkannm.skriptnms.util.MobSpawnerPlaceEvent;
import com.furkannm.skriptnms.util.SpawnerItem;
import com.furkannm.skriptnms.util.SpawnerType;

public class EvtSpawnerPlace extends SkriptEvent{

	static{
		Skript.registerEvent("Spawner Break", EvtSpawnerPlace.class, MobSpawnerPlaceEvent.class, "spawner place")
						.description("Called when a spawner is placed.")
						.examples("on spawner place:")
						.since("0.1.2");
		EventValues.registerEventValue(MobSpawnerPlaceEvent.class, SpawnerType.class, new Getter<SpawnerType, MobSpawnerPlaceEvent>() {
			@Override
			public SpawnerType get(MobSpawnerPlaceEvent e) {
				return e.getSpawnerType();
			}
			
		}, 0);
		EventValues.registerEventValue(MobSpawnerPlaceEvent.class, SpawnerItem.class, new Getter<SpawnerItem, MobSpawnerPlaceEvent>() {
			@Override
			public SpawnerItem get(MobSpawnerPlaceEvent e) {
				return e.getSpawnerType().getSpawnerItem();
			}
			
		}, 0);
		EventValues.registerEventValue(MobSpawnerPlaceEvent.class, Location.class, new Getter<Location, MobSpawnerPlaceEvent>() {
			@Override
			public Location get(MobSpawnerPlaceEvent e) {
				return e.getLocation();
			}
			
		}, 0);
		EventValues.registerEventValue(MobSpawnerPlaceEvent.class, Player.class, new Getter<Player, MobSpawnerPlaceEvent>() {
			@Override
			public Player get(MobSpawnerPlaceEvent e) {
				return e.getPlayer();
			}
			
		}, 0);
	}
	
	@Override
	public String toString(Event e, boolean bol) {
		return "spawner place";
	}

	@Override
	public boolean check(Event e) {
		return e instanceof MobSpawnerPlaceEvent;
	}

	@Override
	public boolean init(Literal<?>[] lit, int i, ParseResult pr) {
		return true;
	}

	
}
