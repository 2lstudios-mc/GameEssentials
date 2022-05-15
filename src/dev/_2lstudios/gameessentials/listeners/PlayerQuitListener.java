package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import java.util.UUID;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.event.player.PlayerQuitEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.entity.Player;
import java.util.Collection;
import dev._2lstudios.gameessentials.managers.VariableManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.event.Listener;

public class PlayerQuitListener implements Listener {
    private final PlayerManager playerManager;
    private final VariableManager variableManager;
    private final Collection<Player> autofeedPlayers;

    public PlayerQuitListener(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
        this.variableManager = essentialsManager.getVariableManager();
        this.autofeedPlayers = essentialsManager.getAutoFeedPlayers();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Collection<EssentialsPlayer> changed = this.playerManager.getChanged();
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(uuid);
        final String quitMessage = event.getQuitMessage();
        
        this.autofeedPlayers.remove(player);

        if (quitMessage != null && !quitMessage.equals("") && this.variableManager.isLeavemessageEnabled()) {
            event.setQuitMessage(
                    this.variableManager.getLeavemessageMessage().replace("%player%", player.getDisplayName()));
        }
        if (changed.contains(essentialsPlayer)) {
            essentialsPlayer.save(false);
            changed.remove(essentialsPlayer);
        }
        this.playerManager.removePlayer(uuid);
    }
}
