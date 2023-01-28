package me.limeglass.graphisk.implementation.decentholograms;

import org.bukkit.entity.EntityType;

import ch.njol.skript.entity.EntityData;
import eu.decentsoftware.holograms.api.holograms.HologramLine;
import eu.decentsoftware.holograms.api.utils.entity.HologramEntity;
import me.limeglass.graphisk.implementation.GraphiskHologram.GraphiskHologramLine.GraphiskHologramEntityLine;

public class DecentHologramEntityLine implements GraphiskHologramEntityLine {

	private final HologramLine line;

	public DecentHologramEntityLine(HologramLine line) {
		this.line = line;
	}

	@Override
	public EntityData<?> getEntityData() {
		return EntityData.fromClass(line.getEntity().getType().getEntityClass());
	}

	@Override
	public void setEntityData(EntityData<?> entity) {
		for (EntityType type : EntityType.values()) {
			if (entity.getType().equals(type.getEntityClass())) {
				line.setEntity(new HologramEntity(type.name()));
				return;
			}
		}
	}

}
