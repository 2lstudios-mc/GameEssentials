package dev._2lstudios.gameessentials.managers;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.PlayerInventory;
import java.util.Iterator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import dev._2lstudios.gameessentials.instanceables.EssentialsKit;
import java.util.Map;
import org.bukkit.inventory.Inventory;
import java.util.Collection;
import dev._2lstudios.gameessentials.utils.ConfigurationUtil;
import org.bukkit.plugin.Plugin;

public class KitManager {
    private final Plugin plugin;
    private final ConfigurationUtil configurationUtil;
    private final Collection<Inventory> previewInventories;
    private final Map<String, EssentialsKit> essentialsKitMap;

    KitManager(final Plugin plugin, final ConfigurationUtil configurationUtil) {
        this.previewInventories = new HashSet<Inventory>();
        this.essentialsKitMap = new HashMap<String, EssentialsKit>();
        this.plugin = plugin;
        (this.configurationUtil = configurationUtil).createConfiguration("%datafolder%/kits.yml");
        this.reload();
    }

    void reload() {
        final File kitFolder = new File(this.plugin.getDataFolder() + "/kits/");
        final String[] kitFiles = kitFolder.list();
        kitFolder.mkdirs();
        this.essentialsKitMap.clear();
        if (kitFiles != null) {
            String[] array;
            for (int length = (array = kitFiles).length, i = 0; i < length; ++i) {
                final String kitFileName = array[i];
                final Configuration configuration = (Configuration) this.configurationUtil
                        .getConfiguration("%datafolder%/kits/" + kitFileName);
                final String name = kitFileName.replace(".yml", "");
                final int cooldown = configuration.getInt("cooldown");
                ItemStack helmet = null;
                ItemStack chestplate = null;
                ItemStack leggings = null;
                ItemStack boots = null;
                final ItemStack[] contents = new ItemStack[36];
                final ConfigurationSection itemsSection = configuration.getConfigurationSection("items");
                if (itemsSection != null) {
                    for (final String slot : itemsSection.getKeys(false)) {
                        final ItemStack itemStack = configuration.getItemStack("items." + slot);
                        if (itemStack != null) {
                            if (slot.equals("helmet")) {
                                helmet = itemStack;
                            }
                            if (slot.equals("chestplate")) {
                                chestplate = itemStack;
                            }
                            if (slot.equals("leggings")) {
                                leggings = itemStack;
                            }
                            if (slot.equals("boots")) {
                                boots = itemStack;
                            } else {
                                try {
                                    contents[Integer.parseInt(slot)] = itemStack;
                                } catch (NumberFormatException ex) {
                                }
                            }
                        }
                    }
                    this.essentialsKitMap.put(name,
                            new EssentialsKit(cooldown, name, helmet, chestplate, leggings, boots, contents));
                }
            }
        }
    }

    public EssentialsKit getKit(final String name) {
        return this.essentialsKitMap.getOrDefault(name, null);
    }

    public void createKit(final String name, final int cooldown, final PlayerInventory playerInventory) {
        final YamlConfiguration yamlConfiguration = this.configurationUtil
                .getConfiguration("%datafolder%/kits/" + name + ".yml");
        final ItemStack helmet = playerInventory.getHelmet();
        final ItemStack chestplate = playerInventory.getChestplate();
        final ItemStack leggings = playerInventory.getLeggings();
        final ItemStack boots = playerInventory.getBoots();
        final ItemStack[] contents = playerInventory.getContents();
        this.essentialsKitMap.put(name,
                new EssentialsKit(cooldown, name, helmet, chestplate, leggings, boots, contents));
        yamlConfiguration.set("cooldown", (Object) cooldown);
        yamlConfiguration.set("items.helmet", (Object) helmet);
        yamlConfiguration.set("items.chestplate", (Object) chestplate);
        yamlConfiguration.set("items.leggings", (Object) leggings);
        yamlConfiguration.set("items.boots", (Object) boots);
        for (int i = 0; i < contents.length; ++i) {
            yamlConfiguration.set("items." + i, (Object) contents[i]);
        }
        this.configurationUtil.saveConfiguration(yamlConfiguration, "%datafolder%/kits/" + name + ".yml");
    }

    public void deleteKit(final String name) {
        this.configurationUtil.deleteConfiguration("%datafolder%/kits/" + name + ".yml");
        this.essentialsKitMap.remove(name);
    }

    public Iterable<EssentialsKit> getKits() {
        return this.essentialsKitMap.values();
    }

    public void addPreviewInventory(final Inventory previewInventory) {
        this.previewInventories.add(previewInventory);
    }

    public boolean isPreviewInventory(final Inventory previewInventory) {
        return this.previewInventories.contains(previewInventory);
    }

    public Collection<Inventory> getPreviewInventories() {
        return this.previewInventories;
    }
}
