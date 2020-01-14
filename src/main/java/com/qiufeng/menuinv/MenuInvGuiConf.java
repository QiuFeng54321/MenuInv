package com.qiufeng.menuinv;

import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuInvGuiConf {
    private FileConfiguration configuration;
    private MainPlugin plugin;
    public Map<String, GuiPanel> panelMap = new HashMap<>();
    public Map<String, GuiPanel> idPanelMap = new HashMap<>();
    public Map<Material, Map<String, GuiPanel>> materialMapMap = new HashMap<>();

    public MenuInvGuiConf(MainPlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }

    public void load() {
        var panels_section = configuration.getConfigurationSection("panels");
        for (String key : panels_section.getKeys(false)) {
            var panel_section = panels_section.getConfigurationSection(key);
            plugin.getLogger().info(panel_section.getValues(true).toString());
            var menu_item_section = panel_section.getConfigurationSection("items.menu");

            var menu_item = (menu_item_section.getString("ID").equals("none") ? null : Material.getMaterial(menu_item_section.getString("ID")));
            var menu_item_display = menu_item_section.getString("name");
            var gui_elements = panel_section.getMapList("gui.items");
            int element_amount = gui_elements.size();
            Map<Integer, GuiElement> elements = new HashMap<>();
            for (var gui_element : gui_elements) {
                Material item = Material.getMaterial((String) gui_element.get("item"));
                String display = (String) gui_element.get("display");
                String command = (String) gui_element.get("command");
                String id = (String) gui_element.get("id");
                Integer slot = (Integer)gui_element.get("slot");
                assert item != null;
                plugin.getLogger().info(String.format("New Gui Element: item: %s, display: %s, id: %s, command: %s, slot: %d",
                        gui_element.get("item"), display, command, id, slot));
                GuiElement element = new GuiElement(item, display, id, command, slot);
                elements.put(slot, element);
            }
            GuiPanel panel = new GuiPanel(key, menu_item, menu_item_display, elements);
            plugin.getLogger().info(panel.toString());
            if(panel.item != null) {
                panelMap.put(panel.item.getItemMeta().getDisplayName(), panel);
                if (!materialMapMap.containsKey(panel.item.getType())) {
                    materialMapMap.put(panel.item.getType(), new HashMap<>());
                }
                materialMapMap.get(panel.item.getType()).put(panel.item.getItemMeta().getDisplayName(), panel);
            }
            idPanelMap.put(panel.id, panel);
        }
    }

    static class GuiPanel {
        String id;
        ItemStack item;
        Map<Integer, GuiElement> elements = new HashMap<>();

        public GuiPanel(String id, Material menu, String menu_name) {
            this.id = id;
            if (menu == null) {
                this.item = null;
            } else {
                item = MenuInvUtils.getItem(menu, menu_name);
            }
        }

        public GuiPanel(String id, Material menu, String menu_name, Map<Integer, GuiElement> elements) {
            this.id = id;
            if (menu == null) {
                this.item = null;
            } else {
                item = MenuInvUtils.getItem(menu, menu_name);
            }
            this.elements = elements;
        }

        @Override
        public String toString() {
            return "GuiPanel{" +
                    "id='" + id + '\'' +
                    ", item=" + item +
                    ", elements=" + elements +
                    '}';
        }
    }

    static class GuiElement {
        Material item;
        String display;
        String id;
        String command;
        int slot;

        public GuiElement(Material item, String display, String id, String command, int slot) {
            this.item = item;
            this.display = display;
            this.command = command;
            this.slot = slot;
            this.id = id;
        }

        @Override
        public String toString() {
            return "GuiElement{" +
                    "item=" + item +
                    ", display='" + display + '\'' +
                    ", id='" + id + '\'' +
                    ", command='" + command + '\'' +
                    ", slot=" + slot +
                    '}';
        }
    }
}
