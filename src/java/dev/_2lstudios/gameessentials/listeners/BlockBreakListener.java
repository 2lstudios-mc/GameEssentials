// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.listeners;

import org.bukkit.World;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.utils.Counter;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.event.Listener;

public class BlockBreakListener implements Listener {
    private final VariableManager variableManager;

    public BlockBreakListener(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (this.variableManager.isDiamondsminedEnabled()) {
            final Block block = event.getBlock();
            if (block.getType() == Material.DIAMOND_ORE) {
                final Player player = event.getPlayer();
                final Collection<Block> blocks = this.variableManager.getBlocks();
                final Counter counter = new Counter();
                this.getDiamondBlocks(block.getWorld(), block.getX(), block.getY(), block.getZ(), counter, blocks);
                if (counter.get() > 3) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + player.getDisplayName() + ChatColor.AQUA + " encontro "
                            + counter.get() + " diamantes!");
                }
            }
        }
    }

    private void getDiamondBlocks(final World world, final int x, final int y, final int z, final Counter counter,
            final Collection<Block> blocks) {
        final Block block = world.getBlockAt(x, y, z);
        if (block.getType() == Material.DIAMOND_ORE && !blocks.contains(block)) {
            blocks.add(block);
            counter.add(1);
            if (counter.get() < 25) {
                for (int x2 = x - 1; x2 < x + 2; ++x2) {
                    for (int y2 = y - 1; y2 < y + 2; ++y2) {
                        for (int z2 = z - 1; z2 < z + 2; ++z2) {
                            this.getDiamondBlocks(world, x2, y2, z2, counter, blocks);
                        }
                    }
                }
            }
        }
    }
}
