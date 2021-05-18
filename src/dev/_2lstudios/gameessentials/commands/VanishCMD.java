package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.command.CommandExecutor;

public class VanishCMD implements CommandExecutor {
    private final PlayerManager playerManager;

    public VanishCMD(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.vanish") && sender instanceof Player) {
            final Player player = (Player) sender;
            if (this.playerManager.getPlayer(player.getUniqueId()).vanish()) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aActivaste el modo &bVANISH&a!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDesactivaste el modo &bVANISH&c!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
