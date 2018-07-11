package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAPropertyExpression;
import me.limeglass.hda.utils.annotations.Properties;
import me.limeglass.hda.utils.annotations.PropertiesAddition;
import me.limeglass.hda.utils.annotations.Changers;

@Name("Hologram placeholder toggle")
@Description("Returns a boolean that sets if the hologram(s) should support placeholders or not.")
@Properties({"holograms", "[allowing] placeholders"})
@PropertiesAddition("[the] holo[gra(m|phic display)][s]")
@Changers(ChangeMode.SET)
public class ExprHologramPlaceholders extends HDAPropertyExpression<Hologram, Boolean> {

	@Override
	protected Boolean[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Boolean> allowing = new ArrayList<Boolean>();
		for (Hologram hologram : holograms) {
			allowing.add(hologram.isAllowPlaceholders());
		}
		return allowing.toArray(new Boolean[allowing.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode){
		if (isNull(event) || delta == null) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.setAllowPlaceholders((Boolean) delta[0]);
		}
	}
}