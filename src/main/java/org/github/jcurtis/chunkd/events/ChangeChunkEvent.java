package org.github.jcurtis.chunkd.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.github.jcurtis.chunkd.managers.ChunkManager;

public class ChangeChunkEvent implements Listener {
    private final ChunkManager chunkManager;

    public ChangeChunkEvent(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getChunk() != event.getTo().getChunk()) {
            if (chunkManager.getOwner(event.getTo().getChunk()) != null) {
                if (chunkManager.getOwner(event.getTo().getChunk()) != chunkManager.getOwner(event.getFrom().getChunk())) {
                    player.sendMessage(ChatColor.GREEN + "You have entered " + chunkManager.getOwner(event.getTo().getChunk()).getName() + "'s territory.");
                }
            }
        }
    }
}
