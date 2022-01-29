package org.github.jcurtis.chunkd.chunk;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerChunk {
    private final int x;
    private final int z;

    private final String key;

    private final Chunk chunk;

    // perms
    private UUID owner;
    private ArrayList<UUID> coArray;
    private ArrayList<UUID> interactArray;

    // other info
    private String name;

    public PlayerChunk(Chunks chunks, Chunk chunk, Player owner) {
        this.chunk = chunk;
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.owner = owner.getUniqueId();
        this.key = this.x + "" + this.z;

        chunks.add(this);
    }

    public Chunk getChunk() {
        return this.chunk;
    }

    public String chunkKey() {
        return this.key;
    }

    public UUID getOwner() {
        return this.owner;
    }

    public String getName() { return this.name; }

    public void updateName(String name) { this.name = name; }

    public ArrayList<UUID> getCoArray() {
        return this.coArray;
    }

    public ArrayList<UUID> getInteractArray() {
        return this.getInteractArray();
    }
}
