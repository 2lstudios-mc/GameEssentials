package dev._2lstudios.gameessentials.commands;

import org.bukkit.Location;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class DelHomeCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;

    public DelHomeCMD(final EssentialsManager essentialsManager) {
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
        if (sender.hasPermission("essentials.delhome")) {
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
                final Location home = essentialsPlayer.delHome(homeNumber);
                if (home != null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&aEliminaste la home &b" + homeNumber + "&a correctamente!"));
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
}
