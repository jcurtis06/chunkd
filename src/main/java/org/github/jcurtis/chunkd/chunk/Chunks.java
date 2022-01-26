package org.github.jcurtis.chunkd.chunk;

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

    public boolean del(PlayerChunk playerChunk) {
        if (playerChunks.contains(playerChunk)) {
            playerChunks.remove(playerChunk);
            return true;
        } else {
            return false;
        }
    }
}
