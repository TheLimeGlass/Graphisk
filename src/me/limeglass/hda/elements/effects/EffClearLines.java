package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Clear hologram lines")
@Description("Clears the lines of hologram(s).")
@Patterns("clear [the] lines of holo[gra(m|phic display)][s] %holograms%")
public class EffClearLines extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.clearLines();
		}
	}
}