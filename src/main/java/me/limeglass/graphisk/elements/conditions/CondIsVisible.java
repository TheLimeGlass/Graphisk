package me.limeglass.graphisk.elements.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram Visible")
@Description("Check if holograms are visible to players.")
@Since("2.0.0")
public class CondIsVisible extends Condition {

	static {
		Skript.registerCondition(CondIsVisible.class, "%holograms% (is|are) visible to %players%", "%holograms% (isn't|is not|aren't|are not) visible to %players%");
	}

	private Expression<GraphiskHologram> holograms;
	private Expression<Player> players;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<GraphiskHologram>) exprs[0];
		players = (Expression<Player>) exprs[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public boolean check(Event event) {
		return holograms.check(event, new Checker<>() {
			@Override
			public boolean check(GraphiskHologram hologram) {
				if (hologram.isVisible())
					return true;
				return players.check(event, new Checker<>() {
					@Override
					public boolean check(Player player) {
						return hologram.isVisibleTo(player);
					}
				});
			}
		}, isNegated());
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (debug)
			return "visibility of holograms";
		return "holograms " + holograms.toString(event, debug) + (isNegated() ? " is not " : " is ") + "visible to " + players.toString(event, debug);
	}

}
