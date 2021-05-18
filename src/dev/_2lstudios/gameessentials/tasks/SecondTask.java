package dev._2lstudios.gameessentials.tasks;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import dev._2lstudios.gameessentials.hooks.TeamsHook;
import dev._2lstudios.gameessentials.managers.EssentialsManager;

public class SecondTask {
    private final Collection<Player> autofeedPlayers;

    public SecondTask(final Plugin plugin, final EssentialsManager essentialsManager,
            final Collection<TeleportTask> teleportTasks, final TeamsHook teamsHook) {
        final Server server = plugin.getServer();
        final BukkitScheduler scheduler = server.getScheduler();
        this.autofeedPlayers = essentialsManager.getAutoFeedPlayers();

        scheduler.runTaskTimer(plugin, () -> {
            final Iterator<TeleportTask> teleportTaskIterator = teleportTasks.iterator();
            TeleportTask teleportTask;

            while (teleportTaskIterator.hasNext()) {
                teleportTask = teleportTaskIterator.next();
                if (teleportTask == null || teleportTask.tick()) {
                    teleportTaskIterator.remove();
                }
            }
            return;
        }, 20L, 20L);
    }

    public void update(final Player player, final int skipTicks) {
        if (player != null && player.isOnline() && !player.isDead()) {
            if (this.autofeedPlayers.contains(player)) {
                player.setFoodLevel(20);
                player.setSaturation(4.0f);
            }
        }
    }
}
