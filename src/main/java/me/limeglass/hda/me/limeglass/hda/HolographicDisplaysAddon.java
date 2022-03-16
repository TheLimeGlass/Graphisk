package me.limeglass.hda;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class HolographicDisplaysAddon extends JavaPlugin {

	private static HolographicDisplaysAddon instance;
	private static SkriptAddon addon;

	public void onEnable() {
		instance = this;
		try {
			addon = Skript.registerAddon(this)
					.loadClasses("me.limeglass.hda", "elements")
					.setLanguageFileDirectory("lang");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static HolographicDisplaysAddon getInstance() {
		return instance;
	}

	public SkriptAddon getAddonInstance() {
		return addon;
	}

}
