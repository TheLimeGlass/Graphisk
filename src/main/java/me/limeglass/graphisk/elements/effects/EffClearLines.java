package me.limeglass.graphisk.elements.effects;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Clear hologram lines")
@Description("Clears the lines of hologram(s).")
@Since("2.0.0")
public class EffClearLines extends Effect {

	static {
		Skript.registerEffect(EffClearLines.class, "clear [all [of]] [the] lines of holo[gram][s] %holograms%");
	}

	private Expression<GraphiskHologram> holograms;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<GraphiskHologram>) exprs[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		for (GraphiskHologram hologram : holograms.getArray(event))
			hologram.clearLines();
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (debug)
			return "clear lines of holograms";
		return "clear lines of holograms " + holograms.toString(event, debug);
	}

}
