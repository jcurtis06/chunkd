package org.github.jcurtis.chunkd.chunk;

import org.github.jcurtis.chunkd.Chunkd;

import java.io.*;
import java.util.ArrayList;

public class Chunks {
    private ArrayList<PlayerChunk> playerChunks = new ArrayList<>();

    public ArrayList<PlayerChunk> get() {
        return this.playerChunks;
    }

    public boolean add(PlayerChunk playerChunk) {
        if (playerChunks.contains(playerChunk)) {
            return false;
        } else {
            playerChunks.add(playerChunk);
            return true;
        }
    }

    public void del(PlayerChunk playerChunk) {
        if (playerChunks.contains(playerChunk)) {
            playerChunks.remove(playerChunk);
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
