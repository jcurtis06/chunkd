package org.github.jcurtis.chunkd.managers;

/*

Created by Jonathan Curtis, 1/2/22
https://github.com/jcurtis06/chunkd

 */

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;
import org.github.jcurtis.chunkd.chunk.PlayerChunk;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class ChunkManager {
    private final LocalDataManager ldm;
    private final Chunkd chunkd;

    public ChunkManager(Chunkd chunkd) {
        this.ldm = chunkd.ldm;
        this.chunkd = chunkd;
    }

    /*
    Chunks are stored using a custom config. The format is as follows:
    uuid.chunkKey.world/x/z/name

    "chunkKey" is just the chunks x combined with the z.
     */
    public void claim(Player owner, Chunk chunk) {
        new PlayerChunk(chunkd.chunks, chunk, owner);

        /*
        ConfigurationSection section;

        // check if the player already has config section and take appropriate action
        if (ldm.getChunkConfig().getConfigurationSection(String.valueOf(owner.getUniqueId())) == null) {
            section = ldm.getChunkConfig().createSection(owner.getUniqueId().toString());
        } else {
            section = ldm.getChunkConfig().getConfigurationSection(String.valueOf(owner.getUniqueId()));
        }

        // get the chunk key
        String chunkKey = getKey(chunk);

        // set world, x, z, and name
        section.set(chunkKey + ".world", chunk.getWorld().toString());
        section.set(chunkKey + ".x", chunk.getX());
        section.set(chunkKey + ".z", chunk.getZ());
        section.set(chunkKey + ".name", null);
        section.createSection(chunkKey + ".permissions");

        // save the config
        try {
            ldm.getChunkConfig().save(ldm.getChunksFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
    }

    /*
    This function *removes* an entry from the chunks.yml file.
    See above 'claim' definition for information on how data is saved.
     */
    public boolean unclaim(Player owner, Chunk chunk) {
        for (PlayerChunk pc : chunkd.chunks.get()) {
            if (pc.getChunk() == chunk) {
                return chunkd.chunks.del(pc);
            }
        }

        return false;

        /*
        // get this player's section
        ConfigurationSection section = ldm.getChunkConfig().getConfigurationSection(owner.getUniqueId().toString());

        // remove entry
        section.set(getKey(chunk), null);

        // save
        try {
            ldm.getChunkConfig().save(ldm.getChunksFile());
        } catch (IOException e) {
            owner.sendMessage(ChatColor.RED + "Something went wrong.");
        }
         */
    }

    /*
    'isOwned()' returns true/false depending on whether the chunk is owned
    For getting the player who owns the chunk, the chunk, use 'getOwner()'
     */
    public Player getOwner(Chunk chunk) {
        if (chunkd.chunks.get() == null) return null;

        for (PlayerChunk pc : chunkd.chunks.get()) {
            if (pc.getChunk() == chunk) {
                return Bukkit.getPlayer(pc.getOwner());
            }
        }

        return null;

        /*
        // start with checking online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID pu = player.getUniqueId();

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()) == null) return null;

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()).contains(getKey(chunk))) {
                return Bukkit.getPlayer(pu);
            }
        }

        // then check offline players
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            UUID pu = player.getUniqueId();

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()) == null) return null;

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()).contains(getKey(chunk))) {
                return Bukkit.getPlayer(pu);
            }
        }

        // no one owns it, return null
        return null;
         */
    }

    public void updateChunkName(Player owner, Chunk chunk, String name) {
        UUID ou = owner.getUniqueId();

        ldm.getChunkConfig().set(ou + "." + getKey(chunk) + ".name", name);
        try {
            ldm.getChunkConfig().save(ldm.getChunksFile());
        } catch (IOException e) {
            owner.sendMessage(ChatColor.RED + "Something went wrong.");
        }
    }

    public String getChunkName(Player owner, Chunk chunk) {
        String ou = owner.getUniqueId().toString();

        return ldm.getChunkConfig().getString(ou + "." + getKey(chunk) + ".name");
    }

    public void setPermission(Chunk chunk, Player player, Player owner, String perm) {
        /*
        Valid perms:
        - owner
        - co
        - build
        - break
        - interact

        to set multiple perms, seperate with spaces
        (e.x: setPermission(chunk, player, owner, "build break");
         */

        ldm.getChunkConfig().set(owner.getUniqueId().toString() + "." + getKey(chunk) + ".permissions." + player.getUniqueId() + "." + perm, true);
    }

    public String getKey(Chunk chunk) {
        return String.valueOf(chunk.getX()) + "" + String.valueOf(chunk.getZ());
    }
}
