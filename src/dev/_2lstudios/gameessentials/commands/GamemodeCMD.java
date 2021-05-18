package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class GamemodeCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player && args.length == 1) {
            final Player player = (Player) sender;
            final String arg0 = args[0];
            if (arg0.equals("0")) {
                this.changeGamemode(sender, player, GameMode.SURVIVAL);
            } else if (arg0.equals("1")) {
                this.changeGamemode(sender, player, GameMode.CREATIVE);
            } else if (arg0.equals("2")) {
                this.changeGamemode(sender, player, GameMode.ADVENTURE);
            } else if (arg0.equals("3")) {
                this.changeGamemode(sender, player, GameMode.SPECTATOR);
            } else {
                GameMode[] values;
                for (int length = (values = GameMode.values()).length, i = 0; i < length; ++i) {
                    final GameMode gameMode = values[i];
                    if (gameMode.name().toLowerCase().startsWith(arg0.toLowerCase())) {
                        this.changeGamemode(sender, player, gameMode);
                        break;
                    }
                }
            }
        } else if (args.length > 1) {
            final String arg2 = args[0];
            final Player player2 = Bukkit.getPlayer(args[1]);
            if (player2 != null) {
                if (arg2.equals("0")) {
                    this.changeGamemode(sender, player2, GameMode.SURVIVAL);
                } else if (arg2.equals("1")) {
                    this.changeGamemode(sender, player2, GameMode.CREATIVE);
                } else if (arg2.equals("2")) {
                    this.changeGamemode(sender, player2, GameMode.ADVENTURE);
                } else if (arg2.equals("3")) {
                    this.changeGamemode(sender, player2, GameMode.SPECTATOR);
                } else {
                    GameMode[] values2;
                    for (int length2 = (values2 = GameMode.values()).length, j = 0; j < length2; ++j) {
                        final GameMode gameMode = values2[j];
                        if (gameMode.name().toLowerCase().startsWith(arg2.toLowerCase())) {
                            this.changeGamemode(sender, player2, gameMode);
                            break;
                        }
                    }
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "El jugador no esta en linea!"));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/gamemode <survival/creative/adventure/spectator> [jugador]");
        }
        return true;
    }

    private void changeGamemode(final CommandSender sender, final Player player, final GameMode gameMode) {
        if (sender.hasPermission("essentials.gamemode." + gameMode.name().toLowerCase())) {
            if (sender == player) {
                player.setGameMode(gameMode);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aCambiaste tu modo de juego a &b" + gameMode.name() + "&a!"));
            } else if (sender.hasPermission("essentials.gamemode.others")) {
                player.setGameMode(gameMode);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aCambiaste su modo de juego a &b" + gameMode.name() + "&a!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aTu modo de juego cambio a &b" + gameMode.name() + "&a!"));
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
    }
}
