package me.princeraiden.missiles.utils;

import me.princeraiden.missiles.Missiles;
import me.princeraiden.missiles.items.missiles.Missile;
import me.princeraiden.missiles.effects.MissileEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MissileControllerRunnable implements Runnable {

    private static final float locationError = 1;
    private static final float velocityError = 0.1F;

    private boolean isAtCruiseAlt;
    private boolean isAtTarget;
    private ArmorStand armorStand;
    private final Location target;
    private Missile missile;
    private Missiles addon;
    private Location prevLoc;
    private int id;

    private boolean arrivalTimeIsSet;
    private long arrivalTime;

    public MissileControllerRunnable(Missiles addon, Block origin, Location target, Missile missile) {
        this.addon = addon;
        this.missile = missile;

        float accuracyRadius = missile.getAccuracyRadius();
        this.target = target.add(
                Math.random() * accuracyRadius * 2 - accuracyRadius,
                0,
                Math.random() * accuracyRadius * 2 - accuracyRadius
        );

        armorStand = origin.getWorld().spawn(origin.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.getEquipment().setHelmet(MissilesHeads.NITROGLYCERIN.getItemStack());
    }

    @Override
    public void run() {
        if (arrivalTimeIsSet) { // if the armorstand has been unloaded, use a time based system to calculate when to explode
            if (System.currentTimeMillis() >= arrivalTime) {
                World world = target.getWorld();
                Block explosionBlock = world.getHighestBlockAt(target);
                explodeMissile(explosionBlock.getLocation());
                addon.getServer().getScheduler().cancelTask(this.id);
            }
        } else if (armorStand != null) { // if the armorstand is still loaded, use a simulation to calculate when to explode
            // test if missiles velocity is too low (has collided or entered unloaded chunk)
            if (prevLoc != null) {
                double distanceSquared = armorStand.getLocation().distanceSquared(prevLoc);

                if (distanceSquared < missile.getSpeed() - velocityError) {
                    List<Block> nearbyBlocks = getAdjacentSolidBlocks(armorStand.getLocation());

                    if (nearbyBlocks.size() > 0) { // if there are any nearby blocks, the missile has most likely collided, so explode it.
                        explodeMissile(armorStand.getLocation());
                        addon.getServer().getScheduler().cancelTask(this.id);
                    } else { // if there are no nearby blocks, the missile has probably entered a nearby chunk, so schedule an explosion at the target
                        double distance = armorStand.getLocation().distance(target);
                        double ticks = distance / (missile.getSpeed() * missile.getSpeed());
                        long milliseconds = (long) (ticks / 20 * 1000);
                        arrivalTime = System.currentTimeMillis() + milliseconds;
                        arrivalTimeIsSet = true;
                    }
                }
            }

            // attempt to get to the cruising altitude before going to target
            if (!isAtCruiseAlt) {
                double armorStandY = armorStand.getLocation().getY();
                if (armorStandY > target.getY() - locationError && armorStandY < target.getY() + locationError) {
                    isAtCruiseAlt = true;
                }

                Vector vec = getSmoothVelocity(target);
                armorStand.setVelocity(vec);
            } else if (!isAtTarget) {
                double armorStandX = armorStand.getLocation().getX();
                double armorStandY = armorStand.getLocation().getY();
                double armorStandZ = armorStand.getLocation().getZ();

                if (armorStandX > target.getX() - locationError && armorStandX < target.getX() + locationError
                    && armorStandY > target.getY() - locationError && armorStandY < target.getY() + locationError
                    && armorStandZ > target.getZ() - locationError && armorStandZ < target.getZ() + locationError) {
                    isAtTarget = true;
                }

                Vector vec = getSmoothVelocity(target);
                armorStand.setVelocity(vec);
            } else {
                Vector vec = new Vector(0, -missile.getSpeed(), 0);
                armorStand.setVelocity(vec);
            }

            prevLoc = armorStand.getLocation();
        }
    }

    private List<Block> getAdjacentSolidBlocks(Location loc) {
        List<Block> blocks = new ArrayList<>();
        Block b = loc.getBlock();

        for (int y = -2; y <= 2; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    Block adjacent = b.getRelative(x, y, z);
                    if (adjacent.getType().isSolid())
                        blocks.add(b.getRelative(x, y, z));
                }
            }
        }

        return blocks;
    }

    private Vector getSmoothVelocity(Location l) {
        double armorStandY = armorStand.getLocation().getY();
        double distance = l.getY() - armorStandY;
        Vector vec = l.toVector()
                .subtract(armorStand.getLocation().toVector())
                .multiply(1 / distance)
                .setY(distance)
                .normalize()
                .multiply(missile.getSpeed());
        return vec;
    }

    private void explodeMissile(Location explosionLoc) {
        float explosionRadius = missile.getExplosionDiameter() / 2f;
        int explosionLowerBound = (int) -Math.floor(explosionRadius);
        int explosionUpperBound = (int) Math.ceil(explosionRadius);

        for (int xOffset = explosionLowerBound; xOffset <= explosionUpperBound; xOffset++) {
            for (int zOffset = explosionLowerBound; zOffset <= explosionUpperBound; zOffset++) {
                Location loc = new Location(
                        explosionLoc.getWorld(),
                        xOffset * missile.getExplosionSpacing() + explosionLoc.getX(),
                        explosionLoc.getY(),
                        zOffset * missile.getExplosionSpacing() + explosionLoc.getZ());
                target.getWorld().createExplosion(loc, missile.getExplosionPower());
            }
        }

        for (MissileEffect effect : missile.getEffects()) {
            effect.handle(explosionLoc);
        }

        // TODO effects and redstone translators then thats it

        if (armorStand != null)
            armorStand.remove();
    }

    public void setId(int id) {
        this.id = id;
    }
}
