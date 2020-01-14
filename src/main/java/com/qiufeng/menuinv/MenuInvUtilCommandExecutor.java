package com.qiufeng.menuinv;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MenuInvUtilCommandExecutor implements CommandExecutor {
    private MainPlugin plugin;

    public MenuInvUtilCommandExecutor(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length < 1) {
            commandSender.sendMessage("Please specify an action");
            return false;
        } else {
            if(strings[0].equals("reload")) {
                plugin.loadConf();
                commandSender.sendMessage("MenuInv Reloaded!");
                return true;
            } else {
                commandSender.sendMessage("Unknown action: " + strings[0] + ".");
                return false;
            }
        }
    }
}
