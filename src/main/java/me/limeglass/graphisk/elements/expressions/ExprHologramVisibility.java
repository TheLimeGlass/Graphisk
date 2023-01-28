package me.limeglass.graphisk.elements.expressions;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram viewers")
@Description("A modifiable list of players that can view the hologram.")
@Since("2.0.0")
public class ExprHologramVisibility extends PropertyExpression<GraphiskHologram, Player> {

	static {
		register(ExprHologramVisibility.class, Player.class, "[all [of]] [the] (viewer[s]|visibil(e|ity) [list])", "holograms");
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends GraphiskHologram>) exprs[0]);
		return true;
	}

	@Override
	protected Player[] get(Event event, GraphiskHologram[] source) {
		return Arrays.stream(source).flatMap(hologram -> Bukkit.getOnlinePlayers().stream().filter(player -> hologram.isVisibleTo(player))).toArray(Player[]::new);
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "visibility list of holograms";
		return "visibility list of " + getExpr().toString(event, debug);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE)
			return null;
		return CollectionUtils.array(Player.class);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta == null)
			return;
		switch (mode) {
			case ADD:
				for (GraphiskHologram hologram : getExpr().getArray(event)) {
					for (Player player : (Player[])delta)
						hologram.setVisibleTo(true, player);
				}
				break;
			case REMOVE:
			case REMOVE_ALL:
				for (GraphiskHologram hologram : getExpr().getArray(event)) {
					for (Player player : (Player[])delta)
						hologram.setVisibleTo(false, player);
				}
				break;
			case SET:
				for (GraphiskHologram hologram : getExpr().getArray(event)) {
					for (Player player : (Player[])delta)
						hologram.setVisibleTo(true, player);
				}
				break;
			case RESET:
			case DELETE:
			default:
				break;
		}
	}

}
