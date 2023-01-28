package me.limeglass.graphisk.implementation.decentholograms;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.bukkitutil.EntityUtils;
import ch.njol.skript.entity.EntityData;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.holograms.HologramPage;
import me.limeglass.graphisk.implementation.GraphiskHologram;
import me.limeglass.graphisk.implementation.decentholograms.DecentHologramHeadLine.DecentHologramSmallHeadLine;

public class DecentHologram extends GraphiskHologram {

	private final Hologram hologram;

	public DecentHologram(Hologram hologram) {
		super(hologram.getName());
		this.hologram = hologram;
	}

	@Override
	public Location getLocation() {
		return hologram.getLocation();
	}

	@Override
	public void teleport(Location location) {
		hologram.setLocation(location);
	}

	@Override
	public boolean isVisible() {
		return hologram.isDefaultVisibleState();
	}

	@Override
	public void setVisible(boolean visible) {
		hologram.setDefaultVisibleState(visible);
	}

	@Override
	public boolean isVisibleTo(Player... players) {
		for (Player player : players) {
			if (!hologram.isVisible(player))
				return false;
		}
		return true;
	}

	@Override
	public void setVisibleTo(boolean visible, Player... players) {
		for (Player player : players) {
			if (visible) {
				hologram.setShowPlayer(player);
			} else {
				hologram.hide(player);
			}
		}
	}

	@Override
	public GraphiskHologramLine getLine(int page, int line) {
		if (line < 0)
			return null;
		HologramLine hologramLine = DHAPI.getHologramLine(hologram.getPage(page), line);
		switch (hologramLine.getType()) {
			case ENTITY:
				return new DecentHologramEntityLine(hologramLine);
			case HEAD:
				return new DecentHologramHeadLine(hologramLine);
			case ICON:
				return new DecentHologramIconLine(hologramLine);
			case SMALLHEAD:
				return new DecentHologramSmallHeadLine(hologramLine);
			case TEXT:
				return new DecentHologramTextLine(hologramLine);
			case UNKNOWN:
			default:
				break;
		}
		return null;
	}

	@Override
	public void insertTextLine(int page, int line, String text) {
		HologramPage hologramPage = hologram.getPage(page);
		if (line < 0 || line >= hologramPage.size())
			return;
		DHAPI.insertHologramLine(hologram, page, line, text);
	}

	@Override
	public void insertItemLine(int page, int line, ItemStack itemstack) {
		HologramPage hologramPage = hologram.getPage(page);
		if (line < 0 || line >= hologramPage.size())
			return;
		DHAPI.insertHologramLine(hologram, page, line, itemstack);
	}

	@Override
	public void removePageLines(int page, int... lines) {
		HologramPage hologramPage = hologram.getPage(page);
		for (int line : lines) {
			if (line < 0 || line >= hologramPage.size())
				continue;
			hologramPage.removeLine(line);
		}
	}

	@Override
	public void appendText(int page, String text) {
		DHAPI.addHologramLine(hologram, page, text);
	}

	@Override
	public void appendItemStack(int page, ItemStack itemstack) {
		DHAPI.addHologramLine(hologram.getPage(page), itemstack);
	}

	@Override
	public void clearLines(int page) {
		hologram.removePage(page);
	}

	@Override
	public void delete() {
		hologram.delete();
	}

	@Override
	public boolean isDeleted() {
		return false;
	}

	@Override
	public void appendEntity(int page, EntityData<?> entityData) {
		EntityType type = EntityUtils.toBukkitEntityType(entityData);
		HologramPage hologramPage = hologram.getPage(page);
		HologramLine line = new HologramLine(hologramPage, hologramPage.getNextLineLocation(), "#ENTITY:" + type.name());
		hologram.getPage(page).insertLine(page, line);
	}

}
