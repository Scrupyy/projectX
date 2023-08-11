package de.scrupy.projectx.command;

import de.scrupy.projectx.player.PlayerSession;
import de.scrupy.projectx.player.PlayerSessionManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocationCommand implements CommandExecutor {
    private final PlayerSessionManager playerSessionManager;

    public SetLocationCommand(PlayerSessionManager playerSessionManager) {
        this.playerSessionManager = playerSessionManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cCommand only usable for players.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("§c/setlocation [1,2]");
            return true;
        }
        try {
            int locationInt = Integer.parseInt(args[0]);

            Location location = player.getLocation().getBlock().getLocation();

            PlayerSession playerSession = playerSessionManager.getPlayer(player);
            if (locationInt == 1) {
                playerSession.setLocation1(location);
                player.sendMessage(location.toString());
            } else {
                playerSession.setLocation2(location);
                player.sendMessage(location.toString());
            }
        } catch (NumberFormatException exception) {
            player.sendMessage("§cFirst argument must to be an integer.");
            return true;
        }

        return true;
    }
}
