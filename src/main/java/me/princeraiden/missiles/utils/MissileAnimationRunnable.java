package me.princeraiden.missiles.utils;

import me.princeraiden.missiles.Missiles;
import me.princeraiden.missiles.items.missiles.Missile;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.Vector;

public class MissileAnimationRunnable implements Runnable {

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

    public MissileAnimationRunnable(Missiles addon, Block origin, Location target, Missile missile) {
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
        addon.getMissileUnloadListener().addMissileUuid(armorStand.getUniqueId());
    }

    @Override
    public void run() {
        // test if missile has collided
        if (prevLoc != null) {
            double distanceSquared = armorStand.getLocation().distanceSquared(prevLoc);
            if (distanceSquared < missile.getSpeed() - velocityError) {
                explodeMissile();
                addon.getServer().getScheduler().cancelTask(this.id);
            }
        }

        // attempt to get to the cruising altitude before going to target
        if (armorStand != null) {
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

    private Vector getSmoothVelocity(Location l) {
        double armorStandY = armorStand.getLocation().getY();
        double distance = l.getY() - armorStandY;
        Vector vec = getVelocity(l, false)
                .multiply(1 / distance)
                .setY(distance)
                .normalize()
                .multiply(missile.getSpeed());
        return vec;
    }

    private Vector getVelocity(Location loc, boolean normalize) {
        Vector vec = loc.toVector()
                .subtract(armorStand.getLocation().toVector());
        return normalize ? vec.normalize().multiply(missile.getSpeed()) : vec;
    }

    private void explodeMissile() {
        World world = target.getWorld();
        Block b = world.getHighestBlockAt(target);
        target.getWorld().createExplosion(b.getLocation(), missile.getExplosionPower());

        // TODO effects and redstone translators then thats it

        if (armorStand != null) {
            armorStand.remove();
            armorStand = null;
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}
