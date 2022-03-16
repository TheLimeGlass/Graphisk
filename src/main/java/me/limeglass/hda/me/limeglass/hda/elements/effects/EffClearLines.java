package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Clear hologram lines")
@Description("Clears the lines of hologram(s).")
public class EffClearLines extends Effect {

	static {
		Skript.registerEffect(EffClearLines.class, "clear [all [of]] [the] lines of holo[gra(m|phic display)][s] %holograms%");
	}

	private Expression<Hologram> holograms;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "clean lines of holograms";
		return "clean lines of holograms " + holograms.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		for (Hologram hologram : holograms.getArray(event))
			hologram.clearLines();
	}

}
