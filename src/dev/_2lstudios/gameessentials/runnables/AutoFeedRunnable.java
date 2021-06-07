package dev._2lstudios.gameessentials.runnables;

import java.util.Collection;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import dev._2lstudios.gameessentials.managers.EssentialsManager;

public class AutoFeedRunnable implements Runnable {
    private final Server server;
    private final Collection<Player> autofeedPlayers;

    public AutoFeedRunnable(final Server server, final EssentialsManager essentialsManager) {
        this.server = server;
        this.autofeedPlayers = essentialsManager.getAutoFeedPlayers();
    }

    public void run() {
        for (final Player player : server.getOnlinePlayers()) {
            if (player != null && player.isOnline() && !player.isDead()) {
                if (this.autofeedPlayers.contains(player)) {
                    if (player.getFoodLevel() < 20) {
                        player.setFoodLevel(20);
                    }

                    if (player.getSaturation() < 4.0f) {
                        player.setSaturation(4.0f);
                    }
                }
            }
        }
    }
}
