// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.event.Listener;

public class EntityDamageListener implements Listener {
    private final PlayerManager playerManager;

    public EntityDamageListener(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(final EntityDamageEvent event) {
        final Entity entity = event.getEntity();
        if (entity instanceof Player) {
            final Player player = (Player) entity;
            final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
            if (essentialsPlayer != null && essentialsPlayer.getTeleportTask() != null) {
                essentialsPlayer.setTeleportTask(null);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&cTeletransporte pendiente cancelado por da\u00f1o!"));
            }
        }
    }
}
