package com.furkannm.skriptnms.expressions;

import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.slot.Slot;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import com.furkannm.skriptnms.util.nms.NMS;
import com.furkannm.skriptnms.util.nms.types.NBTTagCompound;

@Name("NBT of")
@Examples({
		"on place of furnace:",
	"\tadd \"{BurnTime:100s}\" to nbt of event-block"
})

public class ExprNBTOf extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprNBTOf.class, Object.class, ExpressionType.PROPERTY, "nbt[[ ]tag[s]] of %~object%", "%~object%'s nbt[[ ]tag[s]]");
	}
	
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
		target = (Expression<Object>) expr[0];
		Class<?> type = target.getReturnType();
		if (type != Entity.class || type != Block.class || type != ItemStack.class || type != Slot.class) {
			Skript.error(target.toString() + " is not entity, block or itemstack.", ErrorQuality.SEMANTIC_ERROR);
		}
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return target.toString(e, debug) + "'s NBT value";
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		Object tar = target.getSingle(e);
		return NMS.getNBT(tar);
	}

	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		Object tar = target.getSingle(e);
		Object parsedNBT = null;
		if (args != null) {
			parsedNBT = NBTTagCompound.get().cast(NMS.parseRawNBT(((String) args[0])));
		}
		if (mode == ChangeMode.ADD) {
			NMS.addTargetsNBT(tar, parsedNBT);
		}else if (mode == ChangeMode.REMOVE) {
			NMS.removeTargetsNBT(tar, args);
		}else if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
			NMS.deleteTargetsNBT(tar);
		}
	}

	@Override
	public Class<?>[] acceptChange(final ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
			return CollectionUtils.array(String[].class);
		}
		return null;
}
	
}
