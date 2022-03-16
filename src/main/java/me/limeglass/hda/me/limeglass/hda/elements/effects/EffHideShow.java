package me.limeglass.hda.elements.effects;

import java.util.Collection;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

@Name("Show/Hide hologram")
@Description("Hide or show hologram(s) to player(s).")
public class EffHideShow extends Effect {

	static {
		Skript.registerEffect(EffHideShow.class, "(1¦show|2¦hide) holo[gra(m|phic display)][s] %holograms% [(to|from) %-players%]");
	}

	private Expression<Hologram> holograms;
	
	@Nullable
	private Expression<Player> players;
	private boolean hide;


	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[0];
		players = (Expression<Player>) exprs[1];
		hide = parseResult.mark == 2;
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "show/hide of holograms";
		return hide ? "hide" : "show" + " holograms " + holograms.toString(event, debug) + players == null ? "" : " to players " + players.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		Collection<Player> modify = (players == null ? Bukkit.getOnlinePlayers().stream() : players.stream(event)).collect(Collectors.toSet());
		for (Hologram hologram : holograms.getArray(event)) {
			for (Player player : modify) {
				if (hide)
					hologram.getVisibilityManager().hideTo(player);
				else
					hologram.getVisibilityManager().showTo(player);
			}
		}
	}
}