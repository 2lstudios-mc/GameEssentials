package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import org.bukkit.event.player.PlayerMoveEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.event.Listener;

public class PlayerMoveListener implements Listener {
    private final PlayerManager playerManager;

    public PlayerMoveListener(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
        final Location from = event.getFrom();
        final Location to = event.getTo();
        if (essentialsPlayer != null && essentialsPlayer.getTeleportTask() != null && from.distance(to) > 0.1) {
            essentialsPlayer.setTeleportTask(null);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cTeletransporte pendiente cancelado por movimiento!"));
        }
    }
}
