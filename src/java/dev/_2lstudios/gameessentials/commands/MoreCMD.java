// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class MoreCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.more")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                final PlayerInventory playerInventory = player.getInventory();
                playerInventory.getItem(playerInventory.getHeldItemSlot()).setAmount(64);
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
