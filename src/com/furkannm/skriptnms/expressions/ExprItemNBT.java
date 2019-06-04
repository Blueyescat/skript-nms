package com.furkannm.skriptnms.expressions;

import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.event.Event;

import com.furkannm.skriptnms.SkriptNMS;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Name("Item NBT")
@Examples({"give diamond with nbt \"{display:{Name:\"\"SkriptNMS\"\",Lore:[\"\"Hello\"\",\"\"World\"\"]},Unbreakable:1}\" to player"})

public class ExprItemNBT extends SimpleExpression<ItemType>{	
	
	static {
		Skript.registerExpression(ExprItemNBT.class, ItemType.class, ExpressionType.COMBINED, "%itemtype% with [custom] nbt[[ ]tag[s]] %string%");
	}
	
	private Expression<ItemType> itemStack;
	private Expression<String> string;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, ParseResult result) {
		itemStack = (Expression<ItemType>) expr[0];
		string = (Expression<String>) expr[1];
		return true;
	}
	
	@Override
	@Nullable
	protected ItemType[] get(Event e) {
		ItemType item = itemStack.getSingle(e);
		String newTags = string.getSingle(e);
		if(item.getItem().getRandom().getType()==Material.AIR || item==null) {
			Skript.error("Item is air or none! itemtype="+item.getItem()+",itemstack="+item.getItem().getRandom()+",type=" +(item!=null ? item.getItem().getRandom().getType() : ""));
			
			return null;
		}
		Object parsedNBT = null;
		parsedNBT = SkriptNMS.getNMS().parseRawNBT(newTags);
		ItemType newItem = new ItemType(SkriptNMS.getNMS().getItemWithNBT(item.getItem().getRandom(), parsedNBT));
		return new ItemType[] { newItem };
	}
	
	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends ItemType> getReturnType() {
		return ItemType.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return itemStack.toString(e, debug) + " with custom NBT " + string.toString(e, debug);
	}	
}
