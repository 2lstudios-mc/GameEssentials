package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class SpeedCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.speed")) {
            if (sender instanceof Player) {
                if (args.length > 0) {
                    try {
                        final Player player = (Player) sender;
                        final float amount = Float.parseFloat(args[0]);
                        player.setFlySpeed(amount);
                        player.setWalkSpeed(amount);
                    } catch (NumberFormatException ex) {
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "/speed <numero>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
