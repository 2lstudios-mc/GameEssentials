package dev._2lstudios.gameessentials.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.util.Vector;
import org.bukkit.configuration.Configuration;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.block.Block;
import java.util.Collection;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;

public class VariableManager {
    private final ConfigurationUtil configurationUtil;
    private final Collection<Block> blocks;
    private final SidebarManager sidebarManager;
    private Collection<String> spawnCommands;
    private Collection<String> killActionsCommands;
    private Collection<String> nametagWhitelist;
    private Location spawn;
    private String motd;
    private String joinTitleTitle;
    private String joinTitleSubtitle;
    private String killMessageBase;
    private String killMessageBy;
    private String killMessageUsing;
    private String joinMessageMessage;
    private String leaveMessageMessage;
    private boolean arrowRemoverEnabled;
    private boolean clearDropEnabled;
    private boolean diamondsMinedEnabled;
    private boolean disableCraftingEnabled;
    private boolean joinTitleEnabled;
    private boolean spawnClear;
    private boolean noDropWeaponEnabled;
    private boolean joinMessageEnabled;
    private boolean killActionsEnabled;
    private boolean leaveMessageEnabled;
    private boolean nametagEnabled;
    private boolean tabEnabled;

    public VariableManager(final ConfigurationUtil configurationUtil) {
        this.blocks = new HashSet<Block>();
        this.configurationUtil = configurationUtil;
        this.sidebarManager = new SidebarManager();
        this.reload();
    }

    void reload() {
        this.configurationUtil.createConfiguration("%datafolder%/config.yml");
        this.configurationUtil.createConfiguration("%datafolder%/motd.yml");
        this.configurationUtil.createConfiguration("%datafolder%/spawn.yml");
        this.configurationUtil.createConfiguration("%datafolder%/kill_actions.yml");
        final Configuration configYml = (Configuration) this.configurationUtil
                .getConfiguration("%datafolder%/config.yml");
        final Configuration motdYml = (Configuration) this.configurationUtil.getConfiguration("%datafolder%/motd.yml");
        final Configuration spawnYml = (Configuration) this.configurationUtil
                .getConfiguration("%datafolder%/spawn.yml");
        final Configuration killActionsYml = (Configuration) this.configurationUtil
                .getConfiguration("%datafolder%/kill_actions.yml");
        final Vector coordinates = spawnYml.getVector("coordinates");
        final String killmessagesection = "killactions.kill_message";
        if (coordinates != null) {
            this.spawn = new Location(Bukkit.getWorld(spawnYml.getString("world")), coordinates.getX(),
                    coordinates.getY(), coordinates.getZ());
        } else {
            this.spawn = null;
        }
        this.spawnCommands = new HashSet<String>(configYml.getStringList("spawn.commands"));
        this.killActionsCommands = new HashSet<String>(killActionsYml.getStringList("killactions.commands"));
        this.nametagWhitelist = new HashSet<String>(configYml.getStringList("nametag.whitelist"));
        this.motd = motdYml.getString("motd");
        this.killMessageBase = ChatColor.translateAlternateColorCodes('&',
                killActionsYml.getString("killactions.kill_message.base"));
        this.killMessageBy = ChatColor.translateAlternateColorCodes('&',
                killActionsYml.getString("killactions.kill_message.by"));
        this.killMessageUsing = ChatColor.translateAlternateColorCodes('&',
                killActionsYml.getString("killactions.kill_message.using"));
        this.joinTitleTitle = ChatColor.translateAlternateColorCodes('&', configYml.getString("jointitle.title"));
        this.joinTitleSubtitle = ChatColor.translateAlternateColorCodes('&', configYml.getString("jointitle.subtitle"));
        this.joinMessageMessage = ChatColor.translateAlternateColorCodes('&',
                configYml.getString("joinmessage.message"));
        this.leaveMessageMessage = ChatColor.translateAlternateColorCodes('&',
                configYml.getString("leavemessage.message"));
        this.arrowRemoverEnabled = configYml.getBoolean("arrowremover.enabled");
        this.clearDropEnabled = configYml.getBoolean("cleardrop.enabled");
        this.diamondsMinedEnabled = configYml.getBoolean("diamondsmined.enabled");
        this.disableCraftingEnabled = configYml.getBoolean("disablecrafting.enabled");
        this.joinTitleEnabled = configYml.getBoolean("jointitle.enabled");
        this.noDropWeaponEnabled = configYml.getBoolean("nodropweapon.enabled");
        this.joinMessageEnabled = configYml.getBoolean("joinmessage.enabled");
        this.killActionsEnabled = killActionsYml.getBoolean("killactions.enabled");
        this.leaveMessageEnabled = configYml.getBoolean("leavemessage.enabled");
        this.nametagEnabled = configYml.getBoolean("nametag.enabled");
        this.tabEnabled = configYml.getBoolean("tab.enabled");
        this.spawnClear = configYml.getBoolean("spawn.clear");
        final boolean sideBarEnabled = configYml.getBoolean("scoreboard.enabled");
        final Map<String, Collection<String>> sidebars = new HashMap<String, Collection<String>>();
        for (final String string : configYml.getConfigurationSection("scoreboard").getKeys(false)) {
            if (!string.equalsIgnoreCase("enabled")) {
                sidebars.put(string, configYml.getStringList("scoreboard." + string));
            }
        }
        this.sidebarManager.reload(sideBarEnabled, sidebars);
    }

