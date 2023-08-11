package de.scrupy.projectx.command;

import de.scrupy.projectx.area.Area;
import de.scrupy.projectx.area.AreaManager;
import de.scrupy.projectx.player.PlayerSession;
import de.scrupy.projectx.player.PlayerSessionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.IOException;

public class SaveAreaCommand implements CommandExecutor, Listener {
    private final PlayerSessionManager playerSessionManager;
    private final AreaManager areaManager;

    public SaveAreaCommand(PlayerSessionManager playerSessionManager, AreaManager areaManager) {
        this.playerSessionManager = playerSessionManager;
        this.areaManager = areaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cCommand only usable for players.");
            return true;
        }

        PlayerSession playerSession = playerSessionManager.getPlayer(player);
        if (!playerSession.twoLocationsSet()) {
            player.sendMessage("§cYou have to set two locations first.");
            return true;
        }

        Area area = new Area(playerSession.getLocation1(), playerSession.getLocation2());
        try {
            areaManager.saveArea(area);
        } catch (IOException e) {
            player.sendMessage("§cError while saving area file.");
            throw new RuntimeException(e);
        }
        return true;
    }
}
