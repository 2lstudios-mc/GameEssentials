package dev._2lstudios.gameessentials;

import org.bukkit.scoreboard.Scoreboard;
import java.util.UUID;
import org.bukkit.Server;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import java.util.Iterator;
import org.bukkit.entity.Player;
import dev._2lstudios.gameessentials.listeners.initializers.ListenerInitializer;
import dev._2lstudios.gameessentials.tasks.ClearingTask;
import dev._2lstudios.gameessentials.commands.initializers.CommandInitializer;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;
import dev._2lstudios.gameessentials.hooks.TeamsHook;
import dev._2lstudios.gameessentials.utils.VersionUtil;
import java.util.HashSet;
import dev._2lstudios.gameessentials.tasks.SecondTask;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import java.util.Collection;
import org.bukkit.plugin.java.JavaPlugin;

public class GameEssentials extends JavaPlugin {
    private final Collection<TeleportTask> teleportTasks;
    private static EssentialsManager essentialsManager;
    private SecondTask secondTask;

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
        final ConfigurationUtil configurationUtil = new ConfigurationUtil((Plugin) this);
        if (this.getServer().getPluginManager().isPluginEnabled("Teams")) {
            teamsHook.hook();
        }
        GameEssentials.essentialsManager = new EssentialsManager((Plugin) this, configurationUtil, this.teleportTasks);
        new CommandInitializer(this, GameEssentials.essentialsManager);
        new ClearingTask((Plugin) this, GameEssentials.essentialsManager);
        this.secondTask = new SecondTask((Plugin) this, GameEssentials.essentialsManager, this.teleportTasks,
                teamsHook);
        new ListenerInitializer((Plugin) this, GameEssentials.essentialsManager, this.secondTask);
        for (final Player player : this.getServer().getOnlinePlayers()) {
            GameEssentials.essentialsManager.getPlayerManager().addPlayer(player);
            if (GameEssentials.essentialsManager.getVariableManager().getSidebarManager().isEnabled()
                    || GameEssentials.essentialsManager.getVariableManager().isNametagEnabled()) {
                player.setScoreboard(this.getServer().getScoreboardManager().getNewScoreboard());
            }
            this.secondTask.update(player, 0);
        }
    }

    public synchronized void onDisable() {
        final PlayerManager playerManager = GameEssentials.essentialsManager.getPlayerManager();
        final Collection<EssentialsPlayer> changed = playerManager.getChanged();
        final Server server = this.getServer();
        final boolean scoreboardEnabled = GameEssentials.essentialsManager.getVariableManager().getSidebarManager()
                .isEnabled();
        final boolean nametagEnabled = GameEssentials.essentialsManager.getVariableManager().isNametagEnabled();
        for (final Inventory inventory : GameEssentials.essentialsManager.getKitManager().getPreviewInventories()) {
            for (final HumanEntity humanEntity : inventory.getViewers()) {
                humanEntity.closeInventory();
            }
        }

        for (final Player player : server.getOnlinePlayers()) {
            final UUID uuid = player.getUniqueId();
            final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(uuid);
            if (essentialsPlayer != null) {
                final Scoreboard scoreboard = player.getScoreboard();
                if (scoreboardEnabled) {
                    scoreboard.clearSlot(DisplaySlot.SIDEBAR);
                }
                if (nametagEnabled) {
                    scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
                }
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
