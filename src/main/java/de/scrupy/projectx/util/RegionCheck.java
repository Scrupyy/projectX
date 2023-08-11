package de.scrupy.projectx.util;

import org.bukkit.Location;

public class RegionCheck {
    public static boolean locationIsInRegion(Location location, Location firstCorner, Location secondCorner) {
        return location.getX() >= Math.min(firstCorner.getX(), secondCorner.getX()) &&
                location.getY() >= Math.min(firstCorner.getY(), secondCorner.getY()) &&
                location.getZ() >= Math.min(firstCorner.getZ(), secondCorner.getZ()) &&
                location.getX() <= Math.max(firstCorner.getX(), secondCorner.getX()) &&
                location.getY() <= Math.max(firstCorner.getY(), secondCorner.getY()) &&
                location.getZ() <= Math.max(firstCorner.getZ(), secondCorner.getZ());
    }
}
