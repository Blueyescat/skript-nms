package com.furkannm.skriptnms.expressions;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.DatFile;
import com.furkannm.skriptnms.nms.NMS;

@Name("File NBT")
@Examples({"nbt from loaded dat file"})

public class ExprLoadedDATFileNBT extends SimpleExpression<Object>{

	static {
		Skript.registerExpression(ExprLoadedDATFileNBT.class, Object.class, ExpressionType.SIMPLE, "nbt [tag[s]] (of|from) [the] [last[ly]] [loaded] dat file");
	}
	
	private Class<?> returnType = SkriptNMS.getNMS().getCompoundClass();
	
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		return true;
	}
	
	@Override
	@Nullable
	protected Object[] get(Event e) {
		if(DatFile.getFile() == null) {
			Skript.error("Error when geting dat file's nbt");
			return null;
		}
		return new Object[] { returnType.cast(DatFile.getNbt()) };
	}
	
	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(String[].class);
		}
		return null;
	}
	
	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		File file = DatFile.getFile();	
		
		Object fileNBT = returnType.cast(DatFile.getNbt());
		if (mode == ChangeMode.ADD) {
			Object parsedNBT = null;
			if (args != null) parsedNBT = returnType.cast(SkriptNMS.getNMS().parseRawNBT(((String) args[0])));
			SkriptNMS.getNMS().addToCompound(returnType.cast(fileNBT), returnType.cast(parsedNBT));
			SkriptNMS.getNMS().setFileNBT(file, fileNBT);
		} else if (mode == ChangeMode.REMOVE) {
			for (Object s : args) {
				SkriptNMS.getNMS().removeFromCompound(returnType.cast(fileNBT), (String) s);		
			}
			SkriptNMS.getNMS().setFileNBT(file, fileNBT);
		}
		
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public Class<? extends Object> getReturnType() {
		return returnType;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "the NBT from file " + DatFile.getFile().getName();
	}
}
