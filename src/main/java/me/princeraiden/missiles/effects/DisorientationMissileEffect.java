package me.princeraiden.missiles.effects;

import org.bukkit.Location;

public class DisorientationMissileEffect implements MissileEffect {

    private int radius;

    public DisorientationMissileEffect(int radius) {
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
