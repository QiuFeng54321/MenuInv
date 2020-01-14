package com.qiufeng.menuinv;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MainPlugin extends JavaPlugin {
    MenuInvGuiConf conf;
    public static MainPlugin self;
    public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;

    @Override
    public void onEnable() {
//        saveDefaultConfig();
//        getConfig().options().copyDefaults(true);
//        saveConfig();
        loadConf();
        getServer().getPluginManager().registerEvents(new MenuInvHandler(this), this);
        getServer().getPluginManager().registerEvents(new MenuInv(this), this);
        getCommand("givemenubook").setExecutor(new GiveMenuBookCommandExecutor(this));
        getCommand("openmenu").setExecutor(new OpenMenuCommandExecutor(this));
        getCommand("menuinv").setExecutor(new MenuInvUtilCommandExecutor(this));
        this.getLogger().info("MenuInv Enabled!");
        self = this;
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        if(rsp == null) return false;
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if(rsp == null) return false;
        perms = rsp.getProvider();
        return perms != null;
    }

    void loadConf() {
        reloadConfig();
        conf = new MenuInvGuiConf(this);
        conf.load();
    }
    @Override
    public void onDisable() {
        this.getLogger().info("MenuInv Disabled!");
    }

}
