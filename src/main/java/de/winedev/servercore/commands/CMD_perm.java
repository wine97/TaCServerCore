package de.winedev.servercore.commands;

import de.winedev.servercore.Files;
import de.winedev.servercore.ServerCore;
import de.winedev.servercore.framework.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;


public class CMD_perm implements CommandExecutor, Files {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 4) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("servercore.perm") || p.hasPermission("servercore.*") || p.isOp()) {
                    if (args[0].equalsIgnoreCase("add")) {
                        if (args[1].equalsIgnoreCase("user")) {

                            FileManager.load(users);
                            String Starget = args[2];
                            Player target = Bukkit.getPlayerExact(Starget);

                            if (Bukkit.getOnlinePlayers().contains(target)) {

                                String PlayerPath = "users." + target.getUniqueId() + ".";
                                String PlayerNamePath = PlayerPath + "name";
                                String PlayerName = users.getConfig().get(PlayerNamePath).toString();

                                // add Player Specific Permissions to Player
                                List userPerm = users.getConfig().getList(PlayerPath + "permissions");
                                if (!target.hasPermission(args[3].toString())) {
                                    if (!userPerm.contains(args[3])) {
                                        userPerm.add(args[3].toString());
                                        target.addAttachment(ServerCore.pl, args[3].toString(), true);
                                        users.getConfig().set(PlayerPath + "permissions", userPerm);
                                        FileManager.save(users);
                                        p.sendMessage("§3Du hast dem Spieler » §e" + PlayerName + "§3 die Berechtigung » §e" + args[3] + "§3 gegeben");
                                    } else {
                                        p.sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                                    }
                                } else {
                                    p.sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                                }

                            } else {
                                p.sendMessage("§cDer Spieler " + args[2] + " ist offline");
                            }
                        } else if(args[1].equalsIgnoreCase("group")){
                            FileManager.load(groups);
                            if(groups.getConfig().contains(args[2].toLowerCase(Locale.ROOT).toString())){
                                List groupsPerm = groups.getConfig().getList(args[2].toLowerCase(Locale.ROOT).toString() + ".permissions");
                                if (!groupsPerm.contains(args[3])) {
                                    groupsPerm.add(args[3].toString());
                                    groups.getConfig().set(args[2].toLowerCase(Locale.ROOT).toString() + "permissions", groupsPerm);
                                    FileManager.save(groups);
                                    p.sendMessage("§3Du hast der Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + "§3 die Berechtigung » §e" + args[3] + "§3 gegeben");
                                } else {
                                    p.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                                }
                            }else{
                                p.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §cexistiert nicht");
                            }

                        }else{

                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        if (args[1].equalsIgnoreCase("user")) {

                            FileManager.load(users);
                            String Starget = args[2];
                            Player target = Bukkit.getPlayerExact(Starget);

                            if (Bukkit.getOnlinePlayers().contains(target)) {

                                String PlayerPath = "users." + target.getUniqueId() + ".";
                                String PlayerNamePath = PlayerPath + "name";
                                String PlayerName = users.getConfig().get(PlayerNamePath).toString();


                                // add Player Specific Permissions to Player
                                List userPerm = users.getConfig().getList(PlayerPath + "permissions");
                                if (target.hasPermission(args[3].toString())) {
                                    if (userPerm.contains(args[3])) {
                                        userPerm.remove(args[3].toString());
                                        target.addAttachment(ServerCore.pl, args[3].toString(), true);
                                        users.getConfig().set(PlayerPath + "permissions", userPerm);
                                        FileManager.save(users);
                                        p.sendMessage("§3Du hast dem Spieler » §e" + PlayerName + "§3 die Berechtigung » §e" + args[3] + "§3 entzogen");
                                    } else {
                                        p.sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c nicht");
                                    }
                                } else {
                                    p.sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c nicht");
                                }

                            } else {
                                p.sendMessage("§cDer Spieler " + args[2] + " ist offline");
                            }
                        } else if(args[1].equalsIgnoreCase("group")){
                            FileManager.load(groups);
                            if(groups.getConfig().contains(args[2].toLowerCase(Locale.ROOT).toString())){
                                List groupsPerm = groups.getConfig().getList(args[2].toLowerCase(Locale.ROOT).toString() + ".permissions");
                                if (groupsPerm.contains(args[3])) {
                                    groupsPerm.remove(args[3].toString());
                                    groups.getConfig().set(args[2].toLowerCase(Locale.ROOT).toString() + "permissions", groupsPerm);
                                    FileManager.save(groups);
                                    p.sendMessage("§3Du hast der Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + "§3 die Berechtigung » §e" + args[3] + "§3 entzogen");
                                } else {
                                    p.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §chat die Berechtigung » §e" + args[3] + "§c nicht");
                                }
                            }else{
                                p.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §cexistiert nicht");
                            }

                        }else{
                            p.sendMessage("§cUsage: /perm <add/remove> <user/group> <target> <perm>");
                        }
                    } else {
                        p.sendMessage("§cUsage: /perm <add/remove> <user/group> <target> <perm>");
                    }
                } else {
                    p.sendMessage("§cDu hast keine Berechtigung diesen Befehl aus zuführen");
                }
            } else {// COnsole COmmand


                if (args[0].equalsIgnoreCase("add")) {
                    if (args[1].equalsIgnoreCase("user")) {

                        FileManager.load(users);
                        String Starget = args[2];
                        Player target = Bukkit.getPlayerExact(Starget);

                        if (Bukkit.getOnlinePlayers().contains(target)) {

                            String PlayerPath = "users." + target.getUniqueId() + ".";
                            String PlayerNamePath = PlayerPath + "name";
                            String PlayerName = users.getConfig().get(PlayerNamePath).toString();

                            // add Player Specific Permissions to Player
                            List userPerm = users.getConfig().getList(PlayerPath + "permissions");
                            if (!target.hasPermission(args[3].toString())) {
                                if (!userPerm.contains(args[3])) {
                                    userPerm.add(args[3].toString());
                                    target.addAttachment(ServerCore.pl, args[3].toString(), true);
                                    users.getConfig().set(PlayerPath + "permissions", userPerm);
                                    FileManager.save(users);
                                    Bukkit.getConsoleSender().sendMessage("§3Du hast dem Spieler » §e" + PlayerName + "§3 die Berechtigung » §e" + args[3] + "§3 gegeben");
                                } else {
                                    Bukkit.getConsoleSender().sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                                }
                            } else {
                                Bukkit.getConsoleSender().sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                            }

                        } else {
                            Bukkit.getConsoleSender().sendMessage("§cDer Spieler " + args[2] + " ist offline");
                        }
                    } else if(args[1].equalsIgnoreCase("group")){
                        FileManager.load(groups);
                        if(groups.getConfig().contains(args[2].toLowerCase(Locale.ROOT).toString())){
                            List groupsPerm = groups.getConfig().getList(args[2].toLowerCase(Locale.ROOT).toString() + ".permissions");
                            if (!groupsPerm.contains(args[3])) {
                                groupsPerm.add(args[3].toString());
                                groups.getConfig().set(args[2].toLowerCase(Locale.ROOT).toString() + "permissions", groupsPerm);
                                FileManager.save(groups);
                                sender.sendMessage("§3Du hast der Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + "§3 die Berechtigung » §e" + args[3] + "§3 gegeben");
                            } else {
                                sender.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                            }
                        }else{
                            sender.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §cexistiert nicht");
                        }

                    }else{
                        sender.sendMessage("§cUsage: /perm <add/remove/list> <user/group> <target> <perm>");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args[1].equalsIgnoreCase("user")) {

                        FileManager.load(users);
                        String Starget = args[2];
                        Player target = Bukkit.getPlayerExact(Starget);

                        if (Bukkit.getOnlinePlayers().contains(target)) {

                            String PlayerPath = "users." + target.getUniqueId() + ".";
                            String PlayerNamePath = PlayerPath + "name";
                            String PlayerName = users.getConfig().get(PlayerNamePath).toString();

                            // add Player Specific Permissions to Player
                            List userPerm = users.getConfig().getList(PlayerPath + "permissions");
                            if (target.hasPermission(args[3].toString())) {
                                if (userPerm.contains(args[3])) {
                                    userPerm.remove(args[3].toString());
                                    target.addAttachment(ServerCore.pl, args[3].toString(), true);
                                    users.getConfig().set(PlayerPath + "permissions", userPerm);
                                    FileManager.save(users);
                                    Bukkit.getConsoleSender().sendMessage("§3Du hast dem Spieler » §e" + PlayerName + "§3 die Berechtigung » §e" + args[3] + "§3 gegeben");
                                } else {
                                    Bukkit.getConsoleSender().sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                                }
                            } else {
                                Bukkit.getConsoleSender().sendMessage("§cDer Spieler » §e" + PlayerName + " §chat die Berechtigung » §e" + args[3] + "§c bereits");
                            }

                        } else {
                            Bukkit.getConsoleSender().sendMessage("§cDer Spieler " + args[2] + " ist offline");
                        }
                    } else if(args[1].equalsIgnoreCase("group")){
                        FileManager.load(groups);
                        if(groups.getConfig().contains(args[2].toLowerCase(Locale.ROOT).toString())){
                            List groupsPerm = groups.getConfig().getList(args[2].toLowerCase(Locale.ROOT).toString() + ".permissions");
                            if (groupsPerm.contains(args[3])) {
                                groupsPerm.remove(args[3].toString());
                                groups.getConfig().set(args[2].toLowerCase(Locale.ROOT).toString() + "permissions", groupsPerm);
                                FileManager.save(groups);
                                sender.sendMessage("§3Du hast der Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + "§3 die Berechtigung » §e" + args[3] + "§3 entzogen");
                            } else {
                                sender.sendMessage("§cDer Spieler » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §chat die Berechtigung » §e" + args[3] + "§c nicht");
                            }
                        }else{
                            sender.sendMessage("§cDie Gruppe » §e" + args[2].toUpperCase(Locale.ROOT).toString() + " §cexistiert nicht");
                        }

                    }else{
                        sender.sendMessage("§cUsage: /perm <add/remove/list> <user/group> <target> <perm>");
                    }
                } else {
                    Bukkit.getConsoleSender().sendMessage("§cUsage: /perm <add/remove> <user/group> <target> <perm>");
                }
            }
        } else if(args.length == 3){
            if (args[0].equalsIgnoreCase("list")) {
                sender.sendMessage("§c8Work in Progress");
            }else{
                sender.sendMessage("§cUsage: /perm <add/remove/list> <user/group> <target> <perm>");
            }
        } else {
            // args != 4 than give this message
            sender.sendMessage("§cUsage: /perm <add/remove/list> <user/group> <target> <perm>");
        }
        return true;
    }

}
