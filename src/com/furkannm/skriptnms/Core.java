package com.furkannm.skriptnms;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.furkannm.skriptnms.util.nms.NMSClasses;
import com.furkannm.skriptnms.util.nms.types.*;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class Core extends JavaPlugin{
	private static String ver;
	private static Core instance;
	private static SkriptAddon addonInstance;
	
	public Core() {
		if(instance == null) {
			instance = this;
		}else{
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void onEnable() {
		getLogger().info("Skript-NMS is started!");
		ver = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		try {
			getAddonInstance().loadClasses("com.furkannm.skriptnms", "effects", "expressions");
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadClass();
		NMSClasses.loadClasses();
	}
	
	public static SkriptAddon getAddonInstance() {
		if(addonInstance == null) {
			addonInstance = Skript.registerAddon(getInstance());
		}
		return addonInstance;
	}
	
	public static Core getInstance() {
		if(instance == null) {
			throw new IllegalStateException();
		}
		return instance;
	}
	
	public static String getVer() {
		return ver;
	}
	
	public static void loadClass() {
		new BlockPosition();
		new CraftEntity();
		new CraftItemStack();
		new CraftWorld();
		new Entity();
		new IBlockData();
		new ItemStack();
		new MojangsonParser();
		new NBTBase();
		new NBTCompressedStreamTools();
		new NBTTagByte();
		new NBTTagCompound();
		new NBTTagDouble();
		new NBTTagFloat();
		new NBTTagInt();
		new NBTTagLong();
		new NBTTagShort();
		new NBTTagString();
		new TileEntity();
		new World();
	}
}
