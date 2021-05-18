package dev._2lstudios.gameessentials.commands;

import org.bukkit.Location;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.ChatColor;
import dev._2lstudios.gameessentials.tasks.TeleportTask;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class HomeCMD implements CommandExecutor {
    private final Plugin plugin;
    private final EssentialsManager essentialsManager;

    public HomeCMD(final Plugin plugin, final EssentialsManager essentialsManager) {
        this.plugin = plugin;
        this.essentialsManager = essentialsManager;
    }

    public int parseOrDefault(final String input, final int def) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return def;
        }
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.home")) {
            if (sender instanceof Player) {
                int homeNumber;
                if (args.length > 0) {
                    homeNumber = this.parseOrDefault(args[0], 0);
                } else {
                    homeNumber = 0;
                }
                final PlayerManager playerManager = this.essentialsManager.getPlayerManager();
                final Player player = (Player) sender;
                final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(player.getUniqueId());
                final Location home = essentialsPlayer.getHome(homeNumber);
                if (home != null) {
                    int seconds;
                    if (this.hasPlayerNear(player)) {
                        seconds = 5;
                    } else {
                        seconds = 1;
                    }
                    essentialsPlayer
                            .setTeleportTask(new TeleportTask(this.essentialsManager, essentialsPlayer, home, seconds));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aEstas siendo teletransportado a tu &bHome&a! &7(" + seconds + " segundos)"));
                } else {
                    sender.sendMessage(ChatColor.RED + "No tienes una home establecida! Total de Homes: "
                            + essentialsPlayer.getHomes().size());
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    private boolean hasPlayerNear(final Player player) {
        final Location location = player.getLocation();
        for (final Player player2 : this.plugin.getServer().getOnlinePlayers()) {
            if (player2 != player && player2.getWorld() == player.getWorld()) {
                final Location location2 = player2.getLocation();
                if (location.distance(location2) < 75.0) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
}
