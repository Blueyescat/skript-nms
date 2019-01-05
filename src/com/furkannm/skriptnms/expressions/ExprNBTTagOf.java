package com.furkannm.skriptnms.expressions;

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
import com.furkannm.skriptnms.nms.NMS;

@Name("NBT Tag")
@Examples({"set {_burntime} to nbt tag \"BurnTime\" of (nbt of event-block)"})

public class ExprNBTTagOf extends SimpleExpression<Object> {

	private Class<?> nbtBase = NMS.getMCClass("NBTBase");
	static {
		Skript.registerExpression(ExprNBTTagOf.class, Object.class, ExpressionType.PROPERTY, "[nbt] tag %string% of %object%");
	}
	
	private Expression<String> tag;
	private Expression<Object> nbt;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		tag = (Expression<String>) expr[0];
		nbt = (Expression<Object>) expr[1];
		return true;
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE || mode == ChangeMode.RESET)  
			return CollectionUtils.array(Number.class, String.class, nbtBase);
		return null;
	}
	
	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		Object parsedNBT = nbt.getSingle(e);
		if(parsedNBT==null) return;
		String nbttag = tag.getSingle(e);
		if(mode == ChangeMode.SET) {
			Object value = args[0];
			SkriptNMS.getNMS().setNBTTag(parsedNBT, nbttag, value);
		}else if(mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
			SkriptNMS.getNMS().removeNBTTag(parsedNBT, nbttag);
		}
		
	}
	
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	
	@Override
	public String toString(Event e, boolean debug) {
		return tag.toString(e, debug) + " tag of " + nbt.toString(e, debug);
	}	
	
	@Override
	protected Object[] get(Event e) {
		Object tar = nbt.getSingle(e);
		if(tar==null || tar.toString().equals("{}")) return null;
		String nbttag = tag.getSingle(e);
		Object data = SkriptNMS.getNMS().getNBTTag(tar, nbttag);
		if(data == null) return null;
		return new Object[] { data };
	}

	@Override
	public boolean isSingle() {
		return true;
	}


}
