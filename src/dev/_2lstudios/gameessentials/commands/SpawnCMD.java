package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class SpawnCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;
    private final VariableManager variableManager;
    private final PlayerManager playerManager;

    public SpawnCMD(final EssentialsManager essentialsManager) {
        this.essentialsManager = essentialsManager;
        this.variableManager = essentialsManager.getVariableManager();
        this.playerManager = essentialsManager.getPlayerManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.spawn") && sender instanceof Player) {
            final Player player = (Player) sender;
            final Location spawnLocation = this.variableManager.getSpawn();
            if (spawnLocation != null) {
                final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
                if (this.hasPlayerNear(player)) {
                    essentialsPlayer.setTeleportTask(
                            new TeleportTask(this.essentialsManager, essentialsPlayer, spawnLocation, 5));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aEstas siendo teletransportado al &bSpawn&a! &7(5 segundos)"));
                } else {
                    essentialsPlayer.setTeleportTask(
                            new TeleportTask(this.essentialsManager, essentialsPlayer, spawnLocation, 1));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aEstas siendo teletransportado al &bSpawn&a! &7(1 segundo)"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEl spawn no esta establecido!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }

    private boolean hasPlayerNear(final Player player) {
        final Location location = player.getLocation();
        for (final Player player2 : location.getWorld().getPlayers()) {
            if (player2 != player) {
                final Location location2 = player2.getLocation();
                if (location.distance(location2) < 75.0) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
