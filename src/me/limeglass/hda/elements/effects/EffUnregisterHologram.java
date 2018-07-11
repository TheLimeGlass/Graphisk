package me.limeglass.hda.elements.effects;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAEffect;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Unregister hologram")
@Description("Unregister/Delete hologram(s) from existance.")
@Patterns("(unregister|delete|remove) holo[gra(m|phic display)][s] %holograms%")
public class EffUnregisterHologram extends HDAEffect {

	@Override
	protected void execute(Event event) {
		if (areNull(event)) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.delete();
		}
	}
}