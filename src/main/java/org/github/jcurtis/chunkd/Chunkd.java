package org.github.jcurtis.chunkd;

import org.bukkit.plugin.java.JavaPlugin;
import org.github.jcurtis.chunkd.commands.ChunkCMD;
import org.github.jcurtis.chunkd.managers.ChunkManager;

public final class Chunkd extends JavaPlugin {
    public ChunkManager chunkManager;

    @Override
    public void onEnable() {
        this.chunkManager = new ChunkManager(this);

        this.getCommand("chunk").setExecutor(new ChunkCMD(this));
    }

    @Override
    public void onDisable() {

    }
}
