package com.furkannm.skriptnms.expressions;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.slot.Slot;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import com.furkannm.skriptnms.SkriptNMS;
import com.furkannm.skriptnms.nms.NMS;

@Name("NBT of")
@Examples({"on place of furnace:\n\tadd \"{BurnTime:100s}\" to nbt of event-block"})

public class ExprNBTOf extends PropertyExpression<Object,Object> {

	private Class<?> returnType = SkriptNMS.getNMS().getCompoundClass();
	static {
		register(ExprNBTOf.class, Object.class, "nbt [tag[s]]", "objects");
		//register(ExprNBTOf.class, Object.class, "nbt [tag[s]]", "compounds");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		setExpr((Expression<Object>) expr[0]);
		Class<?> type = getExpr().getReturnType();
		if (type != Entity.class && type != Block.class && type != ItemStack.class && type != Slot.class && type != ItemType.class)
			Skript.error(getExpr().toString() + " is not entity, block or itemstack.", ErrorQuality.SEMANTIC_ERROR);
		return true;
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE || mode == ChangeMode.RESET)  
			return CollectionUtils.array(String[].class);
		return null;
	}
	
	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		Object tar = getExpr().getSingle(e);
		if(tar instanceof ItemType) {
			tar = ((ItemType)tar).getItem().getRandom();
		}
		Object parsedNBT = null;
		if (args != null) parsedNBT = returnType.cast(SkriptNMS.getNMS().parseRawNBT(((String) args[0])));
		
		if (mode == ChangeMode.ADD) SkriptNMS.getNMS().addTargetsNBT(tar, parsedNBT);
		if (mode == ChangeMode.REMOVE) SkriptNMS.getNMS().removeTargetsNBT(tar, args);
		if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET) SkriptNMS.getNMS().deleteTargetsNBT(tar);
	}
	
	@Override
	public Class<? extends Object> getReturnType() {
		return returnType;
	}
	
	@Override
	public String toString(Event e, boolean debug) {
		return getExpr().toString(e, debug) + "'s NBT value";
	}	
	
	@Override
	protected Object[] get(Event e, Object[] source) {
		Object tar = getExpr().getSingle(e);
		if(tar instanceof ItemType) {
			tar = ((ItemType)tar).getItem().getRandom();
		}
		return SkriptNMS.getNMS().getNBT(tar);
	}
}
