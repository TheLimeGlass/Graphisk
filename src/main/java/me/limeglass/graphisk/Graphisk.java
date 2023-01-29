package me.limeglass.graphisk;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import eu.decentsoftware.holograms.api.DecentHologramsAPI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.limeglass.graphisk.implementation.HologramPlugin;
import me.limeglass.graphisk.implementation.decentholograms.DecentHologramsPlugin;
import me.limeglass.graphisk.implementation.holographicdisplays.HolographicDisplays;

public class Graphisk extends JavaPlugin {

	private static HologramPlugin plugin;
	private static SkriptAddon addon;
	private static Graphisk instance;

	public void onEnable() {
		instance = this;
		PluginManager pluginManager = Bukkit.getPluginManager();
		if (pluginManager.isPluginEnabled("HolographicDisplays")) {
			plugin = new HolographicDisplays(HolographicDisplaysAPI.get(this));
		} else if (pluginManager.isPluginEnabled("DecentHolograms")) {
			// Annoying API things
			DecentHologramsAPI.onLoad(this);
			DecentHologramsAPI.onEnable();
			plugin = new DecentHologramsPlugin(DecentHologramsAPI.get());
		} else if (pluginManager.isPluginEnabled("Holograms")) {
			plugin = null;
		}
		if (plugin == null) {
			getLogger().severe("Failed to find any hologram plugins. Disabling Graphisk.");
			setEnabled(false);
			return;
		}
		try {
			addon = Skript.registerAddon(this)
					.loadClasses("me.limeglass.graphisk", "elements");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return The hologram plugin that Graphisk is going to be using.
	 */
	public static HologramPlugin getHologramPlugin() {
		return plugin;
	}

	/**
	 * @return The Skript addon instance that is registered.
	 */
	public SkriptAddon getAddonInstance() {
		return addon;
	}

	/**
	 * @return Graphisk instance.
	 */
	public static Graphisk getInstance() {
		return instance;
	}

}
