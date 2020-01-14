package com.qiufeng.menuinv;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MenuInvUtils {
    public static void openMenu(Player player, MainPlugin plugin, MenuInvGuiConf.GuiPanel panel) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, panel.id);
        for (Map.Entry<Integer, MenuInvGuiConf.GuiElement> element : panel.elements.entrySet()) {
//            plugin.getLogger().info("Add Element: " + element.getValue());
            ItemStack itemStack = MenuInvUtils.getItem(element.getValue().item, element.getValue().display);
            inventory.setItem(element.getKey(), itemStack);
        }
        player.openInventory(inventory);
    }
    public static ItemStack getItem(Material material, String name) {
        var item = new ItemStack(material);
        var meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
