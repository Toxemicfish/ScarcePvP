package com.scarce.pvp.commands;

import com.avaje.ebeaninternal.server.core.Message;
import com.scarce.pvp.Core;
import com.scarce.pvp.utils.MessageManager;
import com.scarce.pvp.utils.TeamsYML;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamCommands implements CommandExecutor {

    Core plugin;

    public TeamCommands(Core instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String uuid = player.getUniqueId().toString();
            if (args.length == 0) {
                MessageManager.TEAM_HELP(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("create")) {

                if (TeamsYML.getTeams().getStringList("PIT").contains(uuid)) {
                    MessageManager.ALREADY_IN_A_TEAM(player);
                    return true;
                } else {
                    if (args.length != 1) {
                        if(args.length != 2) {
                            TeamsYML.getTeams().set("players." + player.getUniqueId() + ".team", args[1]);
                            TeamsYML.getTeams().set(args[1] + ".owner", uuid);
                            TeamsYML.getTeams().set(args[1] + ".password", args[2]);

                            List<String> a = new ArrayList<String>();
                            a.add(player.getUniqueId().toString());
                            TeamsYML.getTeams().set(args[1] + ".members", a);

                            List<String> PIT = TeamsYML.getTeams().getStringList("PIT");
                            PIT.add(uuid);
                            TeamsYML.getTeams().set("PIT", PIT);
                            TeamsYML.saveTeams();

                            MessageManager.TEAM_CREATED(player);
                            return true;
                        } else {
                            MessageManager.TEAM_CREATE_ARGS_ERROR_WITH_REPLACEMENT_FOR_TEAM_NAME(player, args[1]);
                            //MessageManager.TEAM_CREATE_ARGS_ERROR(player);
                            return true;
                        }
                    } else {
                        MessageManager.TEAM_CREATE_ARGS_ERROR(player);
                        return true;
                    }
                }
            }

            if (args[0].equalsIgnoreCase("leave")) {
                String team = TeamsYML.getTeams().getString("players." + uuid + ".team");
                if (team != null) {

                    List<String> a = TeamsYML.getTeams().getStringList(team + ".members");
                    a.remove(player.getUniqueId().toString());
                    if (a.size() == 0) {
                        TeamsYML.getTeams().set(team, null);
                        Bukkit.broadcastMessage(ChatColor.YELLOW + team + ChatColor.DARK_AQUA + " has been disbanded!");
                    } else {
                        TeamsYML.getTeams().set(team + ".members", a);
                        for (String s : a) {
                            if (Bukkit.getPlayerExact(s) != null) {
                                Player members = Bukkit.getPlayerExact(s);
                                members.sendMessage(ChatColor.DARK_AQUA + player.getDisplayName() + ChatColor.GRAY + " Has left the team!");
                            }
                        }
                    }

                    TeamsYML.getTeams().set("players." + uuid, null);
                    List<String> b = TeamsYML.getTeams().getStringList("PIT");
                    b.remove(uuid);
                    TeamsYML.getTeams().set("PIT", b);
                    TeamsYML.saveTeams();

                    player.sendMessage(ChatColor.GRAY + "You have left " + ChatColor.DARK_AQUA + team + ChatColor.GRAY + "!");

                    return true;
                } else {
                    MessageManager.NOT_IN_A_TEAM(player);
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("info")) {
                String team = TeamsYML.getTeams().getString("players." + uuid + ".team");
                if (team != null) {
                    player.sendMessage(ChatColor.GRAY + "====================");
                    player.sendMessage(ChatColor.DARK_AQUA + "Team infomation");
                    player.sendMessage(MessageManager.color("&7Team: " + ChatColor.DARK_AQUA + team));
                    Player owner = Bukkit.getPlayer(TeamsYML.getTeams().getString(team + ".owner").toString());
                    //              Player owner = Bukkit.getPlayer(TeamsYML.getTeams().getString(team + ".owner").toString());
                    // player.sendMessage(MessageManager.color("&7Owner: " + ChatColor.DARK_AQUA + owner.getName()));


                    player.sendMessage(ChatColor.GRAY + "Members: ");
                    for (String s : TeamsYML.getTeams().getStringList(team + ".members")) {
                        Player members = Bukkit.getPlayer(s);
                        Player p2 = Bukkit.getPlayerExact(members.getName());
                        String s2 = s;
                        if (p2 != null) {
                            s2 = s2 + ChatColor.GREEN + " (Online)";
                        } else {
                            s2 = s2 + ChatColor.RED + " (Offline)";
                        }
                        player.sendMessage("- " + ChatColor.AQUA + p2);
                    }
                    player.sendMessage(ChatColor.GRAY + "====================");

                    return true;
                } else {
                    MessageManager.NOT_IN_A_TEAM(player);
                    return true;
                }
            }

            if (args[0].equalsIgnoreCase("help")) {
                MessageManager.TEAM_HELP(player);
                return true;
            } else {
                MessageManager.TEAM_HELP(player);
            }

            return true;
        } else {
            MessageManager.playerOnly(sender);
        }
        return true;
    }
}