package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.Server;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {
    private final PlayerManager playerManager;
    private final VariableManager variableManager;

    public PlayerJoinListener(final Server server, final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String motd = this.variableManager.getMotd();
        this.playerManager.addPlayer(player);

        for (final EssentialsPlayer essentialsPlayer1 : this.playerManager.getPlayers()) {
            if (essentialsPlayer1.isVanished()) {
                player.hidePlayer(essentialsPlayer1.getPlayer());
            }
        }

        if (!player.hasPlayedBefore()) {
            final Location spawn = this.variableManager.getSpawn();
            if (spawn != null) {
                player.teleport(spawn);
            }
        }

        if (motd != null && !motd.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
        }

        if (this.variableManager.isJoinTitleEnabled()) {
            player.resetTitle();
            player.sendTitle(this.variableManager.getJointitleTitle(), this.variableManager.getJointitleSubtitle());
        }

        if (this.variableManager.isJoinmessageEnabled()) {
            event.setJoinMessage(
                    this.variableManager.getJoinmessageMessage().replace("%player%", player.getDisplayName()));
        }
    }
}
