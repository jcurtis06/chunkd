package org.github.jcurtis.chunkd.managers;

/*

Created by Jonathan Curtis, 1/2/22
https://github.com/jcurtis06/chunkd

 */

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;

import java.io.IOException;
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
            for (Chunk c : chunkClaims.get(u)) {
                if (c.equals(chunk)) {
                    return Bukkit.getPlayer(u);
                }
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

    public void loadChunksLocal() {
        if (chunkd.ldm.getChunkConfig().getRoot().getKeys(false) == null) return;

        System.out.println("loading chunks");

        chunkd.ldm.getChunkConfig().getRoot().getKeys(false).forEach(key -> {
            UUID u = UUID.fromString(key);

            System.out.println("found key " + key);

            chunkd.ldm.getChunkConfig().getConfigurationSection(u.toString()).getKeys(false).forEach(key1 -> {
                chunkd.ldm.getChunkConfig().getConfigurationSection(u.toString() + "." + key1).getKeys(false).forEach(val -> {
                    Chunk c = Bukkit.getWorld(chunkd.ldm.getChunkConfig().getString(u + "." + key1 + ".world")).getChunkAt(chunkd.ldm.getChunkConfig().getInt(u + "." + key1 + ".x"), chunkd.ldm.getChunkConfig().getInt(u + "." + key1 + ".z"));

                    chunkClaims.put(u, c);
                });
            });
        });
    }

    public void storeChunksLocal() throws IOException {
        for (UUID u : chunkClaims.keys()) {
            chunkd.ldm.getChunkConfig().createSection(u.toString());

            for (Chunk c : chunkClaims.get(u)) {
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".x", c.getX());
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".z", c.getZ());
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".world", c.getWorld().getName());
            }
        }

        chunkd.ldm.getChunkConfig().save(chunkd.ldm.getChunksFile());
    }
}
