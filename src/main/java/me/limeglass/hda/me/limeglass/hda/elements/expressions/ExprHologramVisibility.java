package me.limeglass.hda.elements.expressions;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Name("Hologram viewers")
@Description("A modifiable list of players that can view the hologram.")
public class ExprHologramVisibility extends PropertyExpression<Hologram, Player> {

	static {
		register(ExprHologramVisibility.class, Player.class, "[all [of]] [the] (viewer[s]|visibil(e|ity) [list])", "holograms");
	}

	@Override
	public Class<? extends Player> getReturnType() {
		return Player.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		setExpr((Expression<? extends Hologram>) exprs[0]);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "visibility list of holograms";
		return "visibility list of " + getExpr().toString(event, debug);
	}

	@Override
	protected Player[] get(Event event, Hologram[] source) {
		return Arrays.stream(source).flatMap(hologram -> Bukkit.getOnlinePlayers().stream().filter(player -> hologram.getVisibilityManager().isVisibleTo(player))).toArray(Player[]::new);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		return CollectionUtils.array(Player.class);
	}

	@Override
	public void change(Event event, Object[] delta, ChangeMode mode) {
		if (delta == null)
			return;
		switch (mode) {
			case ADD:
				for (Hologram hologram : getExpr().getArray(event)) {
					for (Player player : (Player[])delta)
						hologram.getVisibilityManager().showTo(player);
				}
				break;
			case RESET:
			case DELETE:
				for (Hologram hologram : getExpr().getArray(event))
					hologram.getVisibilityManager().resetVisibilityAll();
				break;
			case REMOVE:
			case REMOVE_ALL:
				for (Hologram hologram : getExpr().getArray(event)) {
					for (Player player : (Player[])delta)
						hologram.getVisibilityManager().hideTo(player);
				}
				break;
			case SET:
				for (Hologram hologram : getExpr().getArray(event)) {
					hologram.getVisibilityManager().resetVisibilityAll();
					for (Player player : (Player[])delta)
						hologram.getVisibilityManager().showTo(player);
				}
				break;
			default:
				break;
		}
	}

}
