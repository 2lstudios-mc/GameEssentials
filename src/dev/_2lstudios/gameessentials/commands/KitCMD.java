package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.instanceables.EssentialsKit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import dev._2lstudios.gameessentials.managers.KitManager;
import org.bukkit.command.CommandExecutor;

public class KitCMD implements CommandExecutor {
    private final KitManager kitManager;
    private final PlayerManager playerManager;

    public KitCMD(final EssentialsManager essentialsManager) {
        this.kitManager = essentialsManager.getKitManager();
        this.playerManager = essentialsManager.getPlayerManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final int length = args.length;
        if (length > 0) {
            if (length < 2) {
                if (sender instanceof Player) {
                    if (sender.hasPermission("essentials.kit")) {
                        final String kitName = args[0].toUpperCase();
                        final EssentialsKit essentialsKit = this.kitManager.getKit(kitName);
                        if (essentialsKit != null) {
                            if (sender.hasPermission("essentials.kits." + kitName.toLowerCase())) {
                                final Player player = (Player) sender;
                                final EssentialsPlayer essentialsPlayer = this.playerManager
                                        .getPlayer(player.getUniqueId());
                                if (!essentialsPlayer.hasKitCooldown(essentialsKit.getName(),
                                        essentialsKit.getCooldown())) {
                                    essentialsKit.give(player);
                                    if (essentialsKit.getCooldown() != 0) {
                                        essentialsPlayer.setKitCooldown(essentialsKit.getName());
                                    }
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&aRecogiste el kit &b" + kitName + "&a!"));
                                } else {
                                    final int kitCooldown = essentialsKit.getCooldown();
                                    final long lastTakeTime = essentialsPlayer.getKitCooldown(essentialsKit.getName());
                                    final int milliseconds = (int) (lastTakeTime + kitCooldown
                                            - System.currentTimeMillis());
                                    final int seconds = milliseconds / 1000 % 60;
                                    final int minutes = milliseconds / 60000 % 60;
                                    final int hours = milliseconds / 3600000 % 24;
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&cDebes esperar &e" + hours + "H " + minutes + "M " + seconds + "S"
                                                    + "&c para usar este kit!"));
                                }
                            } else {
                                sender.sendMessage(
                                        ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
                            }
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEse kit no existe!"));
                        }
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
                    }
                } else {
                    sender.sendMessage(
                            ChatColor.translateAlternateColorCodes('&', "&cNo puedes recoger kits desde la consola!"));
                }
            } else if (sender.hasPermission("essentials.kits.others")) {
                final String kitName = args[0].toUpperCase();
                final Player player2 = Bukkit.getPlayer(args[1]);
                final EssentialsKit essentialsKit2 = this.kitManager.getKit(kitName);
                if (essentialsKit2 != null) {
                    if (player2 != null) {
                        essentialsKit2.give(player2);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aKit &b" + kitName
                                + "&a entregado al jugador &7" + player2.getDisplayName() + "&a!"));
                    } else {
                        sender.sendMessage(
                                ChatColor.translateAlternateColorCodes('&', "&cEl jugador no esta en linea!"));
                    }
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEse kit no existe!"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
            }
        } else if (sender instanceof Player) {
            final Iterable<EssentialsKit> kits = this.kitManager.getKits();
            final StringBuilder coloredKits = new StringBuilder("&bKits disponibles: &a");
            final EssentialsPlayer essentialsPlayer2 = this.playerManager.getPlayer(((Player) sender).getUniqueId());
            boolean iterated = false;
            for (final EssentialsKit essentialsKit3 : kits) {
                final String essentialsKitName = essentialsKit3.getName();
                if (iterated) {
                    coloredKits.append("&7, ");
                } else {
                    iterated = true;
                }
                if (sender.hasPermission("essentials.kits." + essentialsKitName)
                        && !essentialsPlayer2.hasKitCooldown(essentialsKitName, essentialsKit3.getCooldown())) {
                    coloredKits.append("&a");
                } else {
                    coloredKits.append("&c");
                }
                coloredKits.append(essentialsKitName);
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', coloredKits.toString()));
        } else {
            sender.sendMessage(ChatColor.RED + "/kits <kit> [jugador]");
        }
        return true;
    }
}
