package dev._2lstudios.gameessentials.managers;

import java.util.Iterator;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.HashMap;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import java.util.Collection;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import java.util.UUID;
import java.util.Map;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;
import org.bukkit.plugin.Plugin;

public class PlayerManager {
    private final Plugin plugin;
    private final ConfigurationUtil configurationUtil;
    private final Map<UUID, EssentialsPlayer> essentialsPlayers;
    private final Collection<TeleportTask> teleportTasks;
    private final Collection<EssentialsPlayer> changed;

    public PlayerManager(final Plugin plugin, final ConfigurationUtil configurationUtil,
            final Collection<TeleportTask> teleportTasks) {
        this.plugin = plugin;
        this.configurationUtil = configurationUtil;
        this.essentialsPlayers = new HashMap<UUID, EssentialsPlayer>();
        this.teleportTasks = teleportTasks;
        this.changed = new HashSet<EssentialsPlayer>();
        for (final Player player : plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(player);
        }
    }

    public EssentialsPlayer getPlayer(final UUID uuid) {
        return this.essentialsPlayers.getOrDefault(uuid, null);
    }

    public EssentialsPlayer addPlayer(final Player player) {
        final EssentialsPlayer essentialsPlayer = new EssentialsPlayer(this.plugin, this, this.configurationUtil,
                player);
        this.essentialsPlayers.put(player.getUniqueId(), essentialsPlayer);
        return essentialsPlayer;
    }

    public void removePlayer(final UUID uuid) {
        this.essentialsPlayers.remove(uuid);
    }

    public void addChanged(final EssentialsPlayer essentialsPlayer) {
        this.changed.add(essentialsPlayer);
    }

    public Collection<EssentialsPlayer> getChanged() {
        return this.changed;
    }

    public Collection<EssentialsPlayer> getPlayers() {
        return this.essentialsPlayers.values();
    }

    public void addTeleportTask(final TeleportTask teleportTask) {
        this.teleportTasks.add(teleportTask);
    }
}
