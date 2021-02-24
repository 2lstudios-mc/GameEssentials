// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class ItemInfoCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.iteminfo")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                final PlayerInventory playerInventory = player.getInventory();
                final ItemStack itemStack = playerInventory.getItem(playerInventory.getHeldItemSlot());
                if (itemStack != null) {
                    final Material material = itemStack.getType();
                    final byte data = itemStack.getData().getData();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aMaterial: &b" + material + "\n&aData: &b" + data));
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
