package org.github.jcurtis.chunkd.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.github.jcurtis.chunkd.Chunkd;

import java.util.ArrayList;
import java.util.UUID;

public class Chunks {
    private ArrayList<PlayerChunk> playerChunks = new ArrayList<>();

    public ArrayList<PlayerChunk> get() {
        return this.playerChunks;
    }

    private final Chunkd chunkd;

    public Chunks(Chunkd chunkd) {
        this.chunkd = chunkd;
    }

    public boolean add(PlayerChunk playerChunk) {
        if (playerChunks.contains(playerChunk)) {
            return false;
        } else {
            playerChunks.add(playerChunk);
            return true;
        }
    }

    public boolean del(PlayerChunk playerChunk) {
        if (playerChunks.contains(playerChunk)) {
            playerChunks.remove(playerChunk);
            return true;
        } else {
            return false;
        }
    }

    public void save() {
        ArrayList<String> chunkKeys = new ArrayList<>();

        for (PlayerChunk pc : playerChunks) {
            chunkKeys.add(pc.chunkKey());
        }

        for (String ck : chunkKeys) {
            chunkd.ldm.getChunkConfig().createSection(ck);
            chunkd.ldm.getChunkConfig().set(ck + ".x", chunkd.chunkManager.getPlayerChunk(ck).getChunk().getX());
            chunkd.ldm.getChunkConfig().set(ck + ".z", chunkd.chunkManager.getPlayerChunk(ck).getChunk().getZ());
            chunkd.ldm.getChunkConfig().set(ck + ".world", chunkd.chunkManager.getPlayerChunk(ck).getChunk().getWorld().getName());
            chunkd.ldm.getChunkConfig().set(ck + ".owner", chunkd.chunkManager.getPlayerChunk(ck).getOwner());
        }
    }

    public void load() {
        for (String k : chunkd.ldm.getChunkConfig().getKeys(false)) {
            new PlayerChunk(
                    this,
                    Bukkit.getWorld(chunkd.ldm.getChunkConfig().getString(k + ".world")).getChunkAt(chunkd.ldm.getChunkConfig().getInt(k + ".x"), chunkd.ldm.getChunkConfig().getInt(k + ".z")),
                    Bukkit.getPlayer((UUID) chunkd.ldm.getChunkConfig().get(k + ".owner"))
            );
        }
    }
}
