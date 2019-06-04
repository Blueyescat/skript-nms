package com.furkannm.skriptnms;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.furkannm.skriptnms.nms.NMS;
import com.furkannm.skriptnms.nms.NMSClasses;
import com.furkannm.skriptnms.nms.versions.reflection.types.*;
import com.furkannm.skriptnms.nms.versions.reflection.NMSReflection;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SkriptNMS extends JavaPlugin{
	private static String ver;
	private SkriptNMS instance;
	private SkriptAddon addonInstance;
	private static NMS nmsclass;
	
	public SkriptNMS() {
		if(instance == null) {
			instance = this;
		}else{
			throw new IllegalStateException();
		}
	}
	
	@Override
	public void onEnable() {
		ver = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		NMS.setDefaultNMS(new NMSReflection());
		nmsclass = NMS.getNMS(getVer());
		nmsclass.registerNbtCompound();
		String type = "default classes";
		if (nmsclass instanceof NMSReflection) {
			loadNMSClasses();
			type = "reflection classes";
		}
		getLogger().info("Skript-NMS is started for "+ver+" with "+type+"!");
		try {
			getAddonInstance().loadClasses("com.furkannm.skriptnms", "effects", "expressions");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public SkriptAddon getAddonInstance() {
		if(addonInstance == null) {
			addonInstance = Skript.registerAddon(getInstance());
		}
		return addonInstance;
	}
	
	public SkriptNMS getInstance() {
		if(instance == null) {
			throw new IllegalStateException();
		}
		return instance;
	}
	
	public static String getVer() {
		return ver;
	}
	
	public static NMS getNMS(){
		return nmsclass;
	}

	private void loadNMSClasses() {
		NMSClasses.registerNMS(new BlockPosition());
		NMSClasses.registerNMS(new CraftEntity());
		NMSClasses.registerNMS(new CraftItemStack());
		NMSClasses.registerNMS(new CraftWorld());
		NMSClasses.registerNMS(new Entity());
		NMSClasses.registerNMS(new IBlockData());
		NMSClasses.registerNMS(new ItemStack());
		NMSClasses.registerNMS(new MojangsonParser());
		NMSClasses.registerNMS(new NBTBase());
		NMSClasses.registerNMS(new NBTCompressedStreamTools());
		NMSClasses.registerNMS(new NBTTagByte());
		NMSClasses.registerNMS(new NBTTagCompound());
		NMSClasses.registerNMS(new NBTTagList());
		NMSClasses.registerNMS(new NBTTagDouble());
		NMSClasses.registerNMS(new NBTTagFloat());
		NMSClasses.registerNMS(new NBTTagInt());
		NMSClasses.registerNMS(new NBTTagLong());
		NMSClasses.registerNMS(new NBTTagShort());
		NMSClasses.registerNMS(new NBTTagString());
		NMSClasses.registerNMS(new TileEntity());
		NMSClasses.registerNMS(new World());
	}
}
