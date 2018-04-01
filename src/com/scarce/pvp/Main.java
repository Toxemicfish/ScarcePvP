package com.scarce.pvp;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        registerCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
    }

    private void registerEvents() {
    }

    public static Main getInstance() {
        return instance;
    }
    
}
