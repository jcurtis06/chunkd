package org.github.jcurtis.chunkd.managers;

/*

Created by Jonathan Curtis, 1/2/22
https://github.com/jcurtis06/chunkd

 */

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;

import java.util.Collection;
import java.util.UUID;

public class ChunkManager {
    private final Chunkd chunkd;

    private Multimap<UUID, Chunk> chunkClaims = HashMultimap.create();

    public ChunkManager(Chunkd chunkd) {
        this.chunkd = chunkd;
    }

    public void claim(Player owner, Chunk chunk) {
        chunkClaims.put(owner.getUniqueId(), chunk);
    }

    public void unclaim(Player owner, Chunk chunk) {
        chunkClaims.remove(owner.getUniqueId(), chunk);
    }

    public boolean isOwned(Chunk chunk) {
        for (Chunk c : chunkClaims.values()) {
            return c.equals(chunk);
        }
        return false;
    }

    public Player getOwner(Chunk chunk) {
        for (UUID u : chunkClaims.keySet()) {
            if (chunkClaims.get(u).equals(chunk)) {
                return Bukkit.getPlayer(u);
            }
        }
        return null;
    }

    public Collection<Chunk> getPlayersChunks(Player player) {
        return chunkClaims.get(player.getUniqueId());
    }

    public Collection<Chunk> getAllClaimedChunks() {
        return chunkClaims.values();
    }

    public void saveData() {
        chunkd.jsonManager.addToFile();
    }
}
