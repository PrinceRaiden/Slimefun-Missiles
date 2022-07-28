package me.princeraiden.missiles.utils;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.*;

public class MissileEffectUtils {

    private static final int MAX_POTION_DURATION = 60;
    private static final int MAX_POTION_AMPLIFIER = 3;

    public static Map<LivingEntity, Double> getNearbyLivingEntities(Location loc, int radius) {
        Map<LivingEntity, Double> entities = new HashMap<>();
        int radiusSquared = radius * radius;
        int chunkRadius = radius < 16 ? 1 : (int) Math.ceil(radius / 16f);
        Chunk originChunk = loc.getChunk();

        for (int xOffset = -chunkRadius; xOffset <= chunkRadius; xOffset++) {
            for (int zOffset = -chunkRadius; zOffset <= chunkRadius; zOffset++) {
                int chunkX = originChunk.getX() + xOffset;
                int chunkZ = originChunk.getZ() + zOffset;
                Chunk chunk = loc.getWorld().getChunkAt(chunkX, chunkZ);

                for (Entity entity : chunk.getEntities()) {
                    if (entity instanceof LivingEntity) {
                        double distanceSquared = entity.getLocation().distanceSquared(loc);
                        if (distanceSquared <= radiusSquared)
                            entities.put((LivingEntity) entity, distanceSquared);
                    }
                }
            }
        }

        return entities;
    }

    public static int getPotionDuration(int radius, double distanceSquared) {
        double duration = distanceSquared == 0 ? MAX_POTION_DURATION : 10 * radius * radius / distanceSquared;
        if (duration > MAX_POTION_DURATION)
            duration = MAX_POTION_DURATION;
        return (int) duration;
    }

    public static int getPotionAmplifier(int duration) {
        int amplifier = (int) Math.ceil(duration / 10f);
        if (amplifier > MAX_POTION_AMPLIFIER)
            amplifier = MAX_POTION_AMPLIFIER;
        return amplifier;
    }
}
