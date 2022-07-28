package me.princeraiden.missiles.effects;

import org.bukkit.Location;

public interface MissileEffect {

    void handle(Location origin);

    int getRadius();
}
