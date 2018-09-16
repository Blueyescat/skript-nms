package com.furkannm.skriptnms.effects;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.furkannm.skriptnms.util.nms.NMS;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Load Dat File")
@Examples({
		"load nbt from \"world/level.dat\""
})

public class EffLoadDat extends Effect {

	//load dat file in %string%
	private File lastLoadedFile = null;
	private Expression<String> fileName;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean kleen, ParseResult result) {	
		fileName = (Expression<String>) expr[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the loaded NBT from file " + lastLoadedFile.toString();
	}

	@Override
	protected void execute(Event e) {
		String f = fileName.getSingle(e);
		f = f.endsWith(".dat") ? f : f + ".dat";
		lastLoadedFile = new File(f);
		if(lastLoadedFile.exists()) {
			NMS.loadFileNbt(lastLoadedFile);
		}else{
			Skript.warning("Error when loading dat file");
		}
	}

}
