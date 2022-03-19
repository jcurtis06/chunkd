package org.github.jcurtis.chunkd;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.github.jcurtis.chunkd.chunk.Chunks;
import org.github.jcurtis.chunkd.chunk.PlayerChunk;
import org.github.jcurtis.chunkd.commands.ChunkCMD;
import org.github.jcurtis.chunkd.commands.EditName;
import org.github.jcurtis.chunkd.events.ChangeChunkEvent;
import org.github.jcurtis.chunkd.guis.ChunkManagerGUI;
import org.github.jcurtis.chunkd.managers.ChunkManager;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

import java.io.IOException;
import java.util.logging.Level;

public final class Chunkd extends JavaPlugin {
    public ChunkManager chunkManager;
    public LocalDataManager ldm;
    public EditName editName;
    public Chunks chunks;

    @Override
    public void onEnable() {
        this.ldm = new LocalDataManager(this);
        this.chunks = new Chunks();
        this.chunkManager = new ChunkManager(this);
        this.editName = new EditName(chunkManager);

        ldm.createChunkConfig();

        System.out.println(ldm.getChunkConfig().get("chunks"));

        try {
            chunks.load();
        } catch (IOException | ClassNotFoundException e) {
            Bukkit.getLogger().log(Level.FINE, "Successfully loaded " + chunks.get().size() + " chunks");
        }

        this.getCommand("chunk").setExecutor(new ChunkCMD(this));

        this.getServer().getPluginManager().registerEvents(new ChangeChunkEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new ChunkManagerGUI(this), this);
        this.getServer().getPluginManager().registerEvents(editName, this);

        try {
            chunks.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        saveConfig();
        try {
            chunks.save();
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.FINE, "Successfully saved " + chunks.get().size() + " chunks");
        }
    }
}
