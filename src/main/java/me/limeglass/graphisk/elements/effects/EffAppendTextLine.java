package me.limeglass.graphisk.elements.effects;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.limeglass.graphisk.implementation.GraphiskHologram;

@Name("Hologram append text line")
@Description("Add a line of text to the hologram(s).")
@Since("2.0.0")
public class EffAppendTextLine extends Effect {

	static {
		Skript.registerEffect(EffAppendTextLine.class, "(append|add) text [line] %strings% to holo[gra(m|phic display)][s] %holograms% [(in|at) line %-numbers%]");
	}

	private Expression<GraphiskHologram> holograms;
	private Expression<String> text;

	@Nullable
	private Expression<Number> lines;

	@Override
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		holograms = (Expression<GraphiskHologram>) exprs[1];
		lines = (Expression<Number>) exprs[2];
		text = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	protected void execute(Event event) {
		if (lines != null) {
			String[] stringsArray = text.getArray(event);
			Integer[] linesArray = lines.stream(event).map(number -> number.intValue()).toArray(Integer[]::new);
			Map<String, Integer> map = IntStream.range(0, stringsArray.length)
					.boxed()
					.collect (Collectors.toMap(i -> stringsArray[i], i -> linesArray[i], Integer::sum, TreeMap::new));
			for (GraphiskHologram hologram : holograms.getArray(event))
				map.forEach((string, line) -> hologram.insertTextLine(line, string));
		} else {
			for (GraphiskHologram hologram : holograms.getArray(event)) {
				for (String string : text.getArray(event))
					hologram.appendText(string);
			}
		}
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		if (event == null || debug)
			return "add text to hologram";
		return "add text " + text.toString(event, debug) + " to " + holograms.toString(event, debug) + lines == null ? "" : " on lines " + lines.toString(event, debug);
	}

}
