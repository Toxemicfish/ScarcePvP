package com.scarce.pvp.utils;

import com.scarce.pvp.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class TeamsYML {

    static Core plugin;

    public TeamsYML(Core instance) {
        plugin = instance;
    }

    public static YamlConfiguration team = null;
    public static File teamFile = null;

    public static void reloadTeams() {
        if (teamFile == null) {
            teamFile = new File(Bukkit.getPluginManager().getPlugin("ScarcePvP").getDataFolder(), "teams.yml");
        }
        team = YamlConfiguration.loadConfiguration(teamFile);

        InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("ScarcePvP").getResource("teams.yml");
        if (defConfigStream != null) {
            YamlConfiguration defconfig = YamlConfiguration.loadConfiguration(defConfigStream);
            if (!teamFile.exists() || teamFile.length() == 0L) {
                team.setDefaults(defconfig);
            }
        }
    }

    public static FileConfiguration getTeams() {
        if (team == null) {
            reloadTeams();
        }
        return team;
    }

    public static void saveTeams() {
        if (team == null || teamFile == null) {
            return;
        }
        try {
            getTeams().save(teamFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + teamFile, ex);
        }
    }

}
