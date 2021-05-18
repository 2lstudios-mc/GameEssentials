package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class ClearCMD implements CommandExecutor {
    private final Plugin plugin;

    public ClearCMD(final Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1 && sender.hasPermission("essentials.clear")) {
            if (sender instanceof Player) {
                this.clearInventory((Player) sender);
                sender.sendMessage(ChatColor.GREEN + "Limpiaste tu inventario!");
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else if (args.length > 0 && sender.hasPermission("essentials.clear.others")) {
            final Player target = this.plugin.getServer().getPlayer(args[0]);
            if (target != null) {
                this.clearInventory(target);
                sender.sendMessage(ChatColor.GREEN + "Limpiaste el inventario de " + ChatColor.GRAY
                        + target.getDisplayName() + ChatColor.GREEN + "!");
            } else {
                sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    private void clearInventory(final Player player) {
        final PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        playerInventory.setHelmet((ItemStack) null);
        playerInventory.setChestplate((ItemStack) null);
        playerInventory.setLeggings((ItemStack) null);
        playerInventory.setBoots((ItemStack) null);
    }
}
