package org.github.jcurtis.chunkd;

import com.google.gson.Gson;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.jcurtis.chunkd.commands.ChunkCMD;
import org.github.jcurtis.chunkd.events.ChangeChunkEvent;
import org.github.jcurtis.chunkd.managers.ChunkManager;

public final class Chunkd extends JavaPlugin {
    public Gson gson;
    public ChunkManager chunkManager;

    @Override
    public void onEnable() {
        this.gson = new Gson();
        this.chunkManager = new ChunkManager(this);

        this.getCommand("chunk").setExecutor(new ChunkCMD(this));

        this.getServer().getPluginManager().registerEvents(new ChangeChunkEvent(this.chunkManager), this);
    }

    @Override
    public void onDisable() {

    }
}
