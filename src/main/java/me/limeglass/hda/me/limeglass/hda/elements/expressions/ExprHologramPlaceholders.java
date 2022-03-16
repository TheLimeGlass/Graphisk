package me.limeglass.hda.elements.expressions;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.elements.lang.SetEffect;

@Name("Hologram placeholder toggle")
@Description("Returns a boolean that sets if the hologram(s) should support placeholders or not.")
public class ExprHologramPlaceholders extends SetEffect<Hologram> {

	static {
		register(ExprHologramPlaceholders.class, "[allow[ing]] placeholders", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "allowing placeholders";
	}

	@Override
	protected void execute(Event event) {
		apply(event, (hologram, placeholder) -> hologram.setAllowPlaceholders(placeholder));
	}

}
