package org.github.jcurtis.chunkd.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;
import org.github.jcurtis.chunkd.guis.ChunkManagerGUI;
import org.jetbrains.annotations.NotNull;

public class ChunkCMD implements CommandExecutor {
    private final Chunkd chunkd;

    public ChunkCMD(Chunkd chunkd) {
        this.chunkd = chunkd;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof final Player player) {
            switch (args.length) {
                case 0 -> {
                    player.sendMessage(ChatColor.GREEN + "Chunkd " + ChatColor.GRAY + "Created by Jonathan Curtis");
                    player.sendMessage(ChatColor.DARK_GRAY + "You are running v1.0");
                }
                case 1 -> {
                    if (args[0].equals("claim")) {
                        new Claim(chunkd.chunkManager, player, player.getLocation().getChunk());
                        return true;
                    }
                    if (args[0].equals("unclaim")) {
                        new Unclaim(chunkd.chunkManager, player, player.getLocation().getChunk());
                        return true;
                    }
                    if (args[0].equals("manage")) {
                        if (chunkd.chunks.get().contains(chunkd.chunkManager.getPlayerChunk(player.getLocation().getChunk()))) {
                            if (chunkd.chunkManager.getOwner(player.getLocation().getChunk()).equals(player)) {
                                new ChunkManagerGUI(chunkd).open(player);
                                return true;
                            } else {
                                player.sendMessage("You do not have permission to manage this chunk!");
                            }
                        } else {
                            player.sendMessage("You do not have permission to manage this chunk!");
                        }
                    }
                }
                default -> {
                    player.sendMessage(ChatColor.DARK_GREEN + "Usage: /claim [claim/unclaim]");
                    return false;
                }
            }
        } else {
            sender.sendMessage("Sorry, but this command must be ran in game.");
        }
        return false;
    }
}
