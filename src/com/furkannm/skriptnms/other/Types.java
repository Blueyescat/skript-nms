package com.furkannm.skriptnms.other;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.registrations.Classes;

import com.furkannm.skriptnms.util.SpawnerItem;
import com.furkannm.skriptnms.util.SpawnerType;

public class Types {
	static {
		Classes.registerClass(new ClassInfo<>(SpawnerType.class, "spawnertype")
								.user("spawnertype"));
		Classes.registerClass(new ClassInfo<>(SpawnerItem.class, "spawneritem")
				.user("spawneritem"));
	}
}
