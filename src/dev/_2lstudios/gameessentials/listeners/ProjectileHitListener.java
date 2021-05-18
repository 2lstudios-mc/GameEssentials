package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class ProjectileHitListener implements Listener {
    private final VariableManager variableManager;

    public ProjectileHitListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(final ProjectileHitEvent event) {
        final Entity entity = (Entity) event.getEntity();
        if (this.variableManager.isArrowRemoverEnabled() && entity instanceof Arrow) {
            entity.remove();
        }
    }
}
