package me.limeglass.hda.elements;

import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;

public class Types {

	static {
		Classes.registerClass(new ClassInfo<Hologram>(Hologram.class, "hologram")
				.user("holo(gram)?s?")
				.name("Holograms")
				.defaultExpression(new EventValueExpression<>(Hologram.class))
				.parser(new Parser<Hologram>() {

					@Override
					@Nullable
					public Hologram parse(String input, ParseContext context) {
						return null;
					}

					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(Hologram hologram, int flags) {
						return hologram.getLocation().toString();
					}

					@Override
					public String toVariableNameString(Hologram hologram) {
						return toString(hologram, 0);
					}

				}));
	}

}
