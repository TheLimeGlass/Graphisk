package me.limeglass.graphisk.elements;

import org.bukkit.Location;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;
import me.limeglass.graphisk.implementation.GraphiskHologram;

public class DefaultConverters {

	static {
		Converters.registerConverter(GraphiskHologram.class, Location.class, new Converter<>(){
			@Override
			public @Nullable Location convert(GraphiskHologram hologram) {
				return hologram.getLocation();
			}
		});
	}

}
