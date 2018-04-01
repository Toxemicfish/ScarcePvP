package com.scarce.pvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    static String prefix = ChatColor.translateAlternateColorCodes('&', "&7[&eScarcePvP&7]&f ");

    public static String getPrefix() {
        return prefix;
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void playerOnly(CommandSender sender) {
        sender.sendMessage(prefix + ChatColor.RED + "Player only command!");
    }

    public static void consoleOnly(Player player) {
        player.sendMessage(prefix + ChatColor.RED + "Console only command!");
    }

    public static void TEAM_HELP(Player player) {
        player.sendMessage(ChatColor.GRAY + "---- " + ChatColor.AQUA + "Anyone" + ChatColor.GRAY + " ----");
        player.sendMessage(ChatColor.DARK_AQUA + "/team create <team> <password>" + ChatColor.GRAY + " - To create a team!");
        player.sendMessage(ChatColor.DARK_AQUA + "/team join <team> <password>" + ChatColor.GRAY + " - To join a team!");
        player.sendMessage(ChatColor.DARK_AQUA + "/team leave" + ChatColor.GRAY + " - To leave a team!");
        player.sendMessage(ChatColor.DARK_AQUA + "/team info <team>" + ChatColor.GRAY + " - To view a team!");
        player.sendMessage(ChatColor.GRAY + "---- " + ChatColor.AQUA + "Owner" + ChatColor.GRAY + " ----");
        player.sendMessage(ChatColor.DARK_AQUA + "/team kick <player>" + ChatColor.GRAY + " - To kick in your team!");
        player.sendMessage(ChatColor.DARK_AQUA + "/team cpass <newPassword>" + ChatColor.GRAY + " - To change your team's password!");
    }

    public static void ALREADY_IN_A_TEAM(Player player) {
        player.sendMessage(prefix + ChatColor.RED + "You must leave your current team, to create a new one!");
    }

    public static void TEAM_CREATE_ARGS_ERROR(Player player) {
        player.sendMessage(prefix + ChatColor.RED + "Usage: " + ChatColor.DARK_AQUA + "/team create <team> <password>");
    }

    public static void TEAM_CREATE_ARGS_ERROR_WITH_REPLACEMENT_FOR_TEAM_NAME(Player player, String replacement) {
        player.sendMessage(color(prefix + ChatColor.RED + "Usage: " + ChatColor.DARK_AQUA + "/team create %s <password>").replace("%s", replacement));
    }

    public static void TEAM_CREATED(Player player) {
        player.sendMessage(prefix + ChatColor.YELLOW + TeamsYML.getTeams().getString("players." + player.getUniqueId() + ".team") + ChatColor.GRAY + " was created " + ChatColor.GREEN + "successfully!");
    }

    public static void NOT_IN_A_TEAM(Player player) {
        player.sendMessage(prefix + ChatColor.RED + "You are not in a team!");
    }
}
