package me.limeglass.hda.elements.conditions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;

@Name("Hologram is deleted")
@Description("Check if a hologram is deleted.")
public class CondDeleted extends PropertyCondition<Hologram> {

	static {
		register(CondDeleted.class, "deleted", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "deleted";
	}

	@Override
	public boolean check(Hologram hologram) {
		return hologram.isDeleted();
	}

}
