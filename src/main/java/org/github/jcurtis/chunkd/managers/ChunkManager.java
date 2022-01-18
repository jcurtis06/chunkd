package org.github.jcurtis.chunkd.managers;

/*

Created by Jonathan Curtis, 1/2/22
https://github.com/jcurtis06/chunkd

 */

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.github.jcurtis.chunkd.Chunkd;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

public class ChunkManager {
    private final Chunkd chunkd;
    private final LocalDataManager ldm;

    // these variables will remain until switched over to config-only storage
    private Multimap<UUID, Chunk> chunkClaims = HashMultimap.create();
    private Multimap<Chunk, String> chunkNames = HashMultimap.create();

    public ChunkManager(Chunkd chunkd) {
        this.chunkd = chunkd;
        this.ldm = chunkd.ldm;
    }

    /*
    Chunks are stored using a custom config. The format is as follows:
    uuid.chunkKey.world/x/z/name

    "chunkKey" is just the chunks x combined with the z.
     */
    public void claim(Player owner, Chunk chunk) {
        ConfigurationSection section;

        // check if the player already has config section and take appropriate action
        if (ldm.getChunkConfig().getConfigurationSection(String.valueOf(owner.getUniqueId())) == null) {
            section = ldm.getChunkConfig().createSection(owner.getUniqueId().toString());
        } else {
            section = ldm.getChunkConfig().getConfigurationSection(String.valueOf(owner.getUniqueId()));
        }

        // get the chunk key
        int chunkKey = chunk.getX() + chunk.getZ();

        // set world, x, z, and name
        section.set(String.valueOf(chunkKey) + ".world", chunk.getWorld().toString());
        section.set(String.valueOf(chunkKey) + ".x", chunk.getX());
        section.set(String.valueOf(chunkKey) + ".Z", chunk.getZ());
        section.set(String.valueOf(chunkKey) + ".name", null);
    }

    /*
    This function *removes* an entry from the chunks.yml file.
    See above 'claim' definition for information on how data is saved.
     */
    public void unclaim(Player owner, Chunk chunk) {
        // get this player's section
        ConfigurationSection section = ldm.getChunkConfig().getConfigurationSection(owner.getUniqueId().toString());

        // get this chunk's key
        int chunkKey = chunk.getX() + chunk.getZ();

        // remove entry
        section.set(String.valueOf(chunkKey), null);
    }

    /*
    'isOwned()' returns true/false depending on whether the chunk is owned
    For getting the player who owns the chunk, the chunk, use 'getOwner()'
     */
    public Player getOwner(Chunk chunk) {


        // get the chunk key
        int chunkKey = chunk.getX() + chunk.getZ();
        // store result in a variable
        Player owner = null;

        // start with checking online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID pu = player.getUniqueId();

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()) == null) return null;

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()).contains(String.valueOf(chunkKey))) {
                return Bukkit.getPlayer(pu);
            }
        }

        // then check offline players
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            UUID pu = player.getUniqueId();

            if (ldm.getChunkConfig().getConfigurationSection(pu.toString()).contains(String.valueOf(chunkKey))) {
                return Bukkit.getPlayer(pu);
            }
        }

        // no one owns it, return null
        return null;
    }

    public Collection<Chunk> getPlayersChunks(Player player) {
        Collection<Chunk> chunks = null;
        ConfigurationSection cs = ldm.getChunkConfig().getConfigurationSection(player.getUniqueId().toString());

        for (String k : cs.getKeys(false)) {
            chunks.add(Bukkit.getWorld(cs.getString(k + ".world")).getChunkAt(cs.getInt(k + ".x"), cs.getInt(k + ".z")));
        }

        return chunks;
    }

    public Collection<Chunk> getAllClaimedChunks() {
        return chunkClaims.values();
    }
    /*
    public void loadChunksLocal() {
        if (chunkd.ldm.getChunkConfig().getRoot().getKeys(false) == null) return;

        System.out.println("loading chunks");

        chunkd.ldm.getChunkConfig().getRoot().getKeys(false).forEach(key -> {
            UUID u = UUID.fromString(key);

            System.out.println("found key " + key);

            chunkd.ldm.getChunkConfig().getConfigurationSection(u.toString()).getKeys(false).forEach(key1 -> {
                chunkd.ldm.getChunkConfig().getConfigurationSection(u.toString() + "." + key1).getKeys(false).forEach(val -> {
                    Chunk c = Bukkit.getWorld(chunkd.ldm.getChunkConfig().getString(u + "." + key1 + ".world")).getChunkAt(chunkd.ldm.getChunkConfig().getInt(u + "." + key1 + ".x"), chunkd.ldm.getChunkConfig().getInt(u + "." + key1 + ".z"));

                    chunkClaims.put(u, c);
                });
            });
        });
    }
     */

    public void storeChunksLocal() throws IOException {
        for (UUID u : chunkClaims.keys()) {
            chunkd.ldm.getChunkConfig().createSection(u.toString());

            for (Chunk c : chunkClaims.get(u)) {
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".x", c.getX());
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".z", c.getZ());
                chunkd.ldm.getChunkConfig().set(u.toString() + "." + c.getX() + c.getZ() + ".world", c.getWorld().getName());
            }
        }

        chunkd.ldm.getChunkConfig().save(chunkd.ldm.getChunksFile());
    }
}
