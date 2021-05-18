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
import dev._2lstudios.gameessentials.listeners.InventoryClickListener;
import dev._2lstudios.gameessentials.listeners.EntityDeathListener;
import dev._2lstudios.gameessentials.listeners.EntityDamageListener;
import dev._2lstudios.gameessentials.listeners.EntityDamageByEntityListener;
import org.bukkit.event.Listener;
import dev._2lstudios.gameessentials.listeners.BlockBreakListener;
import dev._2lstudios.gameessentials.tasks.SecondTask;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.plugin.Plugin;

public class ListenerInitializer {
    public ListenerInitializer(final Plugin plugin, final EssentialsManager essentialsManager,
            final SecondTask secondTask) {
        final Server server = plugin.getServer();
        final PluginManager pluginManager = server.getPluginManager();
        pluginManager.registerEvents((Listener) new BlockBreakListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new EntityDamageByEntityListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new EntityDamageListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new EntityDeathListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new InventoryClickListener(essentialsManager.getKitManager()), plugin);
        pluginManager.registerEvents((Listener) new PlayerDeathListener(plugin, essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PlayerDropItemListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PlayerJoinListener(server, essentialsManager, secondTask), plugin);
        pluginManager.registerEvents((Listener) new PlayerMoveListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PlayerQuitListener(server, essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PlayerRespawnListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PlayerTeleportListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new PrepareItemCraftListener(essentialsManager), plugin);
        pluginManager.registerEvents((Listener) new ProjectileHitListener(essentialsManager), plugin);
    }
}
