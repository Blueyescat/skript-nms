package com.furkannm.skriptnms.expressions;

import java.util.Arrays;

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
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.util.slot.Slot;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import com.furkannm.skriptnms.util.nms.NBTTagCompound;
import com.furkannm.skriptnms.util.nms.NMS;

@Name("NBT of")
@Examples({
		"on place of furnace:",
	"\tadd \"{BurnTime:100s}\" to nbt of event-block"
})

public class ExprNBTOf extends SimpleExpression<Object> {

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
		if (tar instanceof Entity) {
			return new Object[] { NBTTagCompound.get().cast(NMS.getEntityNBT((Entity) tar)) };
		} else if (tar instanceof Block) {
			return new Object[] { NBTTagCompound.get().cast(NMS.getTileNBT((Block) tar)) };
		} else if (tar instanceof ItemStack) {
			return new Object[] { NBTTagCompound.get().cast(NMS.getItemNBT((ItemStack) tar)) };
		} else if (tar instanceof Slot) {
			return new Object[] { NBTTagCompound.get().cast(NMS.getItemNBT(((Slot) tar).getItem())) };
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] args, ChangeMode mode) {
		Object tar = target.getSingle(e);
		Object parsedNBT = null;
		if (args != null) {
			parsedNBT = NBTTagCompound.get().cast(NMS.parseRawNBT(((String) args[0])));
		}
		if (tar instanceof Entity) {
			Object entNBT = NBTTagCompound.get().cast(NMS.getEntityNBT((Entity) tar));
			if (mode == ChangeMode.ADD) {
				NMS.removeFromCompound(NBTTagCompound.get().cast(parsedNBT), "UUIDMost", "UUIDLeast", "WorldUUDMost", "WorldUUIDLeast", "Bukkit.updateLevel");
				NMS.addToCompound(NBTTagCompound.get().cast(entNBT), NBTTagCompound.get().cast(parsedNBT));
				NMS.setEntityNBT((Entity) tar, NBTTagCompound.get().cast(entNBT));
			} else if (mode == ChangeMode.REMOVE) {
				for (Object s : args) {
					if (s != "UUIDMost" || s != "UUIDLeast" || s != "WorldUUIDMost" || s != "WorldUUIDLeast" || s != "Bukkit.updateLevel") { 
						NMS.removeFromCompound(NBTTagCompound.get().cast(entNBT), (String) s);
					}
				}
				NMS.setEntityNBT((Entity) tar, NBTTagCompound.get().cast(entNBT));
			}
		} else if (tar instanceof Block) {
			Object blockNBT = NBTTagCompound.get().cast(NMS.getTileNBT((Block) tar));
			if (mode == ChangeMode.ADD) {
				NMS.removeFromCompound(NBTTagCompound.get().cast(parsedNBT), "x", "y", "z", "id");
				NMS.addToCompound(NBTTagCompound.get().cast(blockNBT), NBTTagCompound.get().cast(parsedNBT));
				NMS.setTileNBT((Block) tar, NBTTagCompound.get().cast(blockNBT));
			} else if (mode == ChangeMode.REMOVE) {
				for (Object s : args) {
					if (s != "x" || s != "y" || s != "z" || s != "id") {
						NMS.removeFromCompound(NBTTagCompound.get().cast(blockNBT), (String) s);
					}
				}
				NMS.setTileNBT((Block) tar, NBTTagCompound.get().cast(blockNBT));
			}
		} else if (tar instanceof ItemStack) {
			if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
				Skript.warning("Failed to change the NBT of an item: Itemstack didn't have any slot attached to it.");
			}
		} else if (tar instanceof Slot) {
			ItemStack slotItem = ((Slot) tar).getItem();
			Object itemNBT = NBTTagCompound.get().cast(NMS.getItemNBT(slotItem));
			if (mode == ChangeMode.ADD) {
				NMS.addToCompound(NBTTagCompound.get().cast(itemNBT), NBTTagCompound.get().cast(parsedNBT));
				ItemStack newItem = NMS.getItemWithNBT(slotItem, NBTTagCompound.get().cast(itemNBT));
				((Slot) tar).setItem(newItem);
			} else if (mode == ChangeMode.REMOVE) {
				String[] toRemove = Arrays.copyOf(args, args.length, String[].class);
				NMS.removeFromCompound(itemNBT, toRemove);
				ItemStack newItem = NMS.getItemWithNBT(slotItem, NBTTagCompound.get().cast(itemNBT));
				((Slot) tar).setItem(newItem);
			} else if (mode == ChangeMode.DELETE || mode == ChangeMode.RESET) {
				ItemStack newItem = NMS.getItemWithNBT(slotItem, NBTTagCompound.get().cast(null));
				((Slot) tar).setItem(newItem);
			}
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
