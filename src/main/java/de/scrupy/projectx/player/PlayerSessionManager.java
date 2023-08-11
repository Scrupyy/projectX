package de.scrupy.projectx.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerSessionManager {
    private final Map<Player, PlayerSession> playerSessions;

    public PlayerSessionManager() {
        this.playerSessions = new HashMap<>();
    }

    public PlayerSession getPlayer(Player player) {
        playerSessions.putIfAbsent(player, new PlayerSession());
        return playerSessions.get(player);
    }
}
