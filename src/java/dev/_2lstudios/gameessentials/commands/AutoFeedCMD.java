// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;

public class AutoFeedCMD implements CommandExecutor {
    private final Server server;
    private final Collection<Player> autofeedPlayers;

    public AutoFeedCMD(final Server server, final Collection<Player> autofeedPlayers) {
        this.server = server;
        this.autofeedPlayers = autofeedPlayers;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1 && sender.hasPermission("essentials.autofeed")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                if (this.autofeedPlayers.contains(player)) {
                    this.autofeedPlayers.remove(player);
                    player.sendMessage(ChatColor.RED + "Desactivaste el modo AutoFeed!");
                } else {
                    this.autofeedPlayers.add(player);
                    player.sendMessage(ChatColor.GREEN + "Activaste el modo AutoFeed!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else if (args.length > 0 && sender.hasPermission("essentials.autofeed.others")) {
            final Player player = this.server.getPlayer(args[0]);
            if (player != null) {
                if (this.autofeedPlayers.contains(player)) {
                    this.autofeedPlayers.remove(player);
                    player.sendMessage(ChatColor.RED + "Desactivaste el modo AutoFeed!");
                } else {
                    this.autofeedPlayers.add(player);
                    player.sendMessage(ChatColor.GREEN + "Activaste el modo AutoFeed!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
