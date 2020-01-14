package com.qiufeng.menuinv;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuInv implements Listener {
    private MainPlugin plugin;

    public MenuInv(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String world_name = player.getWorld().getName();
        if(plugin.conf.idPanelMap.containsKey(event.getView().getTitle())) {
            event.setCancelled(true);
            var panel = plugin.conf.idPanelMap.get(event.getView().getTitle());
            if(panel.elements.containsKey(event.getSlot())) {
                player.closeInventory();
                player.performCommand(panel.elements.get(event.getSlot()).command);
            }
        }
    }
}
