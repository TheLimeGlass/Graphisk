package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAPropertyExpression;
import me.limeglass.hda.utils.annotations.Changers;
import me.limeglass.hda.utils.annotations.Properties;
import me.limeglass.hda.utils.annotations.PropertiesAddition;

@Name("Hologram location")
@Description("Returns the location(s) of the hologram(s).")
@Properties({"holograms", "[all [[of] the]] location[s]"})
@PropertiesAddition("[the] holo[gra(m|phic display)][s]")
@Changers(ChangeMode.SET)
public class ExprHologramLocation extends HDAPropertyExpression<Hologram, Location> {

	@Override
	protected Location[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Location> locations = new ArrayList<Location>();
		for (Hologram hologram : holograms) {
			locations.add(hologram.getLocation());
		}
		return locations.toArray(new Location[locations.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (isNull(event) || delta == null) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.teleport((Location) delta[0]);
		}
	}
}