package org.github.jcurtis.chunkd;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.jcurtis.chunkd.commands.ChunkCMD;
import org.github.jcurtis.chunkd.events.ChangeChunkEvent;
import org.github.jcurtis.chunkd.guis.ChunkManagerGUI;
import org.github.jcurtis.chunkd.managers.ChunkManager;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

import java.io.IOException;

public final class Chunkd extends JavaPlugin {
    public ChunkManager chunkManager;
    public LocalDataManager ldm;

    @Override
    public void onEnable() {
        this.chunkManager = new ChunkManager(this);
        this.ldm = new LocalDataManager(this);

        ldm.createChunkConfig();

        chunkManager.loadChunksLocal();

        this.getCommand("chunk").setExecutor(new ChunkCMD(this));

        this.getServer().getPluginManager().registerEvents(new ChangeChunkEvent(this.chunkManager), this);
        this.getServer().getPluginManager().registerEvents(new ChunkManagerGUI(this), this);
    }

    @Override
    public void onDisable() {
        saveConfig();

        try {
            chunkManager.storeChunksLocal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
