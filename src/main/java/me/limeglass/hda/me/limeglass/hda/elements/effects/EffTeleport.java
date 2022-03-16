package me.limeglass.hda.elements.effects;

import org.bukkit.Location;
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

@Name("Teleport Hologram")
@Description("teleport holograms to locations.")
public class EffTeleport extends Effect {

	static {
		Skript.registerEffect(EffTeleport.class, "teleport holo[gra(m|phic display)][s] %holograms% to %location%");
	}

	private Expression<Hologram> holograms;
	private Expression<Location> location;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[0];
		location = (Expression<Location>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "teleport holograms";
		return "teleport holograms " + holograms.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		Location location = this.location.getSingle(event);
		for (Hologram hologram : holograms.getArray(event))
			hologram.teleport(location);
	}

}
