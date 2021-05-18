package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.event.Listener;

public class EntityDamageByEntityListener implements Listener {
    private final PlayerManager playerManager;

    public EntityDamageByEntityListener(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final Entity damager = event.getDamager();
        if (damager instanceof Player) {
            final Player player = (Player) damager;
            final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
            if (essentialsPlayer != null && essentialsPlayer.isVanished()) {
                event.setCancelled(true);
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "&cNo puedes atacar mientras estas en vanish!"));
            }
        }
    }
}
