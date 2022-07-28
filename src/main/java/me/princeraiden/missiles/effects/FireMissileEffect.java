package me.princeraiden.missiles.effects;

import org.bukkit.Location;

public class FireMissileEffect implements MissileEffect {

    private int radius;

    public FireMissileEffect(int radius) {
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
