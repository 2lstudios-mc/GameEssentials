// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import org.bukkit.command.CommandExecutor;

public class AnvilCMD implements CommandExecutor {
    private final PlayerManager playerManager;

    public AnvilCMD(final EssentialsManager essentialsManager) {
        this.playerManager = essentialsManager.getPlayerManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("essentials.anvil")) {
                final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(((Player) sender).getUniqueId());
                essentialsPlayer.openAnvil();
            } else {
                sender.sendMessage(ChatColor.RED + "Permisos insuficientes.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can't be used from the console.");
        }
        return true;
    }
}
