package org.github.jcurtis.chunkd.guis;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerManagerGUI implements Listener {
    private Inventory inv;
    private Player p;

    public PlayerManagerGUI() {
        this.inv = Bukkit.createInventory(null, 9, Component.text("Manage Player Permissions"));
    }

    public void setPlayer(Player p) {
        this.p = p;
        initItems();
    }

    public void initItems() {
        ItemStack build = Utils.createGuiItem(Material.GRASS_BLOCK, "Can Build", "Click to allow " + p.getName() + " to build on this chunk.");
        inv.addItem(build);
    }

    public void open(final HumanEntity entity) {
        entity.sendMessage("Opening inv");
        entity.openInventory(inv);
    }
}
