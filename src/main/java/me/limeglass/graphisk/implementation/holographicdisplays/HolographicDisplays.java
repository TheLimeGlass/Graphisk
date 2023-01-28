package me.limeglass.graphisk.implementation.holographicdisplays;

import java.util.Collection;
import java.util.stream.Collectors;

import org.bukkit.Location;

import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.limeglass.graphisk.implementation.GraphiskHologram;
import me.limeglass.graphisk.implementation.HologramPlugin;

public class HolographicDisplays extends HologramPlugin {

	private final HolographicDisplaysAPI api;

	public HolographicDisplays(HolographicDisplaysAPI api) {
		super(Type.HOLOGRAPHIC_DISPLAYS);
		this.api = api;
	}

	@Override
	public HolographicDisplaysHologram createHologram(String name, Location location) {
		return new HolographicDisplaysHologram(name, api.createHologram(location));
	}

	@Override
	public Collection<GraphiskHologram> getHolograms() {
		return api.getHolograms().stream()
				.map(HolographicDisplaysHologram::new)
				.collect(Collectors.toList());
	}

}
