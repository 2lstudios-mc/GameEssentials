package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class FeedCMD implements CommandExecutor {
    private final Plugin plugin;

    public FeedCMD(final Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1 && sender.hasPermission("essentials.feed")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                player.setFoodLevel(20);
                player.setSaturation(4.0f);
                player.sendMessage(ChatColor.GREEN + "Tu barra de comida fue rellenada!");
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else if (args.length > 0 && sender.hasPermission("essentials.feed.others")) {
            final Player player = this.plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                player.setFoodLevel(20);
                player.setSaturation(4.0f);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aRellenaste la barra de comida de " + player.getDisplayName() + "&a!"));
                player.sendMessage(ChatColor.GREEN + "Tu barra de comida fue rellenada!");
            } else {
                sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
