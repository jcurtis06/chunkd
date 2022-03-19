package org.github.jcurtis.chunkd.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerChunk implements Serializable {
    private final int x;
    private final int z;

    private final String key;

    // perms
    private UUID owner;
    private ArrayList<UUID> coArray;
    private ArrayList<UUID> interactArray;

    // other info
    private String name;

    public PlayerChunk(Chunks chunks, Chunk chunk, Player owner) {
        this.x = chunk.getX();
        this.z = chunk.getZ();
        this.owner = owner.getUniqueId();
        this.key = this.x + "" + this.z;

        chunks.add(this);
    }

    public Chunk getChunk() {
        Chunk c = Bukkit.getWorld("world").getChunkAt(this.x, this.z);

        return c;
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
