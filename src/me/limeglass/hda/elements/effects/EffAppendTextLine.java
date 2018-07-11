package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Hologram append text line")
@Description("Add a line of text to the hologram(s).")
@Patterns("(append|add) text [line] %string% to holo[gra(m|phic display)][s] %holograms% [(in|at) line %-number%]")
public class EffAppendTextLine extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (isNull(event, Hologram.class)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			if (expressions.getSingle(event, Number.class) != null) {
				hologram.insertTextLine(expressions.getInt(event), expressions.getSingle(event, String.class));
			} else {
				hologram.appendTextLine(expressions.getSingle(event, String.class));
			}
		}
	}
}