package org.github.jcurtis.chunkd;

import org.bukkit.plugin.java.JavaPlugin;
import org.github.jcurtis.chunkd.managers.ChunkManager;

public final class Chunkd extends JavaPlugin {
    public ChunkManager chunkManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.chunkManager = new ChunkManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
