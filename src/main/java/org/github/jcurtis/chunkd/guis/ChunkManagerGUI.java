package org.github.jcurtis.chunkd.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.github.jcurtis.chunkd.Chunkd;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

public class ChunkManagerGUI implements Listener {
    private final Inventory inv;
    private final Chunkd chunkd;

    public ChunkManagerGUI(Chunkd chunkd) {
        this.inv = Bukkit.createInventory(null, 9, "Manage Chunk");
        this.chunkd = chunkd;

        initItems();
    }

    public void initItems() {
        inv.setItem(3, Utils.createGuiItem(Material.REDSTONE, "Unclaim Chunk", "WARNING:", "Clicking this will", "unclaim this chunk"));
        inv.setItem(4, Utils.createGuiItem(Material.NAME_TAG, "Edit Name", "Click to set a", "custom name that", "appears when a player", "enters this chunk."));
        inv.setItem(5, Utils.createGuiItem(Material.BARRIER, "Manage Permissions", "Click to manage", "which players can do", "what in this chunk."));
    }

    public void open(final HumanEntity entity) {
        entity.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        System.out.println("reached");

        if (!event.getInventory().equals(inv)) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) event.getWhoClicked();

        if (event.getRawSlot() == 3) {
            chunkd.chunkManager.unclaim(p, p.getLocation().getChunk());
            p.sendMessage("You have successfully unclaimed this chunk.");
        } else if (event.getRawSlot() == 4) {
            p.sendMessage("Please type what you would like to rename this chunk to in the chat:");
        } else if (event.getRawSlot() == 5) {
            p.sendMessage("Coming soon");
        }
    }
}
