// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class TeleportCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.tp") && sender instanceof Player) {
            if (args.length > 1 && sender.hasPermission("essentials.tp.others")) {
                final Player player = Bukkit.getPlayer(args[0]);
                final Player player2 = Bukkit.getPlayer(args[1]);
                if (player != null && player2 != null) {
                    player.teleport(player2.getLocation());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aTeletransportaste a &b"
                            + player.getDisplayName() + "&a a &b" + player2.getDisplayName() + "&a!"));
                } else {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "&cUno de los jugadores no esta en linea!"));
                }
            } else if (args.length > 0) {
                final Player player = (Player) sender;
                final Player player2 = Bukkit.getPlayer(args[0]);
                if (player2 != null) {
                    player.teleport(player2.getLocation());
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aFuiste teletransportado a &b" + player2.getDisplayName() + "&a!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEl jugador no esta en linea!"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/tp <jugador> [jugador]"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
