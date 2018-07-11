package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import me.limeglass.hda.lang.HDAPropertyExpression;
import me.limeglass.hda.utils.annotations.AllChangers;
import me.limeglass.hda.utils.annotations.Multiple;
import me.limeglass.hda.utils.annotations.Properties;
import me.limeglass.hda.utils.annotations.PropertiesAddition;

@Name("Hologram viewers")
@Description("A modifiable list of players that can view the hologram.")
@Properties({"hologram", "[all [[of] the]] (viewer[s]|visibil(e|ity) [list])"})
@PropertiesAddition("[the] holo[gra(m|phic display)][s]")
@AllChangers
@Multiple
public class ExprHologramVisibility extends HDAPropertyExpression<Hologram, Player> {

	@Override
	protected Player[] get(Event event, Hologram[] holograms) {
		if (isNull(event)) return null;
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (expressions.getSingle(event, Hologram.class).getVisibilityManager().isVisibleTo(player)) {
				players.add(player);
			}
		}
		return players.toArray(new Player[players.size()]);
	}
	
	@Override
	public void change(Event event, Object[] delta, ChangeMode mode){
		if (isNull(event) || delta == null) return;
		Player[] players = (Player[]) delta;
		VisibilityManager visibilityManager = expressions.getSingle(event, Hologram.class).getVisibilityManager();
		switch (mode) {
			case ADD:
				for (Player player : players) {
					visibilityManager.showTo(player);
				}
				break;
			case DELETE:
				visibilityManager.resetVisibilityAll();
				break;
			case REMOVE:
				for (Player player : players) {
					visibilityManager.hideTo(player);
				}
				break;
			case REMOVE_ALL:
				visibilityManager.resetVisibilityAll();
				break;
			case RESET:
				visibilityManager.resetVisibilityAll();
				break;
			case SET:
				visibilityManager.resetVisibilityAll();
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (!Arrays.asList(players).contains(player)) {
						visibilityManager.hideTo(player);
					}
				}
				break;
			default:
				break;
		}
	}
}