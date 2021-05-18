package dev._2lstudios.gameessentials.managers;

import java.util.HashMap;
import org.bukkit.entity.Player;
import java.util.Collection;
import java.util.Map;

public class SidebarManager {
    private boolean enabled;
    private Map<String, Collection<String>> sidebars;
    private Map<Player, Collection<String>> customSidebars;

    public SidebarManager() {
        this.enabled = false;
        this.sidebars = new HashMap<String, Collection<String>>();
        this.customSidebars = new HashMap<Player, Collection<String>>();
    }

    public void reload(final boolean enabled, final Map<String, Collection<String>> scoreboards) {
        this.enabled = enabled;
        this.sidebars = scoreboards;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Collection<String> getSidebars(final Player player, final String scoreboard) {
        if (this.customSidebars.containsKey(player)) {
            return this.customSidebars.get(player);
        }
        return this.sidebars.getOrDefault(scoreboard, this.sidebars.getOrDefault("default", null));
    }

    public void setCustomSidebar(final Player player, final Collection<String> customScoreboard) {
        if (customScoreboard == null) {
            this.customSidebars.remove(player);
        } else {
            this.customSidebars.put(player, customScoreboard);
        }
    }
}
