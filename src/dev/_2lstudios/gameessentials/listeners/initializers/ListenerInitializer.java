package dev._2lstudios.gameessentials.listeners.initializers;

import org.bukkit.plugin.PluginManager;
import org.bukkit.Server;
import dev._2lstudios.gameessentials.listeners.ProjectileHitListener;
import dev._2lstudios.gameessentials.listeners.PrepareItemCraftListener;
import dev._2lstudios.gameessentials.listeners.PlayerTeleportListener;
import dev._2lstudios.gameessentials.listeners.PlayerRespawnListener;
import dev._2lstudios.gameessentials.listeners.PlayerQuitListener;
import dev._2lstudios.gameessentials.listeners.PlayerMoveListener;
import dev._2lstudios.gameessentials.listeners.PlayerJoinListener;
import dev._2lstudios.gameessentials.listeners.PlayerDropItemListener;
import dev._2lstudios.gameessentials.listeners.PlayerDeathListener;
import dev._2lstudios.gameessentials.listeners.EntityDeathListener;
import dev._2lstudios.gameessentials.listeners.EntityDamageListener;
import dev._2lstudios.gameessentials.listeners.EntityDamageByEntityListener;
import dev._2lstudios.gameessentials.listeners.BlockBreakListener;
import dev._2lstudios.gameessentials.managers.EssentialsManager;

import org.bukkit.plugin.Plugin;

public class ListenerInitializer {
    public ListenerInitializer(final Plugin plugin, final EssentialsManager essentialsManager) {
        final Server server = plugin.getServer();
        final PluginManager pluginManager = server.getPluginManager();
        pluginManager.registerEvents(new BlockBreakListener(essentialsManager), plugin);
        pluginManager.registerEvents(new EntityDamageByEntityListener(essentialsManager), plugin);
        pluginManager.registerEvents(new EntityDamageListener(essentialsManager), plugin);
        pluginManager.registerEvents(new EntityDeathListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerDeathListener(plugin, essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerDropItemListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(server, essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerQuitListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerRespawnListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PlayerTeleportListener(essentialsManager), plugin);
        pluginManager.registerEvents(new PrepareItemCraftListener(essentialsManager), plugin);
        pluginManager.registerEvents(new ProjectileHitListener(essentialsManager), plugin);
    }
}
