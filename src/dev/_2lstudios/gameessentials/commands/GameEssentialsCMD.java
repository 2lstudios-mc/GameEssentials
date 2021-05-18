package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class GameEssentialsCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;

    public GameEssentialsCMD(final EssentialsManager essentialsManager) {
        this.essentialsManager = essentialsManager;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/gameessentials reload");
        } else {
            final String arg1 = args[0];
            if (arg1.equals("reload") && sender.hasPermission("essentials.reload")) {
                sender.sendMessage(ChatColor.GREEN + "GameEssentials recargado!");
                this.essentialsManager.reload();
            } else {
                sender.sendMessage(ChatColor.RED + "/gameessentials reload");
            }
        }
        return true;
    }
}
