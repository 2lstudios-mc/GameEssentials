package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class BroadcastCMD implements CommandExecutor {
    private final Plugin plugin;

    public BroadcastCMD(final Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.broadcast")) {
            if (args.length > 0) {
                String message = "";
                for (final String arg : args) {
                    message = message.concat(String.valueOf(arg) + " ");
                }
                message = message.trim();
                this.plugin.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/broadcast <mensaje>"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
