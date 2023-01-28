package me.limeglass.graphisk.elements.conditions;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram is deleted")
@Description("Check if a hologram is deleted.")
@Since("2.0.0")
public class CondDeleted extends PropertyCondition<GraphiskHologram> {

	static {
		register(CondDeleted.class, "deleted", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "deleted";
	}

	@Override
	public boolean check(GraphiskHologram hologram) {
		return hologram.isDeleted();
	}

}
