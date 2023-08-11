package de.scrupy.projectx.util;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Random;

public class RandomBlockFinder {
    public static Location getRandomLocation(Location origin, int radius) {
        World world = origin.getWorld();
        Random random = new Random();

        double randomX = origin.getX() + (random.nextDouble() * 2 - 1) * radius;
        double randomY = origin.getY() + (random.nextDouble() * 2 - 1) * radius;
        double randomZ = origin.getZ() + (random.nextDouble() * 2 - 1) * radius;

        return new Location(world, randomX, randomY, randomZ);
    }
}