    public boolean isArrowRemoverEnabled() {
        return this.arrowRemoverEnabled;
    }

    public boolean isCleardropEnabled() {
        return this.clearDropEnabled;
    }

    public boolean isDiamondsminedEnabled() {
        return this.diamondsMinedEnabled;
    }

    public boolean isDisablecraftingEnabled() {
        return this.disableCraftingEnabled;
    }

    public boolean isJoinTitleEnabled() {
        return this.joinTitleEnabled;
    }

    public String getJointitleTitle() {
        return this.joinTitleTitle;
    }

    public String getJointitleSubtitle() {
        return this.joinTitleSubtitle;
    }

    public boolean isJoinmessageEnabled() {
        return this.joinMessageEnabled;
    }

    public String getJoinmessageMessage() {
        return this.joinMessageMessage;
    }

    public boolean isKillactionsEnabled() {
        return this.killActionsEnabled;
    }

    public String getKillMessage(final Player killer, final Player killed) {
        String base = "";
        String by = "";
        String using = "";
        if (killed != null) {
            base = this.killMessageBase.replace("%killed%", killed.getDisplayName().replace("~", ""));
            if (killer != null) {
                final PlayerInventory killerInventory = killer.getInventory();
                final ItemStack killerItem = killerInventory.getItem(killerInventory.getHeldItemSlot());
                by = this.killMessageBy.replace("%killer%", killer.getDisplayName().replace("~", ""));
                if (!by.isEmpty() && killerItem != null && killerItem.hasItemMeta()) {
                    final ItemMeta killerItemMeta = killerItem.getItemMeta();
                    if (killerItemMeta.hasDisplayName()) {
                        using = this.killMessageUsing.replace("%tool%", killerItemMeta.getDisplayName());
                    }
                }
            }
        }
        return base.replace("%by%", by).replace("%using%", using);
    }

    public Collection<String> getKillactionsCommands() {
        return this.killActionsCommands;
    }

    public boolean isLeavemessageEnabled() {
        return this.leaveMessageEnabled;
    }

    public String getLeavemessageMessage() {
        return this.leaveMessageMessage;
    }

    public boolean isNametagEnabled() {
        return this.nametagEnabled;
    }

    public boolean isNodropweaponEnabled() {
        return this.noDropWeaponEnabled;
    }

    public Collection<Block> getBlocks() {
        return this.blocks;
    }

    public Location getSpawn() {
        return this.spawn;
    }

    public void setSpawn(final Location location) {
        final YamlConfiguration spawnYml = new YamlConfiguration();
        this.spawn = location;
        if (location != null) {
            spawnYml.set("world", (Object) location.getWorld().getName());
            spawnYml.set("coordinates", (Object) location.toVector());
        } else {
            spawnYml.set("world", (Object) null);
            spawnYml.set("coordinates", (Object) null);
        }
        this.configurationUtil.saveConfiguration(spawnYml, "%datafolder%/spawn.yml");
    }

    public boolean isSpawnClear() {
        return this.spawnClear;
    }

    public Collection<String> getSpawnCommands() {
        return this.spawnCommands;
    }

    public String getMotd() {
        return this.motd;
    }

    public boolean isTabEnabled() {
        return this.tabEnabled;
    }

    public Collection<String> getNametagWhitelist() {
        return this.nametagWhitelist;
    }

    public SidebarManager getSidebarManager() {
        return this.sidebarManager;
    }
}
