package me.limeglass.hda.elements.conditions;

import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDACondition;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Hologram is deleted")
@Description("Check if a hologram is deleted.")
@Patterns("holo[gra(m|phic display)] %hologram% (1¦is|2¦is(n't| not)) deleted")
public class CondDeleted extends HDACondition {

	public boolean check(Event event) {
		if (areNull(event)) return false;
		return (expressions.getSingle(event, Hologram.class).isDeleted()) ? isNegated() : !isNegated();
	}
}