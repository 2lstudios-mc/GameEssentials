package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.event.Listener;

public class PlayerTeleportListener implements Listener {
    private final PlayerManager playerManager;

    public PlayerTeleportListener(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
        if (essentialsPlayer != null && essentialsPlayer.getTeleportTask() != null) {
            essentialsPlayer.setTeleportTask(null);
        }
    }
}
