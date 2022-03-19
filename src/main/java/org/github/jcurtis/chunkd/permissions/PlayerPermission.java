package org.github.jcurtis.chunkd.permissions;

import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.chunk.PlayerChunk;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerPermission implements Serializable {
    private final PlayerChunk playerChunk;
    private final Player player;

    private final ArrayList<String> blockedList = new ArrayList<>();
    private final ArrayList<String> allowedList = new ArrayList<>();

    public PlayerPermission(Permissions permissions, PlayerChunk playerChunk, Player player, String[] blockedList, String[] allowedList) {
        this.playerChunk = playerChunk;
        this.player = player;

        for (String s : blockedList) {
            this.blockedList.add(s);
        }

        for (String s : allowedList) {
            this.allowedList.add(s);
        }

        permissions.add(this);
    }

    public Player getPlayer() {
        return this.player;
    }

    public ArrayList<String> getBlockedList() {
        return this.blockedList;
    }

    public ArrayList<String> getAllowedList() {
        return this.allowedList;
    }

    public PlayerChunk getPlayerChunk() {
        return this.playerChunk;
    }
}
