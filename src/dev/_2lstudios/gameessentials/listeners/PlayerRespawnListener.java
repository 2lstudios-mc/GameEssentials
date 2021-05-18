package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerRespawnEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class PlayerRespawnListener implements Listener {
    private final VariableManager variableManager;

    public PlayerRespawnListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        final Location spawn = this.variableManager.getSpawn();
        if (spawn != null) {
            if (!event.getPlayer().isOnline()) {
                return;
            }
            event.setRespawnLocation(spawn);
        }
    }
}
