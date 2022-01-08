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
            System.out.println("Player entered a new chunk");
            if (chunkManager.isOwned(player.getLocation().getChunk())) {
                System.out.println("Player entered an owned chunk");
                player.sendMessage(ChatColor.GREEN + "You have entered " + chunkManager.getOwner(event.getTo().getChunk()).getName() + "'s territory.");
            }
        }
    }
}
