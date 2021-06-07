package dev._2lstudios.gameessentials;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.gameessentials.commands.initializers.CommandInitializer;
import dev._2lstudios.gameessentials.hooks.TeamsHook;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.listeners.initializers.ListenerInitializer;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import dev._2lstudios.gameessentials.runnables.AutoFeedRunnable;
import dev._2lstudios.gameessentials.runnables.TeleportTaskRunnable;
import dev._2lstudios.gameessentials.tasks.ClearingTask;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;
import dev._2lstudios.gameessentials.utils.VersionUtil;

public class GameEssentials extends JavaPlugin {
    private final Collection<TeleportTask> teleportTasks;
    private static EssentialsManager essentialsManager;
    private AutoFeedRunnable secondTask;

    static {
        GameEssentials.essentialsManager = null;
    }

    public GameEssentials() {
        this.teleportTasks = new HashSet<TeleportTask>();
        this.secondTask = null;
    }

    public synchronized void onEnable() {
        VersionUtil.init();

        final TeamsHook teamsHook = new TeamsHook();
        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);
        final Server server = getServer();

        if (server.getPluginManager().isPluginEnabled("Teams")) {
            teamsHook.hook();
        }

        GameEssentials.essentialsManager = new EssentialsManager(this, configurationUtil, this.teleportTasks);

        new CommandInitializer(this, GameEssentials.essentialsManager);
        new ClearingTask(this, GameEssentials.essentialsManager);
        new ListenerInitializer(this, GameEssentials.essentialsManager, this.secondTask);

        server.getScheduler().runTaskTimerAsynchronously(this, new AutoFeedRunnable(server, essentialsManager), 20L,
                20L);
        server.getScheduler().runTaskTimerAsynchronously(this, new TeleportTaskRunnable(teleportTasks), 20L, 20L);

        for (final Player player : this.getServer().getOnlinePlayers()) {
            GameEssentials.essentialsManager.getPlayerManager().addPlayer(player);
        }
    }

    public synchronized void onDisable() {
        final PlayerManager playerManager = GameEssentials.essentialsManager.getPlayerManager();
        final Collection<EssentialsPlayer> changed = playerManager.getChanged();
        final Server server = this.getServer();

        for (final Inventory inventory : GameEssentials.essentialsManager.getKitManager().getPreviewInventories()) {
            for (final HumanEntity humanEntity : inventory.getViewers()) {
                humanEntity.closeInventory();
            }
        }

        for (final Player player : server.getOnlinePlayers()) {
            final UUID uuid = player.getUniqueId();
            final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(uuid);
            if (essentialsPlayer != null) {
                if (changed.contains(essentialsPlayer)) {
                    essentialsPlayer.save(true);
                    changed.remove(essentialsPlayer);
                }

                playerManager.removePlayer(uuid);
            }
        }
    }

    public static EssentialsManager getEssentialsManager() {
        return GameEssentials.essentialsManager;
    }
}
