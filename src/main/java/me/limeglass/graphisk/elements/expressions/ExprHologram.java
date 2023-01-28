package me.limeglass.graphisk.elements.expressions;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.limeglass.graphisk.Graphisk;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Create hologram")
@Description("Create a hologram at a location.")
@Examples("set {_hologram} to a new hologram named \"example\" at player's location")
@Since("2.0.0")
public class ExprHologram extends SimpleExpression<GraphiskHologram> {

	static {
		Skript.registerExpression(ExprHologram.class, GraphiskHologram.class, ExpressionType.SIMPLE, "[create] [a] [new] holo[gram] named %string% at %location%");
	}

	private Expression<Location> location;
	private Expression<String> name;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		name = (Expression<String>) exprs[0];
		location = (Expression<Location>) exprs[1];
		return true;
	}

	@Override
	@Nullable
	protected GraphiskHologram[] get(Event event) {
		String name = this.name.getSingle(event);
		Location location = this.location.getSingle(event);
		if (name == null || location == null)
			return null;
		return new GraphiskHologram[] {Graphisk.getHologramPlugin().createHologram(name, location)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends GraphiskHologram> getReturnType() {
		return GraphiskHologram.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "create hologram named " + name.toString(event, debug) + " at " + location.toString(event, debug);
	}

}
