package dev._2lstudios.gameessentials.commands;

import org.bukkit.permissions.PermissionAttachmentInfo;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.command.CommandExecutor;

public class SetHomeCMD implements CommandExecutor {
    private final EssentialsManager essentialsManager;

    public SetHomeCMD(final EssentialsManager essentialsManager) {
        this.essentialsManager = essentialsManager;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.sethome")) {
            if (sender instanceof Player) {
                final PlayerManager playerManager = this.essentialsManager.getPlayerManager();
                final Player player = (Player) sender;
                final EssentialsPlayer essentialsPlayer = playerManager.getPlayer(player.getUniqueId());
                final int homeLimit = this.getHomeLimit(player);
                if (essentialsPlayer.getHomes().size() < homeLimit) {
                    essentialsPlayer.addHome(player.getLocation());
                    sender.sendMessage(ChatColor.GREEN + "Estableciste tu home correctamente!");
                } else {
                    sender.sendMessage(ChatColor.RED + "No puedes tener mas de " + homeLimit
                            + " homes! Usa /delhome para eliminar homes!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    private int getHomeLimit(final Player player) {
        for (final PermissionAttachmentInfo permissionInfo : player.getEffectivePermissions()) {
            final String permission = permissionInfo.getPermission();
            if (permission.startsWith("essentials.sethome.")) {
                try {
                    return Integer.parseInt(permission.split("[.]")[2]);
                } catch (Exception ex) {
                }
            }
        }
        return 1;
    }
}
