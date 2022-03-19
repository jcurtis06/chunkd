package org.github.jcurtis.chunkd.guis;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.github.jcurtis.chunkd.Chunkd;
import org.github.jcurtis.chunkd.commands.PermsGUI;
import org.github.jcurtis.chunkd.commands.Unclaim;

public class PermsManagerGUI {
    private final Inventory inv;
    private final Chunkd chunkd;

    public PermsManagerGUI(Chunkd chunkd) {
        this.inv = Bukkit.createInventory(null, 18, Component.text("Manage Chunk"));
        this.chunkd = chunkd;

        initItems();
    }

    public void initItems() {
        Bukkit.getOnlinePlayers().forEach(o -> {
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwningPlayer(o);

            skullMeta.displayName(Component.text(ChatColor.RESET + "" + ChatColor.GREEN + o.getName()));

            skull.setItemMeta(skullMeta);

            inv.addItem(skull);
        });
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
    }
}
