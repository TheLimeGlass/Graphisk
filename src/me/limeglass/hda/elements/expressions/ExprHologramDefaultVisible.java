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

@Name("Hologram visible default")
@Description("Boolean to determine if hologram(s) are visible by default.")
@Properties({"holograms", "(default visib(ility|le)|visible by default)"})
@PropertiesAddition("[the] [holo[gra(m|phic display)][s]]")
@Changers(ChangeMode.SET)
public class ExprHologramDefaultVisible extends HDAPropertyExpression<Hologram, Boolean> {

	@Override
	protected Boolean[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Boolean> visible = new ArrayList<Boolean>();
		for (Hologram hologram : holograms) {
			visible.add(hologram.getVisibilityManager().isVisibleByDefault());
		}
		return visible.toArray(new Boolean[visible.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode){
		if (isNull(event) || delta == null) return;
		for (Hologram hologram : expressions.getAll(event, Hologram.class)) {
			hologram.getVisibilityManager().setVisibleByDefault((Boolean) delta[0]);
		}
	}
}