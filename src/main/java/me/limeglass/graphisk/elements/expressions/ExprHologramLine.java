package me.limeglass.graphisk.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.entity.EntityData;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.limeglass.graphisk.Graphisk;
import me.limeglass.graphisk.implementation.GraphiskHologram;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramTextLine;
import me.limeglass.graphisk.implementation.HologramPlugin.Type;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramEntityLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramHeadLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramIconLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramItemLine;

@Name("Hologram Line")
@Description("Modify a line of a hologram.")
@Since("2.0.0")
public class ExprHologramLine extends SimpleExpression<Object> {

	static {
		if (Graphisk.getHologramPlugin().getPluginType() != Type.DECENT_HOLOGRAMS) {
			Skript.registerExpression(ExprHologramLine.class, Object.class, ExpressionType.SIMPLE, "[the] line %numbers% of [holo[gram[s]]] %holograms%");
		} else {
			Skript.registerExpression(ExprHologramLine.class, Object.class, ExpressionType.SIMPLE, "[the] line %numbers% [on page %number%] of [holo[gram[s]]] %holograms%");
		}
	}

	private Expression<GraphiskHologram> holograms;
	private Expression<Number> lines;

	@Nullable
	private Expression<Number> page;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		if (Graphisk.getHologramPlugin().getPluginType() != Type.DECENT_HOLOGRAMS) {
			holograms = (Expression<GraphiskHologram>) exprs[1];
			lines = (Expression<Number>) exprs[0];
		} else {
			holograms = (Expression<GraphiskHologram>) exprs[2];
			page = (Expression<Number>) exprs[1];
			lines = (Expression<Number>) exprs[0];
		}
		return true;
	}

	@Override
	@Nullable
	protected Object[] get(Event event) {
		return holograms.stream(event).flatMap(hologram -> lines.stream(event).map(Number::intValue).map(line -> {
			GraphiskHologramLine hologramLine = null;
			if (Graphisk.getHologramPlugin().getPluginType() == Type.DECENT_HOLOGRAMS && page != null) {
				hologramLine = hologram.getLine(page.getSingle(event).intValue(), line);
			} else {
				hologramLine = hologram.getLine(line);
			}
			if (hologramLine instanceof GraphiskHologramTextLine)
				return ((GraphiskHologramTextLine)hologramLine).getText();
			if (hologramLine instanceof GraphiskHologramEntityLine)
				return ((GraphiskHologramEntityLine)hologramLine).getEntityData();
			if (hologramLine instanceof GraphiskHologramHeadLine)
				return ((GraphiskHologramHeadLine)hologramLine).getHeadOwner();
			// Item line has to be last for subclasses to be first.
			if (hologramLine instanceof GraphiskHologramItemLine)
				return ((GraphiskHologramItemLine)hologramLine).getItemStack();
			return null;
		})).toArray(Object[]::new);
	}

	@Override
	public boolean isSingle() {
		return holograms.isSingle() && lines.isSingle();
	}

	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "lines of hologram";
		return "lines " + lines.toString(event, debug) + " of holograms " + holograms.toString(event, debug);
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (Graphisk.getHologramPlugin().getPluginType() == Type.DECENT_HOLOGRAMS) {
			return CollectionUtils.array(String[].class, ItemStack[].class, EntityData[].class, ItemType[].class);
		}
		return CollectionUtils.array(String[].class, ItemStack[].class);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta == null)
			return;
		int page = 0;
		if (this.page != null) {
			if (this.page.getSingle(event) == null)
				return;
			page = this.page.getSingle(event).intValue();
		}
		switch (mode) {
			case ADD:
				for (GraphiskHologram hologram : holograms.getArray(event)) {
					for (Number number : lines.getArray(event)) {
						int line = number.intValue();
						for (Object object : delta) {
							if (object instanceof String)
								hologram.insertTextLine(page, line, (String) object);
							if (object instanceof ItemStack)
								hologram.insertItemLine(page, line, (ItemStack) object);
							if (object instanceof EntityData)
								hologram.appendEntity(page, (EntityData<?>) object);
						}
					}
				}
				break;
			case RESET:
			case DELETE:
			case REMOVE:
			case REMOVE_ALL:
				for (GraphiskHologram hologram : holograms.getArray(event)) {
					for (Number line : lines.getArray(event))
						hologram.removeLine(page, line.intValue());
				}
				break;
			case SET:
				for (GraphiskHologram hologram : holograms.getArray(event)) {
					for (Number number : lines.getArray(event)) {
						int line = number.intValue();
						for (Object object : delta) {
							GraphiskHologramLine holoLine = hologram.getLine(page, line);
							if (holoLine == null) {
								if (object instanceof String) {
									hologram.appendText(page, (String) object);
								} else if (object instanceof ItemStack) {
									hologram.appendItemStack(page, (ItemStack) object);
								} else if (object instanceof EntityData) {
									hologram.appendEntity(page, (EntityData<?>) object);
								}
							} else {
								if (holoLine instanceof GraphiskHologramItemLine && object instanceof ItemStack) {
									((GraphiskHologramItemLine)holoLine).setItemStack((ItemStack) object);
								} else if (holoLine instanceof GraphiskHologramEntityLine && object instanceof EntityData) {
									((GraphiskHologramEntityLine)holoLine).setEntityData((EntityData<?>) object);
								} else if (holoLine instanceof GraphiskHologramHeadLine && object instanceof String) {
									((GraphiskHologramHeadLine)holoLine).setHeadOwner((String) object);
								} else if (holoLine instanceof GraphiskHologramIconLine && object instanceof ItemType) {
									((GraphiskHologramIconLine)holoLine).setIcon((ItemType) object);
								} else if (holoLine instanceof GraphiskHologramTextLine && object instanceof String) {
									((GraphiskHologramTextLine)holoLine).setText((String) object);
								}
							}
						}
					}
				}
				break;
			default:
				break;
		}
	}

}
