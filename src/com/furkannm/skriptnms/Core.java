package com.furkannm.skriptnms;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;

import com.furkannm.skriptnms.effects.EffLoadDat;
import com.furkannm.skriptnms.expressions.ExprFileNbt;
import com.furkannm.skriptnms.expressions.ExprNBTOf;

public class Core extends JavaPlugin{
	static String ver;
	
	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().getPlugin("Skript") != null && Skript.isAcceptRegistrations()) {
			Skript.registerExpression(ExprNBTOf.class, Object.class, ExpressionType.PROPERTY, "nbt[[ ]tag[s]] of %~object%", "%~object%'s nbt[[ ]tag[s]]");
			Skript.registerExpression(ExprFileNbt.class, Object.class, ExpressionType.PROPERTY, "nbt[[ ]tag[s]] from [file] %string%", "nbt[[ ]tag[s]] from last loaded [dat ]file", "last loaded [dat ]file's nbt[[ ]tag[s]]");
			Skript.registerEffect(EffLoadDat.class, "load nbt[[ ]tag[s]] from [file] %string%","load %string%'s nbt[[ ]tag[s]]");
			getLogger().info("Skript-NMS is started!");
			ver = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		}else {
			getLogger().info("Unable to find Skript or Skript isn't accepting registrations, disabling skript-nms...");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}
	
	public static String getVer() {
		return ver;
	}
}
