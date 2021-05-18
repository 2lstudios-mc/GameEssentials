package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class HatCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.hat")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                final PlayerInventory playerInventory = player.getInventory();
                final ItemStack helmet = playerInventory.getHelmet();
                if (helmet == null || helmet.getType() == Material.AIR) {
                    final ItemStack heldItem = playerInventory.getItem(playerInventory.getHeldItemSlot());
                    if (this.isValidItem(heldItem)) {
                        playerInventory.remove(heldItem);
                        playerInventory.setHelmet(heldItem);
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&', "&aDisfruta de tu nuevo sombrero!"));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&cSolo puedes usar bloques como sombrero!"));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cDesequipa tu sombrero actual para equipar uno nuevo!"));
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    private boolean isValidItem(final ItemStack itemStack) {
        if (itemStack != null) {
            final Material material = itemStack.getType();
            return material.isBlock();
        }
        return false;
    }
}
