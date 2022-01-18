package org.github.jcurtis.chunkd.guis;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.github.jcurtis.chunkd.commands.ChunkPerms;
import org.github.jcurtis.chunkd.commands.EditName;
import org.github.jcurtis.chunkd.commands.Unclaim;
import org.github.jcurtis.chunkd.managers.LocalDataManager;

public class ChunkManagerGUI implements Listener {
    private final Inventory inv;
    private final Chunkd chunkd;

    public ChunkManagerGUI(Chunkd chunkd) {
        this.inv = Bukkit.createInventory(null, 9, Component.text("Manage Chunk"));
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
        if (!event.getView().title().equals(Component.text("Manage Chunk"))) return;

        event.setCancelled(true);

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) event.getWhoClicked();

        if (event.getRawSlot() == 3) {
            new Unclaim(chunkd.chunkManager, p, p.getLocation().getChunk());
            event.getInventory().close();
        } else if (event.getRawSlot() == 4) {
            chunkd.editName.run(p, p.getLocation().getChunk());
            event.getInventory().close();
        } else if (event.getRawSlot() == 5) {
            new ChunkPerms();
        }
    }
}
