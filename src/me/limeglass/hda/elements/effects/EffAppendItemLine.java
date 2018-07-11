package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Hologram append item line")
@Description("Add a hologram line of an item to the hologram(s).")
@Patterns("(append|add) item [line] %itemtypes% to holo[gra(m|phic display)][s] %holograms% [(in|at) line %-number%]")
public class EffAppendItemLine extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (isNull(event, Hologram.class)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			if (expressions.getSingle(event, Number.class) != null) {
				hologram.insertItemLine(expressions.getInt(event), expressions.getSingle(event, ItemType.class).getRandom());
			} else {
				hologram.appendItemLine(expressions.getSingle(event, ItemType.class).getRandom());
			}
		}
	}
}