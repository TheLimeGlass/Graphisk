package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.util.Date;
import me.limeglass.hda.lang.HDAPropertyExpression;
import me.limeglass.hda.utils.annotations.Properties;
import me.limeglass.hda.utils.annotations.PropertiesAddition;

@Name("Hologram creation")
@Description("Returns the date(s) hologram(s) were created at. Can be used in difference between syntax.")
@Properties({"holograms", "(time created|creat(ion|ed) (date|time[[ ]stamp]))"})
@PropertiesAddition("[the] [holo[gra(m|phic display)][s]]")
public class ExprHologramCreated extends HDAPropertyExpression<Hologram, Date> {

	@Override
	protected Date[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Date> times = new ArrayList<Date>();
		for (Hologram hologram : holograms) {
			times.add(new Date(hologram.getCreationTimestamp()));
		}
		return times.toArray(new Date[times.size()]);
	}
}