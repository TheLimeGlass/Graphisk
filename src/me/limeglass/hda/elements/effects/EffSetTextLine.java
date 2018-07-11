package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Hologram set line")
@Description("Set a line of text in the hologram(s).")
@Patterns("set text (for|in|of) holo[gra(m|phic display)][s] %holograms% to %string% (in|at) line %number%")
public class EffSetTextLine extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (isNull(event, Hologram.class)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.getLine(expressions.getInt(event)).removeLine();
			hologram.insertTextLine(expressions.getInt(event), expressions.getSingle(event, String.class));
		}
	}
}