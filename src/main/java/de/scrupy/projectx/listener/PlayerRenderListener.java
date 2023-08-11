package de.scrupy.projectx.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.scrupy.projectx.ProjectX;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerRenderListener extends PacketAdapter {
    public PlayerRenderListener() {
        super(
                ProjectX.getInstance(),
                ListenerPriority.NORMAL,
                PacketType.Play.Server.NAMED_ENTITY_SPAWN,
                PacketType.Play.Server.ENTITY_DESTROY
        );
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketType packetType = event.getPacketType();

        if (packetType == PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            UUID uuid = event.getPacket().getUUIDs().read(0);
            Player renderedPlayer = Bukkit.getPlayer(uuid);

            if (renderedPlayer != null) {
                player.sendMessage("§a" + renderedPlayer.getName());
            }

        } else if (packetType == PacketType.Play.Server.ENTITY_DESTROY) {
            int entityId = event.getPacket().getIntLists().read(0).get(0);
            World playerWorld = player.getWorld();
            Entity entity = getEntityInWorldById(playerWorld, entityId);

            if (entity instanceof Player)
                player.sendMessage("§c" + entity.getName());
        }
    }

    @Nullable
    private Entity getEntityInWorldById(World world, int entityId) {
        for (Entity entity : world.getEntities()) {
            if (entity.getEntityId() == entityId)
                return entity;
        }
        return null;
    }
}
