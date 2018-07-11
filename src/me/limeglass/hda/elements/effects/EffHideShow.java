package me.limeglass.hda.elements.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Show/Hide hologram")
@Description("Hide or show hologram(s) to player(s).")
@Patterns("(1¦show|2¦hide) holo[gra(m|phic display)][s] %holograms% (to|from) %players%")
public class EffHideShow extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			for (Player player : expressions.getAll(event, Player.class)) {
				if (patternMark == 1) {
					hologram.getVisibilityManager().showTo(player);
				} else {
					hologram.getVisibilityManager().hideTo(player);
				}
			}
		}
	}
}