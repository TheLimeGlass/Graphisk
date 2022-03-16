package me.limeglass.hda.elements.expressions;

import java.util.Arrays;

import org.bukkit.Bukkit;
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

@Name("Holograms hologram")
@Description("Grabs all the holograms currently registered. If the plugins string is not set, it will grab all the holograms registered under HolographicDisplaysAddon")
public class ExprHolograms extends SimpleExpression<Hologram> {

	static {
		Skript.registerExpression(ExprHolograms.class, Hologram.class, ExpressionType.SIMPLE, "[all [of]] [the] holo[gra(m|phic display)]s [[(under|for)] plugin[s] %-strings%]");
	}

	@Nullable
	private Expression<String> plugins;

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Hologram> getReturnType() {
		return Hologram.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		plugins = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "all of the holograms";
	}

	@Override
	protected @Nullable Hologram[] get(Event event) {
		if (plugins != null)
			return Arrays.stream(Bukkit.getPluginManager().getPlugins())
					.flatMap(plugin -> HologramsAPI.getHolograms(plugin).stream()).toArray(Hologram[]::new);
		return HologramsAPI.getHolograms(HolographicDisplaysAddon.getInstance()).toArray(Hologram[]::new);
	}

}
