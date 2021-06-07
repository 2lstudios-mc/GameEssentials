package dev._2lstudios.gameessentials.tasks;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.Location;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;

public class TeleportTask {
    private final EssentialsPlayer essentialsPlayer;
    private final Location location;
    private int time;

    public TeleportTask(final EssentialsManager essentialsManager, final EssentialsPlayer essentialsPlayer,
            final Location location, final int time) {
        this.essentialsPlayer = essentialsPlayer;
        this.location = location;
        this.time = time;
    }

    public boolean tick() {
        --this.time;
        if (this.essentialsPlayer.getTeleportTask() != this) {
            return true;
        }
        if (this.time <= 0) {
            final Player player = this.essentialsPlayer.getPlayer();
            if (player != null && player.isOnline() && !player.isDead()) {
                player.teleport(this.location);
                this.essentialsPlayer.setTeleportTask(null);
                player.sendMessage(ChatColor.GREEN + "Fuiste teletransportado correctamente!");
            }
            return true;
        }
        return false;
    }
}
