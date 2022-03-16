package me.limeglass.hda.elements.expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.limeglass.hda.HolographicDisplaysAddon;

@Name("Create hologram")
@Description("Create a hologram at a location. It is returned as an expression.")
public class ExprHologram extends SimpleExpression<Hologram> {

	static {
		Skript.registerExpression(ExprHologram.class, Hologram.class, ExpressionType.SIMPLE, "[a] new holo[gra(m|phic display)[s]] at [the] %locations%");
	}

	private Expression<Location> locations;

	@Override
	public boolean isSingle() {
		return locations.isSingle();
	}

	@Override
	public Class<? extends Hologram> getReturnType() {
		return Hologram.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		locations = (Expression<Location>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "create hologram";
		return "create hologram at " + locations.toString(event, debug);
	}

	@Override
	protected @Nullable Hologram[] get(Event event) {
		HolographicDisplaysAddon instance = HolographicDisplaysAddon.getInstance();
		return locations.stream(event).map(location -> HologramsAPI.createHologram(instance, location)).toArray(Hologram[]::new);
	}

}
