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

@Name("Unregister hologram")
@Description("Unregister/Delete hologram(s) from existance.")
public class EffUnregisterHologram extends Effect {

	static {
		Skript.registerEffect(EffUnregisterHologram.class, "unregister [holo[gra(m|phic display)][s]] %holograms%");
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
		return "unregister holograms";
	}

	@Override
	protected void execute(Event event) {
		for (Hologram hologram : holograms.getArray(event))
			hologram.delete();
	}

}
