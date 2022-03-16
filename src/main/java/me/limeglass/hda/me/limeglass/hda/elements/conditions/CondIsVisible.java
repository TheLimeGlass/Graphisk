package me.limeglass.hda.elements.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Checker;
import ch.njol.util.Kleenean;

@Name("Hologram Visible To Players")
@Description("Check if holograms are visible to players.")
public class CondIsVisible extends Condition {

	static {
		Skript.registerCondition(CondIsVisible.class, "%holograms% (is|are) visible to %players%", "%holograms% (isn't|is not|aren't|are not) visible to %players%");
	}

	private Expression<Hologram> holograms;
	private Expression<Player> players;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[0];
		players = (Expression<Player>) exprs[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "visibility of holograms";
		return "is visible from " + holograms.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		return holograms.check(event, new Checker<>() {
			@Override
			public boolean check(Hologram hologram) {
				return players.check(event, new Checker<>() {
					@Override
					public boolean check(Player player) {
						return hologram.getVisibilityManager().isVisibleTo(player);
					}
				});
			}
		}, isNegated());
	}

}
