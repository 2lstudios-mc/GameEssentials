// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class PrepareItemCraftListener implements Listener {
    private final VariableManager variableManager;

    public PrepareItemCraftListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPrepareItemCraft(final PrepareItemCraftEvent event) {
        if (this.variableManager.isDisablecraftingEnabled()) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }
}
