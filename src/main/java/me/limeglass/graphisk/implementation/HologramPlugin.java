package me.limeglass.graphisk.implementation;

import java.util.Collection;

import org.bukkit.Location;

public abstract class HologramPlugin {

	public enum Type {
		HOLOGRAMS,
		DECENT_HOLOGRAMS,
		HOLOGRAPHIC_DISPLAYS,
		HOLOGRAPHIC_DISPLAYS_V2;
	}

	private final Type type;

	public HologramPlugin(Type type) {
		this.type = type;
	}

	/**
	 * @return The hologram plugin this abstract class represents.
	 */
	public Type getPluginType() {
		return type;
	}

	public abstract GraphiskHologram createHologram(String name, Location location);

	public abstract Collection<GraphiskHologram> getHolograms();

}
