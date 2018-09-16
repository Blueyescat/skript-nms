package com.furkannm.skriptnms.expressions;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import com.furkannm.skriptnms.util.nms.NBTTagCompound;
import com.furkannm.skriptnms.util.nms.NMS;
import com.furkannm.skriptnms.util.nms.types.DatFile;

@Name("File NBT")
@Examples({
		"nbt from \"world/level.dat\"",
		"last loaded file's nbt"
})

public class ExprFileNbt extends SimpleExpression<Object>{

	private Expression<Object> target;
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends Object> getReturnType() {
		return NBTTagCompound.get();
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		if(expr != null && expr.length>0) {
			target = (Expression<Object>) expr[0];
		}else{
			target = null;
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		if(target != null) {
			return "the NBT from file " + target.toString(e, debug);
		}else{
			if(DatFile.getFile() != null) {
				return "the NBT from file " + DatFile.getFile().getName();
			}else{
				return null;
			}
		}
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		if (target != null) {
			Object file = target.getSingle(e);
			file = file.toString().endsWith(".dat") ? file.toString() : file.toString() + ".dat";
			File f = new File(file.toString());
			if(!f.exists()) return null;
			NMS.loadFileNbt(f);
			return new Object[] { NBTTagCompound.get().cast(DatFile.getNbt()) };
		}else{
			return new Object[] { NBTTagCompound.get().cast(DatFile.getNbt()) };
		}
	}

	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		File file;
		if(target != null) {
			Object f = target.getSingle(e);
			f = f.toString().endsWith(".dat") ? f.toString() : f.toString() + ".dat";
			file = new File(f.toString());
		
		}else{
			file = DatFile.getFile();
		}
		Object parsedNBT = null;
		if (args != null) {
			parsedNBT = NBTTagCompound.get().cast(NMS.parseRawNBT(((String) args[0])));
		}
		
		Object fileNBT = NBTTagCompound.get().cast(DatFile.getNbt());
		if (mode == ChangeMode.ADD) {
			NMS.addToCompound(NBTTagCompound.get().cast(fileNBT), NBTTagCompound.get().cast(parsedNBT));
			NMS.setFileNBT(file, fileNBT);
		} else if (mode == ChangeMode.REMOVE) {
			for (Object s : args) {
				NMS.removeFromCompound(NBTTagCompound.get().cast(fileNBT), (String) s);		
			}
			NMS.setFileNBT(file, fileNBT);
		}
		
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(String[].class);
		}
		return null;
}

}
