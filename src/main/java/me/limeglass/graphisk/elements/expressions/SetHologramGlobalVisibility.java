package me.limeglass.graphisk.elements.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import me.limeglass.graphisk.elements.lang.SetEffect;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram visible default")
@Description("Boolean to determine if hologram(s) are visible by default. This is for everyone.")
@Since("2.0.0")
public class SetHologramGlobalVisibility extends SetEffect<GraphiskHologram> {

	static {
		register(SetHologramGlobalVisibility.class, "(global visib(ility|le)|visible by default)", "holograms");
	}

	@Override
	protected void execute(Event event) {
		apply(event, (hologram, visibility) -> hologram.setVisible(visibility));
	}

	@Override
	protected String getPropertyName() {
		return "visible by default";
	}

}
