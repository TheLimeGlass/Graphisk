package me.limeglass.hda.elements.conditions;

import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDACondition;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Entity is hologram")
@Description("Check if an entity is a hologram.")
@Patterns("%entity% (1¦is|2¦is(n't| not)) [a] holo[gra(m|phic display)]")
public class CondIsEntity extends HDACondition {

	public boolean check(Event event) {
		if (areNull(event)) return false;
		return (HologramsAPI.isHologramEntity(expressions.getSingle(event, Entity.class))) ? isNegated() : !isNegated();
	}
}