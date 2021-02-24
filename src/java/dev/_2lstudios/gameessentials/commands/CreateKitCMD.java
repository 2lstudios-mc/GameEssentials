// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.KitManager;
import org.bukkit.command.CommandExecutor;

public class CreateKitCMD implements CommandExecutor {
    private final KitManager kitManager;

    public CreateKitCMD(final EssentialsManager essentialsManager) {
        this.kitManager = essentialsManager.getKitManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length > 1) {
            if (sender.hasPermission("essentials.createkit")) {
                if (sender instanceof Player) {
                    try {
                        final String name = args[0].toUpperCase();
                        final int cooldown = Integer.parseInt(args[1]);
                        final Player player = (Player) sender;
                        final PlayerInventory playerInventory = player.getInventory();
                        this.kitManager.createKit(name, cooldown, playerInventory);
                        sender.sendMessage(ChatColor.GREEN + "Creaste el kit correctamente!");
                    } catch (NumberFormatException ignored) {
                        sender.sendMessage(ChatColor.RED + "Ingresaste un numero invalido!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/createkit <nombre> <cooldown>");
        }
        return true;
    }
}
