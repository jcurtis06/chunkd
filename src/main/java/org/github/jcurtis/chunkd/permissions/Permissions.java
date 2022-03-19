package org.github.jcurtis.chunkd.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.github.jcurtis.chunkd.chunk.PlayerChunk;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

import java.io.*;
import java.util.ArrayList;

public class Permissions {
    private ArrayList<PlayerPermission> permissions;

    public void add(PlayerPermission permission) {
        this.permissions.add(permission);
    }

    public void save() throws IOException {
        File chunksDataFolder = new File("plugins/Chunkd");
        chunksDataFolder.mkdir();
        FileOutputStream fos = new FileOutputStream(chunksDataFolder + "/chunkperms.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        for (PlayerPermission pp : permissions) {
            oos.writeObject(pp);
        }
    }

    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("plugins/Chunkd/chunkperms.dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        Object loadedObj;

        while ((loadedObj = ois.readObject()) != null) {
            permissions.add((PlayerPermission) loadedObj);
        }
    }
}
