// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.command.CommandSender;
import java.util.Iterator;
import org.bukkit.Server;
import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.Sound;
import dev._2lstudios.gameessentials.utils.VersionUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.PlayerDeathEvent;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;

public class PlayerDeathListener implements Listener {
    private final Plugin plugin;
    private final VariableManager variableManager;

    public PlayerDeathListener(final Plugin plugin, final EssentialsManager essentialsManager) {
        this.plugin = plugin;
        this.variableManager = essentialsManager.getVariableManager();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerDeath(final PlayerDeathEvent event) {
        if (this.variableManager.isCleardropEnabled()) {
            event.getDrops().clear();
        }
        if (this.variableManager.isKillactionsEnabled()) {
            final Player killed = event.getEntity();
            final Player killer = killed.getKiller();
            if (killed == killer || (killed != null && !killed.isOnline()) || (killer != null && !killer.isOnline())) {
                event.setDeathMessage("");
            } else {
                final Collection<String> commands = this.variableManager.getKillactionsCommands();
                event.setDeathMessage(this.variableManager.getKillMessage(killer, killed));
                if (killer != null && commands != null && !commands.isEmpty()) {
                    final Server server = Bukkit.getServer();
                    for (final String command : commands) {
                        if (command.startsWith("%pling%")) {
                            if (VersionUtil.isOneDotNine()) {
                                killer.playSound(killer.getLocation(), Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"),
                                        1.0f, 1.0f);
                            } else {
                                killer.playSound(killer.getLocation(), Sound.valueOf("ORB_PICKUP"), 1.0f, 1.0f);
                            }
                        } else if (command.startsWith("%thunder%")) {
                            if (VersionUtil.isOneDotNine()) {
                                killed.getWorld().playSound(killed.getLocation(),
                                        Sound.valueOf("ENTITY_LIGHTNING_IMPACT"), 16.0f, 0.75f);
                            } else {
                                killed.getWorld().playSound(killed.getLocation(), Sound.valueOf("AMBIENCE_THUNDER"),
                                        16.0f, 0.75f);
                            }
                        } else {
                            CommandSender sender;
                            if (command.startsWith("console: ")) {
                                sender = (CommandSender) server.getConsoleSender();
                            } else if (command.startsWith("killer: ")) {
                                sender = (CommandSender) killer;
                            } else if (command.startsWith("killed: ")) {
                                sender = (CommandSender) killed;
                            } else {
                                sender = null;
                            }
                            server.getScheduler().runTask(this.plugin,
                                    () -> server.dispatchCommand(sender,
                                            command.replace("%killer%", killer.getName())
                                                    .replace("%killed%", killed.getName()).replace("console: ", "")
                                                    .replace("killer: ", "").replace("killed: ", "")));
                        }
                    }
                }
            }
        }
    }
}
