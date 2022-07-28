package me.princeraiden.missiles.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MissileUnloadListener implements Listener {

    private List<UUID> missileUuid = new ArrayList<>();

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e) {
        for (Entity entity : e.getChunk().getEntities()) {
            UUID entityUuid = entity.getUniqueId();

            if (missileUuid.contains(entityUuid)) {
                entity.remove();
            }
        }
    }

    public void addMissileUuid(UUID uuid) {
        this.missileUuid.add(uuid);
    }
}
