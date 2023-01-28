package me.limeglass.graphisk.elements.effects;

import org.bukkit.Location;
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

@Name("Teleport Hologram")
@Description("teleport holograms to locations.")
@Since("2.0.0")
public class EffTeleport extends Effect {

	static {
		Skript.registerEffect(EffTeleport.class, "teleport holo[gram][s] %holograms% to %location%");
	}

	private Expression<GraphiskHologram> holograms;
	private Expression<Location> location;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<GraphiskHologram>) exprs[0];
		location = (Expression<Location>) exprs[1];
		return true;
	}

	@Override
	protected void execute(Event event) {
		Location location = this.location.getSingle(event);
		for (GraphiskHologram hologram : holograms.getArray(event))
			hologram.teleport(location);
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "teleport holograms";
		return "teleport holograms " + holograms.toString(event, debug);
	}

}
