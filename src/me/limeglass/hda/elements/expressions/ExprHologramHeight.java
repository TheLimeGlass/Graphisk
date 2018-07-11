package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAPropertyExpression;
import me.limeglass.hda.utils.annotations.Properties;
import me.limeglass.hda.utils.annotations.PropertiesAddition;

@Name("Hologram height")
@Description("Returns the height(s) of the hologram(s). This is determined by how many lines it has. It's overall height.")
@Properties({"holograms", "[all [[of] the]] height[s]"})
@PropertiesAddition("[the] holo[gra(m|phic display)][s]")
public class ExprHologramHeight extends HDAPropertyExpression<Hologram, Number> {

	@Override
	protected Number[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Number> heights = new ArrayList<Number>();
		for (Hologram hologram : holograms) {
			heights.add(hologram.getHeight());
		}
		return heights.toArray(new Number[heights.size()]);
	}
}