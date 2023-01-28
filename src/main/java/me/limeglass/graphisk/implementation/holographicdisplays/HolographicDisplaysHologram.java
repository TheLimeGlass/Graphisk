package me.limeglass.graphisk.implementation.holographicdisplays;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.entity.EntityData;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import me.filoghost.holographicdisplays.api.hologram.HologramLines;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.hologram.VisibilitySettings.Visibility;
import me.filoghost.holographicdisplays.api.hologram.line.HologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.ItemHologramLine;
import me.filoghost.holographicdisplays.api.hologram.line.TextHologramLine;
import me.limeglass.graphisk.implementation.GraphiskHologram;

public class HolographicDisplaysHologram extends GraphiskHologram {

	private final Hologram hologram;

	public HolographicDisplaysHologram(Hologram hologram) {
		this(null, hologram);
	}

	public HolographicDisplaysHologram(String name, Hologram hologram) {
		super(name);
		this.hologram = hologram;
	}

	@Override
	public Location getLocation() {
		return hologram.getPosition().toLocation();
	}

	@Override
	public void teleport(Location location) {
		hologram.setPosition(location);
	}

	@Override
	public boolean isVisible() {
		return hologram.getVisibilitySettings().getGlobalVisibility() == Visibility.VISIBLE;
	}

	@Override
	public void setVisible(boolean visible) {
		hologram.getVisibilitySettings().setGlobalVisibility(visible ? Visibility.VISIBLE : Visibility.HIDDEN);
	}

	@Override
	public boolean isVisibleTo(Player... players) {
		VisibilitySettings setting = hologram.getVisibilitySettings();
		for (Player player : players) {
			if (!setting.isVisibleTo(player))
				return false;
		}
		return true;
	}

	@Override
	public void setVisibleTo(boolean visible, Player... players) {
		VisibilitySettings setting = hologram.getVisibilitySettings();
		for (Player player : players)
			setting.setIndividualVisibility(player, visible ? Visibility.VISIBLE : Visibility.HIDDEN);
	}

	@Override
	public GraphiskHologramLine getLine(int page, int line) {
		return getLine(line);
	}

	@Override
	public GraphiskHologramLine getLine(int line) {
		if (line < 0 || line >= hologram.getLines().size())
			return null;
		HologramLine hologramLine = hologram.getLines().get(line);
		if (hologramLine instanceof TextHologramLine) {
			return new HolographicDisplaysTextLine((TextHologramLine) hologramLine);
		} else if (hologramLine instanceof ItemHologramLine) {
			return new HolographicDisplaysItemLine((ItemHologramLine) hologramLine);
		}
		return null;
	}

	@Override
	public void insertTextLine(int page, int line, String text) {
		HologramLines hologramLines = hologram.getLines();
		if (line < 0 || line >= hologramLines.size())
			return;
		hologramLines.insertText(line, text);
	}

	@Override
	public void insertItemLine(int page, int line, ItemStack itemstack) {
		HologramLines hologramLines = hologram.getLines();
		if (line < 0 || line >= hologramLines.size())
			return;
		hologramLines.insertItem(line, itemstack);
	}

	@Override
	public void removePageLines(int page, int... lines) {
		HologramLines hologramLines = hologram.getLines();
		for (int line : lines) {
			if (line < 0 || line >= hologramLines.size())
				continue;
			hologramLines.remove(line);
		}
	}

	@Override
	public void appendText(int page, String text) {
		hologram.getLines().appendText(text);
	}

	@Override
	public void appendItemStack(int page, ItemStack itemstack) {
		hologram.getLines().appendItem(itemstack);
	}

	@Override
	public void clearLines(int page) {
		hologram.getLines().clear();
	}

	@Override
	public void delete() {
		hologram.delete();
	}

	@Override
	public boolean isDeleted() {
		return hologram.isDeleted();
	}

	@Override
	public void appendEntity(int page, EntityData<?> entityData) {
		throw new UnsupportedOperationException();
	}

}
