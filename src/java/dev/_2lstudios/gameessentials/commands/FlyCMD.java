// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class FlyCMD implements CommandExecutor {
    private final Plugin plugin;

    public FlyCMD(final Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1 && sender.hasPermission("essentials.fly")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                final boolean allow = !player.getAllowFlight();
                player.setAllowFlight(allow);
                player.setFlying(allow);
                if (player.getAllowFlight()) {
                    player.sendMessage(ChatColor.GREEN + "Se activo tu habilidad de vuelo!");
                } else {
                    player.sendMessage(ChatColor.RED + "Se desactivo tu habilidad de vuelo!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else if (args.length > 0 && sender.hasPermission("essentials.fly.others")) {
            final Player player = this.plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                player.setAllowFlight(!player.getAllowFlight());
                if (player.getAllowFlight()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aSe activo la habilidad de vuelo de " + player.getDisplayName() + "&a!"));
                    player.sendMessage(ChatColor.GREEN + "Se activo tu habilidad de vuelo!");
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cSe desactivo la habilidad de vuelo de " + player.getDisplayName() + "&c!"));
                    player.sendMessage(ChatColor.RED + "Se desactivo tu habilidad de vuelo!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cTe falta el permiso &4essentials.fly &cpara usar este comando!"));
            sender.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "&cPuedes obtener fly con el comando &e/vote&c!"));
        }
        return true;
    }
}
