package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Item;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerDropItemEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class PlayerDropItemListener implements Listener {
    private final VariableManager variableManager;

    public PlayerDropItemListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDropItem(final PlayerDropItemEvent event) {
        final Item itemDrop = event.getItemDrop();
        if (this.variableManager.isCleardropEnabled()) {
            itemDrop.remove();
        }
        if (this.variableManager.isNodropweaponEnabled()) {
            final Material material = itemDrop.getItemStack().getType();
            if (material.name().toLowerCase().contains("sword") || material == Material.FISHING_ROD
                    || material == Material.BOW) {
                event.setCancelled(true);
            }
        }
    }
}
