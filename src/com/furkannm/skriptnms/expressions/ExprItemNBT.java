package com.furkannm.skriptnms.expressions;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.furkannm.skriptnms.SkriptNMS;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("Item NBT")
@Examples({"give diamond with nbt \"{display:{Name:\"SkriptNMS\",Lore:[\"Hello\",\"World\"]},Unbreakable:1}\" to player"})

public class ExprItemNBT extends SimpleExpression<ItemStack>{	
	
	static {
		Skript.registerExpression(ExprItemNBT.class, ItemStack.class, ExpressionType.PROPERTY, "%itemstack% with [custom] nbt[[ ]tag[s]] %string%");
	}
	
	private Expression<ItemStack> itemStack;
	private Expression<String> string;
	
	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		itemStack = (Expression<ItemStack>) expr[0];
		string = (Expression<String>) expr[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return itemStack.toString(e, debug) + " with custom NBT " + string.toString(e, debug);
	}

	@Override
	@Nullable
	protected ItemStack[] get(Event e) {
		ItemStack item = itemStack.getSingle(e);
		String newTags = string.getSingle(e);
		if(item.getType()==Material.AIR || item==null) return null;
		Object parsedNBT = null;
		parsedNBT = SkriptNMS.getNMS().parseRawNBT(newTags);
		ItemStack newItem = SkriptNMS.getNMS().getItemWithNBT(item, parsedNBT);
		return new ItemStack[] { newItem };
	}
	
}
