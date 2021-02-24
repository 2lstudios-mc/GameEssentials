// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class TpacceptCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;

    public TpacceptCMD(final EssentialsManager essentialsManager) {
        this.essentialsManager = essentialsManager;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.tpaccept") && sender instanceof Player) {
            final PlayerManager playerManager = this.essentialsManager.getPlayerManager();
            final Player player = (Player) sender;
            final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(player.getUniqueId());
            final String teleportRequest = essentialsPlayer.getTeleportRequest();
            if (teleportRequest != null
                    && System.currentTimeMillis() - essentialsPlayer.getLastTeleportRequestTime() < 60000L) {
                final Player player2 = Bukkit.getPlayer(teleportRequest);
                if (player2 != null) {
                    if (player != player2) {
                        final EssentialsPlayer essentialsPlayer2 = playerManager.getPlayer(player2.getUniqueId());
                        if (essentialsPlayer2 != null) {
                            essentialsPlayer.setTeleportRequest(null);
                            essentialsPlayer2.setTeleportTask(new TeleportTask(this.essentialsManager,
                                    essentialsPlayer2, player.getLocation(), 5));
                            player2.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&', "&aEstas siendo teletransportado a &b"
                                            + player.getDisplayName() + "&a!&7 (5 segundos)"));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&aAceptaste la solicitud de teletransporte de &b" + player2.getDisplayName()
                                            + "&a!"));
                        } else {
                            sender.sendMessage(
                                    ChatColor.translateAlternateColorCodes('&', "&cEl jugador no esta en linea!"));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&cNo puedes teletransportarte a ti mismo!"));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEl jugador no esta en linea!"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&cNo tienes ninguna solicitud de teletransporte o la solicitud a expirado!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
