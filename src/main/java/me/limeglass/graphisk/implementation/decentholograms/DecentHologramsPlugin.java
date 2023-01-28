package me.limeglass.graphisk.implementation.decentholograms;

import java.util.Collection;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.eclipse.jdt.annotation.Nullable;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.DecentHolograms;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.limeglass.graphisk.implementation.GraphiskHologram;
import me.limeglass.graphisk.implementation.HologramPlugin;

public class DecentHologramsPlugin extends HologramPlugin {

	private final DecentHolograms api;

	public DecentHologramsPlugin(DecentHolograms api) {
		super(Type.DECENT_HOLOGRAMS);
		this.api = api;
	}

	@Override
	@Nullable
	public DecentHologram createHologram(String name, Location location) {
		if (name == null || location == null)
			return null;
		Hologram hologram = api.getHologramManager().getHologram(name);
		if (hologram != null) {
			DecentHologram decentHologram = new DecentHologram(hologram);
			decentHologram.teleport(location);
			return decentHologram;
		}
		hologram = DHAPI.createHologram(name, location);
		api.getHologramManager().registerHologram(hologram);
		return new DecentHologram(hologram);
	}

	@Override
	public Collection<GraphiskHologram> getHolograms() {
		return api.getHologramManager().getHolograms().stream()
				.map(DecentHologram::new)
				.collect(Collectors.toList());
	}

}
