package me.limeglass.graphisk.elements.expressions;

import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram Name")
@Description("Returns the names of the holograms.")
@Since("2.0.0")
public class ExprHologramName extends SimplePropertyExpression<GraphiskHologram, String> {

	static {
		register(ExprHologramName.class, String.class, "name[s]", "holograms");
	}

	@Override
	@Nullable
	public String convert(GraphiskHologram hologram) {
		return hologram.getName();
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	protected String getPropertyName() {
		return "name";
	}

}
