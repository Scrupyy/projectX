package de.scrupy.projectx;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import de.scrupy.projectx.area.AreaManager;
import de.scrupy.projectx.command.RandomTeleportCommand;
import de.scrupy.projectx.command.SaveAreaCommand;
import de.scrupy.projectx.command.SetLocationCommand;
import de.scrupy.projectx.listener.PlayerMoveListener;
import de.scrupy.projectx.listener.PlayerRenderListener;
import de.scrupy.projectx.player.PlayerSessionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ProjectX extends JavaPlugin {
    private static ProjectX instance;

    @Override
    public void onEnable() {
        instance = this;

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PlayerRenderListener());

        PlayerSessionManager playerSessionManager = new PlayerSessionManager();
        AreaManager areaManager = new AreaManager();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerMoveListener(areaManager.getArea()), this);

        getCommand("randomTeleport").setExecutor(new RandomTeleportCommand());
        getCommand("setLocation").setExecutor(new SetLocationCommand(playerSessionManager));
        getCommand("saveArea").setExecutor(new SaveAreaCommand(playerSessionManager, areaManager));
    }

    public static ProjectX getInstance() {
        return instance;
    }
}
