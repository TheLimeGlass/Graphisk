package me.limeglass.graphisk.implementation.holographicdisplays;

import org.bukkit.inventory.ItemStack;

import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramItemLine;

public class HolographicDisplaysItemLine implements GraphiskHologramItemLine {

	private final ItemHologramLine line;

	public HolographicDisplaysItemLine(ItemHologramLine line) {
		this.line = line;
	}

	@Override
	public ItemStack getItemStack() {
		return line.getItemStack();
	}

	@Override
	public void setItemStack(ItemStack itemstack) {
		line.setItemStack(itemstack);
	}

}
