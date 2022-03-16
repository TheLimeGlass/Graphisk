package me.limeglass.hda.elements.expressions;

import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.limeglass.hda.elements.lang.StupidSimplePropertyExpression;

@Name("Hologram height")
@Description("Returns the height(s) of the hologram(s). This is determined by how many lines it has. It's overall height.")
public class ExprHologramHeight extends SimplePropertyExpression<Hologram, Double> {

	static {
		//register(ExprHologramHeight.class, Double.class, "[all [of]] [the] holo[gra(m|phic display)[s] height[s]", "holograms");
		new StupidSimplePropertyExpression<Hologram, Double>("[all [of]] [the] holo[gra(m|phic display)[s] height") {
			@Override
			public @Nullable Double convert(Hologram hologram) {
				return hologram.getHeight();
			}
		}.plurals().register();
	}

	@Override
	public Class<? extends Double> getReturnType() {
		return Double.class;
	}

	@Override
	protected String getPropertyName() {
		return "hologram height";
	}

	@Override
	public @Nullable Double convert(Hologram hologram) {
		return hologram.getHeight();
	}

}
