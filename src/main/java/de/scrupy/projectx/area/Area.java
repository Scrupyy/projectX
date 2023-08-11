package de.scrupy.projectx.area;

import org.bukkit.Location;

public class Area {
    private final Location cornerOne;
    private final Location cornerTwo;

    public Area(Location x, Location y) {
        this.cornerOne = x;
        this.cornerTwo = y;
    }

    public Location getCornerOne() {
        return cornerOne;
    }

    public Location getCornerTwo() {
        return cornerTwo;
    }
}
