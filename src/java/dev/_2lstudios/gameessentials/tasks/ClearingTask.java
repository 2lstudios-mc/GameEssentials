// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.tasks;

import org.bukkit.block.Block;
import java.util.Collection;
import org.bukkit.Server;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.plugin.Plugin;

public class ClearingTask {
    public ClearingTask(final Plugin plugin, final EssentialsManager essentialsManager) {
        final Server server = plugin.getServer();
        final Collection<Block> blocks = essentialsManager.getVariableManager().getBlocks();
        server.getScheduler().runTaskTimer(plugin, blocks::clear, 6000L, 6000L);
    }
}
