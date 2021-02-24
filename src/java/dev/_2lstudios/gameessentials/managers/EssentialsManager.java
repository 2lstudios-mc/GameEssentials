// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.managers;

import java.util.HashSet;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import java.util.Collection;

public class EssentialsManager {
    private final PlayerManager playerManager;
    private final VariableManager variableManager;
    private final KitManager kitManager;
    private final PlaceholderAPIManager placeholderAPIManager;
    private final Collection<Player> autoFeedPlayers;

    public EssentialsManager(final Plugin plugin, final ConfigurationUtil configurationUtil,
            final Collection<TeleportTask> teleportTasks) {
        this.playerManager = new PlayerManager(plugin, configurationUtil, teleportTasks);
        this.variableManager = new VariableManager(configurationUtil);
        this.kitManager = new KitManager(plugin, configurationUtil);
        this.placeholderAPIManager = new PlaceholderAPIManager();
        this.autoFeedPlayers = new HashSet<Player>();
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public KitManager getKitManager() {
        return this.kitManager;
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public PlaceholderAPIManager getPlaceholderAPIManager() {
        return this.placeholderAPIManager;
    }

    public void reload() {
        this.variableManager.reload();
        this.kitManager.reload();
    }

    public Collection<Player> getAutoFeedPlayers() {
        return this.autoFeedPlayers;
    }
}
