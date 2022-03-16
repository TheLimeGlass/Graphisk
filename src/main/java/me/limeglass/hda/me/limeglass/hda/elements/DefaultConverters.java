package me.limeglass.hda.elements;

import org.bukkit.Location;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;

public class DefaultConverters {

	static {
		Converters.registerConverter(Hologram.class, Location.class, new Converter<>(){
			@Override
			public @Nullable Location convert(Hologram hologram) {
				return hologram.getLocation();
			}
		});
	}

}
