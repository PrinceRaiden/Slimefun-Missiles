package me.princeraiden.missiles.effects;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class FireMissileEffect implements MissileEffect {

    private double fireChance;
    private int radius;

    public FireMissileEffect(int radius, double fireChance) {
        this.radius = radius;
        this.fireChance = fireChance;
    }

    @Override
    public void handle(Location origin) {
        for (int x = origin.getBlockX() - radius; x <= origin.getBlockX() + radius; x++) {
            for (int z = origin.getBlockZ() - radius; z <= origin.getBlockZ() + radius; z++) {
                double rand = Math.random();

                if (rand <= fireChance) {
                    Location loc = new Location(origin.getWorld(), x, 0, z);
                    Block b = loc.getWorld().getHighestBlockAt(loc);
                    Block up = b.getRelative(BlockFace.UP);
                    up.setType(Material.FIRE);
                }
            }
        }
    }

    @Override
    public int getRadius() {
        return radius;
    }
}
