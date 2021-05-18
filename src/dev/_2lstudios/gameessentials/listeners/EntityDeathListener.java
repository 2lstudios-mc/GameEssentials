package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class EntityDeathListener implements Listener {
    private final VariableManager variableManager;

    public EntityDeathListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (this.variableManager.isCleardropEnabled()) {
            event.getDrops().clear();
        }
    }
}
