package me.limeglass.hda.elements.expressions;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Name("Hologram Line")
@Description("Modify a line of a hologram.")
public class ExprHologramLine extends SimpleExpression<Object> {

	static {
		Skript.registerExpression(ExprHologramLine.class, Object.class, ExpressionType.SIMPLE, "[the] line %numbers% of [holo[gra(m|phic display)[s]]] %holograms%");
	}

	private Expression<Hologram> holograms;
	private Expression<Number> lines;

	@Override
	public boolean isSingle() {
		return holograms.isSingle() && lines.isSingle();
	}

	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[1];
		lines = (Expression<Number>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "lines of hologram";
		return "lines " + lines.toString(event, debug) + " of holograms " + holograms.toString(event, debug);
	}

	@Override
	protected @Nullable Object[] get(Event event) {
		return holograms.stream(event).flatMap(hologram -> lines.stream(event).map(line -> {
			HologramLine holoLine = hologram.getLine(line.intValue());
			if (holoLine instanceof TextLine)
				return ((TextLine)holoLine).getText();
			if (holoLine instanceof ItemLine)
				return ((ItemLine)holoLine).getItemStack();
			return null;
		})).toArray(Object[]::new);
	}

	@Override
	@Nullable
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(String.class, String[].class, ItemStack.class, ItemStack[].class);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta == null)
			return;
		switch (mode) {
			case ADD:
				for (Hologram hologram : holograms.getArray(event)) {
					for (Number line : lines.getArray(event)) {
						for (Object object : delta) {
							if (object instanceof String)
								hologram.insertTextLine(line.intValue(), (String) object);
							if (object instanceof ItemStack)
								hologram.insertItemLine(line.intValue(), (ItemStack) object);
						}
					}
				}
				break;
			case RESET:
			case DELETE:
			case REMOVE:
			case REMOVE_ALL:
				for (Hologram hologram : holograms.getArray(event)) {
					for (Number line : lines.getArray(event))
						hologram.removeLine(line.intValue());
				}
				break;
			case SET:
				for (Hologram hologram : holograms.getArray(event)) {
					for (Number line : lines.getArray(event)) {
						for (Object object : delta) {
							HologramLine holoLine = hologram.getLine(line.intValue());
							if (holoLine instanceof TextLine && object instanceof String)
								((TextLine)holoLine).setText((String) object);
							if (holoLine instanceof ItemLine && object instanceof ItemStack)
								((ItemLine)holoLine).setItemStack((ItemStack) object);
						}
					}
				}
				break;
			default:
				break;
		}
	}

}
