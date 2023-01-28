package me.limeglass.graphisk.elements.expressions;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.limeglass.graphisk.Graphisk;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("All Holograms")
@Description("Grabs all the holograms currently registered")
@Since("2.0.0")
public class ExprHolograms extends SimpleExpression<GraphiskHologram> {

	static {
		Skript.registerExpression(ExprHolograms.class, GraphiskHologram.class, ExpressionType.SIMPLE, "[all [of]] [the] holo[gram]s");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		return true;
	}

	@Override
	@Nullable
	protected GraphiskHologram[] get(Event event) {
		return Graphisk.getHologramPlugin().getHolograms().stream().toArray(GraphiskHologram[]::new);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends GraphiskHologram> getReturnType() {
		return GraphiskHologram.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "all of the holograms";
	}

}
