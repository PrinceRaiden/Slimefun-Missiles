package me.princeraiden.missiles.utils.ui;

import org.bukkit.Location;

public interface AttemptLaunchCallback {

    void run(Location targetLocation, Location computerLocation);
}
