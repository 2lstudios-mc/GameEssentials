// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.event.player.PlayerJoinEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.tasks.SecondTask;
import dev._2lstudios.gameessentials.managers.VariableManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.Server;
import org.bukkit.event.Listener;

public class PlayerJoinListener implements Listener {
    private final Server server;
    private final PlayerManager playerManager;
    private final VariableManager variableManager;
    private final SecondTask secondTask;

    public PlayerJoinListener(final Server server, final EssentialsManager essentialsManager,
            final SecondTask secondTask) {
        this.server = server;
        this.playerManager = essentialsManager.getPlayerManager();
        this.variableManager = essentialsManager.getVariableManager();
        this.secondTask = secondTask;
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String motd = this.variableManager.getMotd();
        this.playerManager.addPlayer(player);
        if (this.variableManager.getSidebarManager().isEnabled() || this.variableManager.isNametagEnabled()) {
            player.setScoreboard(this.server.getScoreboardManager().getNewScoreboard());
        }
        for (final EssentialsPlayer essentialsPlayer1 : this.playerManager.getPlayers()) {
            if (essentialsPlayer1.isVanished()) {
                player.hidePlayer(essentialsPlayer1.getPlayer());
            }
        }
        this.secondTask.update(player, 0);
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
