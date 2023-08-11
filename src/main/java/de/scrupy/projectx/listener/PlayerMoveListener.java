package de.scrupy.projectx.listener;

import de.scrupy.projectx.area.Area;
import de.scrupy.projectx.util.RegionCheck;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    private final Area area;

    public PlayerMoveListener(Area area) {
        this.area = area;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        if (player.isOp())
            return;

        if (area != null) {
            if (RegionCheck.locationIsInRegion(to.getBlock().getLocation(), area.getCornerOne(), area.getCornerTwo())) {
                Location override = from.clone();
                override.setX(override.getBlockX() + 0.5);
                override.setZ(override.getBlockZ() + 0.5);
                event.setTo(override);
            }
        }
    }
}
