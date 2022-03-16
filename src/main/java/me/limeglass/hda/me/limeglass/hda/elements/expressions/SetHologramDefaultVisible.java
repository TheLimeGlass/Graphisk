package me.limeglass.hda.elements.expressions;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.elements.lang.SetEffect;

@Name("Hologram visible default")
@Description("Boolean to determine if hologram(s) are visible by default.")
public class SetHologramDefaultVisible extends SetEffect<Hologram> {

	static {
		register(SetHologramDefaultVisible.class, "(default visib(ility|le)|visible by default)", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "visible by default";
	}

	@Override
	protected void execute(Event event) {
		apply(event, (hologram, visibility) -> hologram.getVisibilityManager().setVisibleByDefault(visibility));
	}

}
