package me.limeglass.graphisk.implementation.holographicdisplays;

import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramTextLine;

public class HolographicDisplaysTextLine implements GraphiskHologramTextLine {

	private final TextHologramLine line;

	public HolographicDisplaysTextLine(TextHologramLine line) {
		this.line = line;
	}

	@Override
	public String getText() {
		return line.getText();
	}

	@Override
	public void setText(String text) {
		line.setText(text);
	}

}
