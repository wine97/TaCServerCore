package de.winedev.servercore.framework;

import de.winedev.servercore.ServerCore;
import de.winedev.servercore.commands.CMD_perm;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;

public class CommandHandler{

    public static void init() {
        regCommand("perm", new CMD_perm());

    }

    private static void regCommand(String cmd, CommandExecutor executor){
        ServerCore.serverCore.getCommand(cmd).setExecutor(executor);
        System.out.println("Der Command "+ executor.getClass().getSimpleName()+" wurde regestriert");
    }
}
