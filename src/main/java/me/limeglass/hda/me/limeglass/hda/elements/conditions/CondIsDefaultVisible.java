package me.limeglass.hda.elements.conditions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;

@Name("Hologram Default Visibility")
@Description("Check if the default visibility is set for holograms.")
public class CondIsDefaultVisible extends PropertyCondition<Hologram> {

	static {
		register(CondIsDefaultVisible.class, "(default visible|visible by default)", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "visible by default";
	}

	@Override
	public boolean check(Hologram hologram) {
		return hologram.getVisibilityManager().isVisibleByDefault();
	}

}
