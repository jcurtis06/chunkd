package org.github.jcurtis.chunkd.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;

import java.io.*;
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

    public void save() throws IOException {
        File chunksDataFolder = new File("plugins/Chunkd");
        chunksDataFolder.mkdir();
        FileOutputStream fos = new FileOutputStream(chunksDataFolder + "/chunks.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (PlayerChunk pc : playerChunks) {
            oos.writeObject(pc);
        }
    }

    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("plugins/Chunkd/chunks.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object loadedObj;

        while ((loadedObj = ois.readObject()) != null) {
            playerChunks.add((PlayerChunk) loadedObj);
        }
    }
}
