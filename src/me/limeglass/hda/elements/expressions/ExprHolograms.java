package me.limeglass.hda.elements.expressions;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import me.limeglass.hda.HolographicDisplaysAddon;
import me.limeglass.hda.lang.HDAExpression;
import me.limeglass.hda.utils.annotations.ExpressionProperty;
import me.limeglass.hda.utils.annotations.Patterns;

@Name("Holograms hologram")
@Description("Grabs all the holograms currently registered. If the plugins string is not set, it will grab all the holograms registered under HolographicDisplaysAddon")
@Patterns("[(all [[of] the]|the)] holo[gra(m|phic display)]s [[(under|for)] plugin[s] %-strings%]")
@ExpressionProperty(ExpressionType.SIMPLE)
public class ExprHolograms extends HDAExpression<Hologram> {
	
	@Override
	protected Hologram[] get(Event event) {
		if (!areNull(event)) {
			ArrayList<Hologram> holograms = new ArrayList<Hologram>();
			for (String plugin : expressions.getAll(event, String.class)) {
				if (Bukkit.getPluginManager().getPlugin(plugin) != null) {
					holograms.addAll(HologramsAPI.getHolograms(Bukkit.getPluginManager().getPlugin(plugin)));
				}
			}
			return holograms.toArray(new Hologram[holograms.size()]);
		}
		Collection<Hologram> holograms = HologramsAPI.getHolograms(HolographicDisplaysAddon.getInstance());
		return holograms.toArray(new Hologram[holograms.size()]);
	}
}