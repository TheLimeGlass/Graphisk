package me.limeglass.graphisk.implementation.decentholograms;

import org.bukkit.inventory.ItemStack;

import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.utils.items.SkullUtils;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramHeadLine;

public class DecentHologramHeadLine implements GraphiskHologramHeadLine {

	public static class DecentHologramSmallHeadLine extends DecentHologramHeadLine {

		public DecentHologramSmallHeadLine(HologramLine line) {
			super(line);
		}

	}

	private final HologramLine line;

	public DecentHologramHeadLine(HologramLine line) {
		this.line = line;
	}

	@Override
	public String getHeadOwner() {
		ItemStack item = line.getItem().parse();
		String owner = SkullUtils.getSkullTexture(item);
		if (owner == null)
			owner = SkullUtils.getSkullOwner(item);
		return owner;
	}

	@Override
	public void setHeadOwner(String player) {
		line.getItem().setExtras(player);
	}

}
