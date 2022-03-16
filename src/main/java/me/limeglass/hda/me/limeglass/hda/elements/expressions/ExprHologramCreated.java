package me.limeglass.hda.elements.expressions;

import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.util.Date;

@Name("Hologram creation")
@Description("Returns the date(s) hologram(s) were created at. Can be used in difference between syntax.")
public class ExprHologramCreated extends SimplePropertyExpression<Hologram, Date> {

	static {
		register(ExprHologramCreated.class, Date.class, "[all [of]] [the] (time created|creat(ion|ed) (date|time[[ ]stamp]))", "holograms");
	}

	@Override
	public Class<? extends Date> getReturnType() {
		return Date.class;
	}

	@Override
	protected String getPropertyName() {
		return "time created";
	}

	@Override
	public @Nullable Date convert(Hologram hologram) {
		return new Date(hologram.getCreationTimestamp());
	}

}
