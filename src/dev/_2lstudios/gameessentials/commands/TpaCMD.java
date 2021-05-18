package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.command.CommandExecutor;

public class TpaCMD implements CommandExecutor {
    private final PlayerManager playerManager;

    public TpaCMD(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.tpa") && sender instanceof Player) {
            if (args.length > 0) {
                final Player senderPlayer = (Player) sender;
                final Player receiverPlayer = Bukkit.getPlayer(args[0]);
                if (receiverPlayer != null) {
                    if (receiverPlayer != senderPlayer) {
                        final EssentialsPlayer receiverEssentialsPlayer = this.playerManager
                                .getPlayer(receiverPlayer.getUniqueId());
                        if (receiverEssentialsPlayer != null) {
                            if (receiverEssentialsPlayer.isTeleportRequests()) {
                                final String teleportRequest = receiverEssentialsPlayer.getTeleportRequest();
                                if (teleportRequest == null || !teleportRequest.equals(senderPlayer.getName())
                                        || System.currentTimeMillis()
                                                - receiverEssentialsPlayer.getLastTeleportRequestTime() > 6000L) {
                                    receiverEssentialsPlayer.setTeleportRequest(senderPlayer.getName());
                                    receiverPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b"
                                            + senderPlayer.getName() + "&a te envio una solicitud de teletransporte!"));
                                    senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&aEnviaste una solicitud de teletransporte a &b" + receiverPlayer.getName()
                                                    + "&a!"));
                                } else {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cYa enviaste una peticion de teletransporte a ese jugador!"));
                                }
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&cEl jugador tiene los teletransportes desactivados!"));
                            }
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
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/tpa <jugador>"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
