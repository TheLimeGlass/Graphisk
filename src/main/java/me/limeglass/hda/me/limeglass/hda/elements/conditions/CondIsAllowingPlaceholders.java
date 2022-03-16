package me.limeglass.hda.elements.conditions;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.conditions.base.PropertyCondition;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;

@Name("Hologram Allowing Placeholders")
@Description("Check if the allow placeholders setting is set for holograms.")
public class CondIsAllowingPlaceholders extends PropertyCondition<Hologram> {

	static {
		register(CondIsDefaultVisible.class, "[allow[ing]] placeholders", "holograms");
	}

	@Override
	protected String getPropertyName() {
		return "allowing placeholders";
	}

	@Override
	public boolean check(Hologram hologram) {
		return hologram.isAllowPlaceholders();
	}

}

