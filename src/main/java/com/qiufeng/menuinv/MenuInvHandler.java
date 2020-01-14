package com.qiufeng.menuinv;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.regex.Matcher;

public class MenuInvHandler implements Listener {
    private MainPlugin plugin;

    public MenuInvHandler(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            plugin.getLogger().info("Player " + player.getName() + " used " + e.getMaterial());
            if(plugin.conf.materialMapMap.containsKey(e.getMaterial())) {
                var name = e.getItem().getItemMeta().getDisplayName();
                if(plugin.conf.materialMapMap.get(e.getMaterial()).containsKey(name)) {
                    var panel = plugin.conf.materialMapMap.get(e.getMaterial()).get(name);
//                    plugin.getLogger().info("Inventory Triggered: " + panel.id);
                    MenuInvUtils.openMenu(player, plugin, panel);
                }
            }
        }
    }
}
