package org.github.jcurtis.chunkd.commands;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.github.jcurtis.chunkd.Chunkd;
import org.github.jcurtis.chunkd.managers.ChunkManager;

import java.util.HashMap;
import java.util.UUID;

public class EditName implements Listener {
    private final ChunkManager chunkManager;
    public HashMap<UUID, Chunk> pendingEdits = new HashMap<>();

    public EditName(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
    }

    public void run(Player player, Chunk chunk) {
        if (chunkManager.getOwner(chunk) != player) return;

        pendingEdits.put(player.getUniqueId(), chunk);

        player.sendMessage(ChatColor.GREEN + "Please enter in the chat what you would like to rename this chunk to, or type 'cancel' to cancel.");
    }

    @EventHandler
    public void chatEvent(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();

        if (!pendingEdits.isEmpty()) {
            if (pendingEdits.isEmpty()) return;

            if (pendingEdits.containsKey(sender.getUniqueId())) {
                if (event.getMessage().equals("cancel")) {
                     pendingEdits.remove(sender.getUniqueId());
                } else {
                    // the person who sent this message is currently editing the chunk name
                    event.setCancelled(true);

                    String input = event.getMessage();

                    chunkManager.updateChunkName(sender, sender.getLocation().getChunk(), input);

                    sender.sendMessage(ChatColor.GREEN + "You have renamed this chunk to " + input);

                    pendingEdits.remove(sender.getUniqueId());
                }
            } else {
                // not editing, send normal message
                for (Player p : event.getRecipients()) {
                    if (pendingEdits.containsKey(p.getUniqueId())) {
                        event.getRecipients().remove(p);
                    }
                }
            }
        }
    }
}
