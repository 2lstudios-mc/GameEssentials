// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.VariableManager;
import org.bukkit.command.CommandExecutor;

public class SetSpawnCMD implements CommandExecutor {
    private final VariableManager variableManager;

    public SetSpawnCMD(final EssentialsManager essentialsManager) {
        this.variableManager = essentialsManager.getVariableManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.setspawn") && sender instanceof Player) {
            final Player player = (Player) sender;
            this.variableManager.setSpawn(player.getLocation());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bSpawn &aestablecido correctamente!"));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPermisos insuficientes!"));
        }
        return true;
    }
}
