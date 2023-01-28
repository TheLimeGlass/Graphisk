package me.limeglass.graphisk.elements.expressions;

import org.bukkit.Location;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram Location")
@Description("Returns the locations of the holograms.")
@Since("2.0.0")
public class ExprHologramLocation extends SimplePropertyExpression<GraphiskHologram, Location> {

	static {
		register(ExprHologramLocation.class, Location.class, "location", "holograms");
	}

	@Override
	@Nullable
	public Location convert(GraphiskHologram hologram) {
		return hologram.getLocation();
	}

	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}

	@Override
	protected String getPropertyName() {
		return "location";
	}

}
