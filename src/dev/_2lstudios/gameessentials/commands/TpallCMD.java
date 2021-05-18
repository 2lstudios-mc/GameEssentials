package dev._2lstudios.gameessentials.commands;

import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class TpallCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.tpall") && sender instanceof Player) {
            final Player senderPlayer = (Player) sender;
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if (player != senderPlayer) {
                    player.teleport((Entity) senderPlayer);
                }
            }
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "&aTeletransportaste a todos los jugadores hacia ti!"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
