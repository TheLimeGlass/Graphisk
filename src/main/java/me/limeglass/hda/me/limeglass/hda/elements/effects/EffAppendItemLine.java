package me.limeglass.hda.elements.effects;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Name("Hologram append item line")
@Description("Add a hologram line of an item to the hologram(s).")
public class EffAppendItemLine extends Effect {

	static {
		Skript.registerEffect(EffAppendItemLine.class, "(append|add) item [line] %itemstacks% to holo[gra(m|phic display)][s] %holograms% [(in|at) line %-numbers%]");
	}

	private Expression<Hologram> holograms;
	private Expression<ItemStack> items;
	
	@Nullable
	private Expression<Number> lines;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<Hologram>) exprs[1];
		items = (Expression<ItemStack>) exprs[0];
		lines = (Expression<Number>) exprs[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "add item to hologram";
		return "add item " + items.toString(event, debug) + " to " + holograms.toString(event, debug) + lines == null ? "" : " on lines " + lines.toString(event, debug);
	}

	@Override
	protected void execute(Event event) {
		if (lines != null) {
			ItemStack[] itemsArray = items.getArray(event);
			Integer[] linesArray = lines.stream(event).map(number -> number.intValue()).toArray(Integer[]::new);
			Map<ItemStack, Integer> map = IntStream.range(0, itemsArray.length)
					.boxed()
					.collect (Collectors.toMap(i -> itemsArray[i], i -> linesArray[i], Integer::sum, TreeMap::new));
			for (Hologram hologram : holograms.getArray(event))
				map.forEach((item, line) -> hologram.insertItemLine(line, item));
		} else {
			for (Hologram hologram : holograms.getArray(event)) {
				for (ItemStack item : items.getArray(event))
					hologram.appendItemLine(item);
			}
		}
	}

}
