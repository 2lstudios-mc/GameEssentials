// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class ToggleTPCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;

    public ToggleTPCMD(final EssentialsManager essentialsManager) {
        this.essentialsManager = essentialsManager;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.toggletp") && sender instanceof Player) {
            final PlayerManager playerManager = this.essentialsManager.getPlayerManager();
            final Player player = (Player) sender;
            final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(player.getUniqueId());
            if (essentialsPlayer.isTeleportRequests()) {
                essentialsPlayer.setTeleportRequests(false);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&cDesactivaste las peticiones de teletransporte!"));
            } else {
                essentialsPlayer.setTeleportRequests(true);
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "&aActivaste las peticiones de teletransporte!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
