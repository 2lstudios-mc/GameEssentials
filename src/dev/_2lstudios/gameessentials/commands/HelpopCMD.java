package dev._2lstudios.gameessentials.commands;

import org.bukkit.command.Command;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.text.DecimalFormat;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;

public class HelpopCMD implements CommandExecutor {
    private final Server server;
    private final PlayerManager playerManager;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
    private static final int COOLDOWN_TIME = 15000;

    public HelpopCMD(final Server server, final PlayerManager playerManager) {
        this.server = server;
        this.playerManager = playerManager;
    }

    private void sendMessage(final CommandSender commandSender, String message) {
        message = ChatColor.translateAlternateColorCodes('&',
                "&c[STAFF] &7" + commandSender.getName() + "&7: &6" + message);
        if (!commandSender.hasPermission("essentials.helpop.receive")) {
            commandSender.sendMessage(message);
        }
        for (final Player player : this.server.getOnlinePlayers()) {
            if (player.hasPermission("essentials.helpop.receive")) {
                player.sendMessage(message);
            }
        }
    }

    private long getAndUpdateCooldown(final CommandSender commandSender) {
        if (!(commandSender instanceof Player)) {
            return COOLDOWN_TIME;
        }

        if (commandSender.hasPermission("essentials.helpop.receive")) {
            return COOLDOWN_TIME;
        }

        final Player player = (Player) commandSender;
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());

        if (essentialsPlayer == null) {
            return COOLDOWN_TIME;
        }

        final long currentTime = System.currentTimeMillis();
        final long cooldown = Math.abs(essentialsPlayer.getLastHelpop() - currentTime);

        if (cooldown >= COOLDOWN_TIME) {
            essentialsPlayer.setLastHelpop(currentTime);
        }

        return cooldown;
    }

    private double toSeconds(final long cooldown) {
        return Double.parseDouble(HelpopCMD.DECIMAL_FORMAT.format(cooldown / 1000.0));
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.helpop")) {
            final long cooldown = this.getAndUpdateCooldown(sender);

            if (cooldown < COOLDOWN_TIME) {
                sender.sendMessage(ChatColor.RED + "Espera " + this.toSeconds(COOLDOWN_TIME - cooldown)
                        + " antes de volver a usar el helpop!");
            } else if (args.length > 0) {
                String message = "";

                for (final String arg : args) {
                    message = message.concat(String.valueOf(arg) + " ");
                }

                if (!message.isEmpty()) {
                    this.sendMessage(sender, message.trim());
                } else {
                    sender.sendMessage(ChatColor.RED + "/" + label + " <mensaje>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "/" + label + " <mensaje>");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
