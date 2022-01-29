package org.github.jcurtis.chunkd.permissions;

import org.bukkit.permissions.Permission;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

import java.util.ArrayList;

public class Permissions {
    private ArrayList<PlayerPermission> permissions;

    public void add(PlayerPermission permission) {
        this.permissions.add(permission);
    }

    public void save(LocalDataManager localDataManager) {

    }
}
