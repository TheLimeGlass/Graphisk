package me.limeglass.hda.elements.conditions;

import org.bukkit.entity.Entity;

import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;

@Name("Entity is hologram")
@Description("Check if an entity is a hologram.")
public class CondIsHolo extends PropertyCondition<Entity> {

	static {
		register(CondIsHolo.class, "[a] holo[gra(m|phic display)]", "entities");
	}

	@Override
	public boolean check(Entity entity) {
		return HologramsAPI.isHologramEntity(entity);
	}

	@Override
	protected String getPropertyName() {
		return "a holo";
	}

}
