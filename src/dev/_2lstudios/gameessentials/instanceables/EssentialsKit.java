package dev._2lstudios.gameessentials.instanceables;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EssentialsKit {
    private final int cooldown;
    private final String name;
    private final ItemStack helmet;
    private final ItemStack chestplate;
    private final ItemStack leggings;
    private final ItemStack boots;
    private final ItemStack[] contents;

    public EssentialsKit(final int cooldown, final String name, final ItemStack helmet, final ItemStack chestplate,
            final ItemStack leggings, final ItemStack boots, final ItemStack[] contents) {
        this.cooldown = cooldown;
        this.name = name;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.contents = contents;
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public void give(final Player player) {
        final PlayerInventory playerInventory = player.getInventory();
        if (this.helmet != null) {
            if (playerInventory.getHelmet() == null) {
                playerInventory.setHelmet(this.helmet.clone());
            } else {
                playerInventory.addItem(new ItemStack[] { this.helmet.clone() });
            }
        }
        if (this.chestplate != null) {
            if (playerInventory.getChestplate() == null) {
                playerInventory.setChestplate(this.chestplate.clone());
            } else {
                playerInventory.addItem(new ItemStack[] { this.chestplate.clone() });
            }
        }
        if (this.leggings != null) {
            if (playerInventory.getLeggings() == null) {
                playerInventory.setLeggings(this.leggings.clone());
            } else {
                playerInventory.addItem(new ItemStack[] { this.leggings.clone() });
            }
        }
        if (this.boots != null) {
            if (playerInventory.getBoots() == null) {
                playerInventory.setBoots(this.boots.clone());
            } else {
                playerInventory.addItem(new ItemStack[] { this.boots.clone() });
            }
        }
        ItemStack[] contents;
        for (int length = (contents = this.contents).length, i = 0; i < length; ++i) {
            final ItemStack itemStack = contents[i];
            if (itemStack != null) {
                playerInventory.addItem(new ItemStack[] { itemStack.clone() });
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public ItemStack[] getContents() {
        return this.contents.clone();
    }
}
