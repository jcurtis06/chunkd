package org.github.jcurtis.chunkd.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;
import org.jetbrains.annotations.NotNull;

public class ChunkCMD implements CommandExecutor {
    private final Chunkd chunkd;

    public ChunkCMD(Chunkd chunkd) {
        this.chunkd = chunkd;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;

            switch (args.length) {
                case 0:
                    player.sendMessage(ChatColor.GREEN + "Chunkd " + ChatColor.GRAY + "Created by Jonathan Curtis");
                    player.sendMessage(ChatColor.DARK_GRAY + "You are running v1.0");

                    break;
                case 1:
                    // TODO: claim chunk, unclaim chunk
                    if (args[0].equals("claim")) {
                        chunkd.chunkManager.claim(player, player.getChunk());
                    }
                    break;
            }
        } else {
            sender.sendMessage("Sorry, but this command must be ran in game.");
        }
        return false;
    }
}