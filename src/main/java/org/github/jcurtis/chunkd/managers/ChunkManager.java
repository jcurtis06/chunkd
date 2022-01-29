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
    }

    /*
    This function *removes* an entry from the chunks.yml file.
    See above 'claim' definition for information on how data is saved.
     */
    public boolean unclaim(Player owner, Chunk chunk) {
        if (getPlayerChunk(chunk) == null) return false;

        if (chunkd.chunks.get().contains(getPlayerChunk(chunk)) && getPlayerChunk(chunk).getOwner() == owner.getUniqueId()) {
            chunkd.chunks.del(getPlayerChunk(chunk));
            return true;
        } else {
            return false;
        }
    }

    /*
    'isOwned()' returns true/false depending on whether the chunk is owned
    For getting the player who owns the chunk, the chunk, use 'getOwner()'
     */
    public Player getOwner(Chunk chunk) {
        if (getPlayerChunk(chunk) == null) return null;

        return Bukkit.getPlayer(getPlayerChunk(chunk).getOwner());
    }

    public void updateChunkName(Chunk chunk, String name) {
        getPlayerChunk(chunk).updateName(name);
    }

    public String getChunkName(Chunk chunk) {
        return getPlayerChunk(chunk).getName();
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
        return chunk.getX() + "" + chunk.getZ();
    }

    public PlayerChunk getPlayerChunk(Chunk chunk) {
        for (PlayerChunk pc : chunkd.chunks.get()) {
            if (pc.getChunk() == chunk) {
                return pc;
            }
        }
        return null;
    }

    public PlayerChunk getPlayerChunk(String key) {
        for (PlayerChunk pc : chunkd.chunks.get()) {
            if (pc.chunkKey().equals(key)) {
                return pc;
            }
        }
        return null;
    }
}
