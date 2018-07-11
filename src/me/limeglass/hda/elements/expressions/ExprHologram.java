package me.limeglass.hda.elements.expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.HolographicDisplaysAddon;
import me.limeglass.hda.lang.HDAExpression;
import me.limeglass.hda.utils.annotations.Patterns;
import me.limeglass.hda.utils.annotations.RegisterType;
import me.limeglass.hda.utils.annotations.Single;

@Name("Create hologram")
@Description("Create a hologram at a location. It is returned as an expression.")
@Patterns("[a] new holo[gra(m|phic display)] at [the] %location%")
@RegisterType("hologram")
@Single
public class ExprHologram extends HDAExpression<Hologram> {
	
	@Override
	protected Hologram[] get(Event event) {
		if (areNull(event)) return null;
		return new Hologram[] {HologramsAPI.createHologram(HolographicDisplaysAddon.getInstance(), expressions.getSingle(event, Location.class))};
	}
}