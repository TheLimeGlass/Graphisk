package me.limeglass.graphisk.implementation;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.entity.EntityData;

public abstract class GraphiskHologram {

	private final String name;

	public GraphiskHologram(String name) {
		this.name = name;
	}

	@Nullable
	public String getName() {
		return name;
	}

	public abstract Location getLocation();

	public abstract void teleport(Location location);

	public abstract void delete();

	public abstract boolean isDeleted();

	public abstract boolean isVisible();

	public abstract void setVisible(boolean visible);

	public abstract boolean isVisibleTo(Player... players);

	public abstract void setVisibleTo(boolean visible, Player... players);

	public abstract void clearLines(int page);

	public void clearLines() {
		clearLines(0);
	}

	public void removeLine(int page, int line) {
		removePageLines(page, line);
	}

	public void removeLine(int line) {
		removePageLines(0, line);
	}

	public abstract void removePageLines(int page, int... lines);

	public void removeLines(int... lines) {
		removePageLines(0, lines);
	}

	public abstract void appendText(int page, String text);

	public void appendText(String text) {
		appendText(0, text);
	}

	public abstract void insertTextLine(int page, int line, String text);

	public void insertTextLine(int line, String text) {
		insertTextLine(0, line, text);
	}

	public abstract void appendItemStack(int page, ItemStack itemstack);

	public void appendItemStack(ItemStack itemstack) {
		appendItemStack(0, itemstack);
	}

	public abstract void insertItemLine(int page, int line, ItemStack itemstack);

	public void insertItemLine(int line, ItemStack itemstack) {
		insertItemLine(0, line, itemstack);
	}

	public abstract void appendEntity(int page, EntityData<?> entityData);

	public void appendEntity(EntityData<?> entityData) {
		appendEntity(0, entityData);
	}

	public abstract GraphiskHologramLine getLine(int page, int line);

	public GraphiskHologramLine getLine(int line) {
		return getLine(0, line);
	}

	public interface GraphiskHologramLine {

		public interface GraphiskHologramEntityLine extends GraphiskHologramLine {

			public EntityData<?> getEntityData();

			public void setEntityData(EntityData<?> entity);

		}

		public interface GraphiskHologramTextLine extends GraphiskHologramLine {

			public String getText();

			public void setText(String text);

		}

		public interface GraphiskHologramItemLine extends GraphiskHologramLine {

			public ItemStack getItemStack();

			public void setItemStack(ItemStack itemstack);

		}

		public interface GraphiskHologramHeadLine extends GraphiskHologramLine {

			public String getHeadOwner();

			public void setHeadOwner(String player);

		}

		public interface GraphiskHologramSmallHeadLine extends GraphiskHologramHeadLine {}

		public interface GraphiskHologramIconLine extends GraphiskHologramLine {

			public ItemType getIcon();

			public void setIcon(ItemType item);

		}

	}

}
