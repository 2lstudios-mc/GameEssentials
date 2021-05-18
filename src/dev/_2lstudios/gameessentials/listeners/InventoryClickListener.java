package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import dev._2lstudios.gameessentials.managers.KitManager;
import org.bukkit.event.Listener;

public class InventoryClickListener implements Listener {
    private KitManager kitManager;

    public InventoryClickListener(final KitManager kitManager) {
        this.kitManager = kitManager;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (this.kitManager.isPreviewInventory(event.getView().getTopInventory())) {
            event.setCancelled(true);
        }
    }
}
