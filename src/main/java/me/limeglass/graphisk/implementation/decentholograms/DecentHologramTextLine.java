package me.limeglass.graphisk.implementation.decentholograms;

import eu.decentsoftware.holograms.api.holograms.HologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramTextLine;

public class DecentHologramTextLine implements GraphiskHologramTextLine {

	private final HologramLine line;

	public DecentHologramTextLine(HologramLine line) {
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
