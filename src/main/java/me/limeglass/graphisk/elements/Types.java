package me.limeglass.graphisk.elements;

import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import me.limeglass.graphisk.implementation.GraphiskHologram;

public class Types {

	static {
		Classes.registerClass(new ClassInfo<GraphiskHologram>(GraphiskHologram.class, "hologram")
				.user("holo(gram)?s?")
				.name("Holograms")
				.defaultExpression(new EventValueExpression<>(GraphiskHologram.class))
				.parser(new Parser<GraphiskHologram>() {

					@Override
					@Nullable
					public GraphiskHologram parse(String input, ParseContext context) {
						return null;
					}

					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public String toString(GraphiskHologram hologram, int flags) {
						return hologram.getName();
					}

					@Override
					public String toVariableNameString(GraphiskHologram hologram) {
						return toString(hologram, 0);
					}

				}));
	}

}
