package com.qiufeng.menuinv;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.StringJoiner;

public class GiveMenuBookCommandExecutor implements CommandExecutor {
    private MainPlugin plugin;

    public GiveMenuBookCommandExecutor(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(strings.length < 1) {
                commandSender.sendMessage("You have to specify a name");
                return false;
            }
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (String arg : strings) {
                stringJoiner.add(arg);
            }
            String res = stringJoiner.toString();
            player.getInventory().addItem(plugin.conf.panelMap.get(res).item);
            return true;
        } else {
            commandSender.sendMessage("Command has to be done by a player");
            return false;
        }
    }
}
