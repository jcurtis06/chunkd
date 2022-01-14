package org.github.jcurtis.chunkd.managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.github.jcurtis.chunkd.Chunkd;

import java.io.File;
import java.io.IOException;

public class LocalDataManager {
    private final Chunkd chunkd;

    private File chunkConfigFile;
    private FileConfiguration chunkConfig;

    public LocalDataManager(Chunkd chunkd) {
        this.chunkd = chunkd;
    }

    public FileConfiguration getChunkConfig() {
        return this.chunkConfig;
    }

    public File getChunksFile() { return this.chunkConfigFile; }

    public void createChunkConfig() {
        chunkConfigFile = new File(chunkd.getDataFolder(), "chunks.yml");
        if (!chunkConfigFile.exists()) {
            chunkConfigFile.getParentFile().mkdirs();
            chunkd.saveResource("chunks.yml", false);
        }

        chunkConfig = new YamlConfiguration();
        try {
            chunkConfig.load(chunkConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
