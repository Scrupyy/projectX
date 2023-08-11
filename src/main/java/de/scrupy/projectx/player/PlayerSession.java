package de.scrupy.projectx.player;

import org.bukkit.Location;

public class PlayerSession {
    private Location location1;
    private Location location2;

    public PlayerSession(Location location1, Location location2) {
        this.location1 = location1;
        this.location2 = location2;
    }

    public PlayerSession() {}

    public Location getLocation1() {
        return location1;
    }

    public void setLocation1(Location location1) {
        this.location1 = location1;
    }

    public Location getLocation2() {
        return location2;
    }

    public void setLocation2(Location location2) {
        this.location2 = location2;
    }

    public boolean twoLocationsSet() {
        return location1 != null && location2 != null;
    }
}
