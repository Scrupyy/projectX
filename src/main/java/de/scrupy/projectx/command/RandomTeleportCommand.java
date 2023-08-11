package de.scrupy.projectx.command;

import de.scrupy.projectx.ProjectX;
import de.scrupy.projectx.util.RandomBlockFinder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.CompletableFuture;

public class RandomTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cCommand only usable for players.");
            return true;
        }

        Location location = player.getLocation();
        Block blockUnderPlayer = location.subtract(0, 1, 0).getBlock();

        getSaveBlock(blockUnderPlayer.getLocation(), 5000).thenAccept(randomLocation -> {
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(randomLocation.add(0.0, 1.0, 0.0));

                }
            }.runTask(ProjectX.getInstance());
        });

        return true;
    }

    private CompletableFuture<Location> getSaveBlock(Location currentLocation, int searchRadius) {
        CompletableFuture<Location> completableFuture = new CompletableFuture<>();

        new BukkitRunnable() {
            @Override
            public void run() {
                boolean blockIsSave = false;

                while (true) {
                    Location randomLocation = RandomBlockFinder.getRandomLocation(currentLocation, searchRadius);
                    if (randomLocation.getWorld() == null)
                        continue;

                    if (compareLocations(randomLocation, currentLocation)) {
                        continue;
                    }

                    double randomHeight = randomLocation.getY();
                    double highestBlockY = randomLocation.getWorld().getHighestBlockYAt(randomLocation);

                    if (randomHeight > highestBlockY) {
                        randomLocation.setY(highestBlockY);
                    }

                    if (isSaveBlockMaterial(randomLocation.getBlock())) {
                        if (isEnoughSpaceForPlayer(randomLocation)) {
                            blockIsSave = true;
                        }
                    }

                    if (blockIsSave) {
                        completableFuture.complete(randomLocation);
                        break;
                    }
                }
            }
        }.runTaskAsynchronously(ProjectX.getInstance());

        return completableFuture;
    }

    private boolean compareLocations(Location randomLocation, Location currentLocation) {
        return currentLocation.getBlockX() == randomLocation.getBlockX() && currentLocation.getBlockZ() == randomLocation.getBlockZ();
    }

    private boolean isEnoughSpaceForPlayer(Location location) {
        Location locationToCheck = location.clone();
        boolean isSave = true;
        for (int i = 0; i < 2; i++) {
            locationToCheck.add(0, 1, 0);
            if (locationToCheck.getBlock().getType() != Material.AIR) {
                isSave = false;
                break;
            }
        }
        return isSave;
    }

    private boolean isSaveBlockMaterial(Block block) {
        Material blockType = block.getType();
        return blockType != Material.LAVA && blockType != Material.VOID_AIR && blockType != Material.AIR;
    }
}
