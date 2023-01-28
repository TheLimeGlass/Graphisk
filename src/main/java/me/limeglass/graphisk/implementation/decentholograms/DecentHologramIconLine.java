package me.limeglass.graphisk.implementation.decentholograms;

import ch.njol.skript.aliases.ItemType;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramIconLine;

public class DecentHologramIconLine implements GraphiskHologramIconLine {

	private final HologramLine line;

	public DecentHologramIconLine(HologramLine line) {
		this.line = line;
	}

	@Override
	public ItemType getIcon() {
		return new ItemType(line.getItem().parse());
	}

	@Override
	public void setIcon(ItemType item) {
		line.getItem().setMaterial(item.getMaterial());
	}

}
