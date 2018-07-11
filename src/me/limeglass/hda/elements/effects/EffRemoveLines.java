package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Remove hologram line")
@Description("Removes specific line(s) from hologram(s).")
@Patterns("(delete|remove|clear) [the] lines %numbers% (from|in) holo[gra(m|phic display)][s] %holograms%")
public class EffRemoveLines extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			for (Number number : expressions.getAll(event, Number.class)) {
				if (number.intValue() < hologram.size()) {
					hologram.getLine(number.intValue()).removeLine();
				}
			}
		}
	}
}