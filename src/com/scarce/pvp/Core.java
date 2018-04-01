package com.scarce.pvp;

import com.scarce.pvp.commands.TeamCommands;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static Core instance;

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
        getCommand("team").setExecutor(new TeamCommands(this));
    }

    private void registerEvents() {
    }

    public static Core getInstance() {
        return instance;
    }

}
