package dev._2lstudios.gameessentials.managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public class PlaceholderAPIManager {
    private boolean placeholders;

    public PlaceholderAPIManager() {
        this.placeholders = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI").isEnabled();
    }

    public String setPlaceholders(final OfflinePlayer offlinePlayer, final String message) {
        if (this.placeholders) {
            return PlaceholderAPI.setPlaceholders((Player) offlinePlayer, message);
        }
        return message;
    }
}
