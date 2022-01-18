package org.github.jcurtis.chunkd.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.managers.ChunkManager;

public class Claim {
    public Claim(ChunkManager chunkManager, Player player, Chunk chunk) {
        if (chunkManager.getOwner(chunk) != null) {
            player.sendMessage(ChatColor.RED + "This chunk has already been claimed!");
            return;
        }

        chunkManager.claim(player, chunk);
        player.sendMessage(ChatColor.GREEN + "You have successfully claimed this chunk!");
    }
}
