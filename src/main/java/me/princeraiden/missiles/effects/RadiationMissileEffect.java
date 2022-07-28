package me.princeraiden.missiles.effects;

import org.bukkit.Location;

public class RadiationMissileEffect implements MissileEffect {

    private int radius;

    public RadiationMissileEffect(int radius) {
        this.radius = radius;
    }

    @Override
    public void handle(Location origin) {

    }

    @Override
    public int getRadius() {
        return radius;
    }
}
