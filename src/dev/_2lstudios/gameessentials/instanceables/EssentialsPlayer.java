package dev._2lstudios.gameessentials.instanceables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import dev._2lstudios.gameessentials.managers.PlayerManager;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;

public class EssentialsPlayer {
    private final Plugin plugin;
    private final PlayerManager playerManager;
    private final ConfigurationUtil configurationUtil;
    private final Map<String, Long> cooldowns;
    private final Player player;
    private final List<Location> homes;
    private TeleportTask teleportTask;
    private World lastScoreboardWorld;
    private Inventory anvil;
    private String teleportRequest;
    private long lastTeleportRequestTime;
    private long lastHelpop;
    private boolean vanished;
    private boolean teleportRequests;

    public EssentialsPlayer(final Plugin plugin, final PlayerManager playerManager,
            final ConfigurationUtil configurationUtil, final Player player) {
        this.cooldowns = new HashMap<String, Long>();
        this.homes = new ArrayList<Location>();
        this.teleportTask = null;
        this.lastScoreboardWorld = null;
        this.anvil = null;
        this.teleportRequest = null;
        this.lastTeleportRequestTime = 0L;
        this.lastHelpop = 0L;
        this.vanished = false;
        this.teleportRequests = true;
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.configurationUtil = configurationUtil;
        this.player = player;
        final YamlConfiguration yamlConfiguration = configurationUtil
                .getConfiguration("%datafolder%/players/" + player.getName() + ".yml");
        final String homeWorld = yamlConfiguration.getString("home.world");
        final Vector homeVector = yamlConfiguration.getVector("home.vector");
        final ConfigurationSection cooldownSection = yamlConfiguration.getConfigurationSection("cooldowns");
        if (cooldownSection != null) {
            for (final String name : cooldownSection.getKeys(false)) {
                final long cooldown = yamlConfiguration.getLong("cooldowns." + name);
                this.cooldowns.put(name, cooldown);
            }
        }
        if (homeWorld != null && homeVector != null) {
            this.addHome(new Location(plugin.getServer().getWorld(homeWorld), homeVector.getX(), homeVector.getY(),
                    homeVector.getZ()));
        } else if (yamlConfiguration.contains("homes")) {
            final List<?> playerHomes = yamlConfiguration.getList("homes");
            this.homes.addAll((List<Location>) playerHomes);
        }
    }

    public void openAnvil() {
        if (this.anvil == null) {
            this.anvil = this.plugin.getServer().createInventory((InventoryHolder) this.player, InventoryType.ANVIL);
        }
        this.player.openInventory(this.anvil);
    }

    public void save(final boolean sync) {
        final YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.set("homes", (Object) this.homes);
        for (final Map.Entry<String, Long> entry : this.cooldowns.entrySet()) {
            final String name = entry.getKey();
            final long cooldown = entry.getValue();
            if (this.hasKitCooldown(name, cooldown)) {
                yamlConfiguration.set("cooldowns." + name, (Object) cooldown);
            }
        }
        if (!sync) {
            this.configurationUtil.saveConfiguration(yamlConfiguration,
                    "%datafolder%/players/" + this.player.getName() + ".yml");
        } else {
            this.configurationUtil.saveConfigurationSync(yamlConfiguration,
                    "%datafolder%/players/" + this.player.getName() + ".yml");
        }
    }

    public void addHome(final Location location) {
        this.homes.add(location);
        this.playerManager.addChanged(this);
    }

    public Location delHome(final int number) {
        try {
            final Location home = this.homes.get(number);
            this.homes.remove(number);
            return home;
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public Collection<Location> getHomes() {
        return this.homes;
    }

    public Location getHome(final int number) {
        try {
            return this.homes.get(number);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public boolean isVanished() {
        return this.vanished;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setTeleportTask(final TeleportTask teleportTask) {
        if (teleportTask != null) {
            this.playerManager.addTeleportTask(teleportTask);
        }
        this.teleportTask = teleportTask;
    }

    public TeleportTask getTeleportTask() {
        return this.teleportTask;
    }

    public boolean vanish() {
        final Server server = this.plugin.getServer();
        final Collection<? extends Player> onlinePlayers = (Collection<? extends Player>) server.getOnlinePlayers();
        if (!this.vanished) {
            for (final Player player : onlinePlayers) {
                if (player != null && player != this.player) {
                    player.hidePlayer(this.player);
                }
            }
        } else {
            for (final Player player : onlinePlayers) {
                if (player != null && player != this.player) {
                    player.showPlayer(this.player);
                }
            }
        }
        return this.vanished = !this.vanished;
    }

    public World getLastScoreboardWorld() {
        return this.lastScoreboardWorld;
    }

    public void setLastScoreboardWorld(final World world) {
        this.lastScoreboardWorld = world;
    }

    public void clearInventory() {
        final PlayerInventory playerInventory = this.player.getInventory();
        playerInventory.clear();
        playerInventory.setHelmet((ItemStack) null);
        playerInventory.setChestplate((ItemStack) null);
        playerInventory.setLeggings((ItemStack) null);
        playerInventory.setBoots((ItemStack) null);
    }

    public void setTeleportRequest(final String teleportRequest) {
        this.teleportRequest = teleportRequest;
        this.lastTeleportRequestTime = System.currentTimeMillis();
    }

    public String getTeleportRequest() {
        return this.teleportRequest;
    }

    public boolean isTeleportRequests() {
        return this.teleportRequests;
    }

    public void setTeleportRequests(final boolean teleportRequests) {
        this.teleportRequests = teleportRequests;
    }

    public boolean hasKitCooldown(final String name, final long cooldown) {
        return System.currentTimeMillis() - this.cooldowns.getOrDefault(name, 0L) < cooldown;
    }

    public void setKitCooldown(final String name) {
        this.cooldowns.put(name, System.currentTimeMillis());
        this.playerManager.addChanged(this);
    }

    public long getKitCooldown(final String name) {
        return this.cooldowns.getOrDefault(name, 0L);
    }

    public long getLastTeleportRequestTime() {
        return this.lastTeleportRequestTime;
    }

    public long getLastHelpop() {
        return this.lastHelpop;
    }

    public void setLastHelpop(final long lastHelpop) {
        this.lastHelpop = lastHelpop;
    }
}
