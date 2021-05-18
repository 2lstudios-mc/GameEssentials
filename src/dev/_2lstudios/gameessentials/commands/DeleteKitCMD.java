package dev._2lstudios.gameessentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.KitManager;
import org.bukkit.command.CommandExecutor;

public class DeleteKitCMD implements CommandExecutor {
    private final KitManager kitManager;

    public DeleteKitCMD(final EssentialsManager essentialsManager) {
        this.kitManager = essentialsManager.getKitManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length > 0 && sender.hasPermission("essentials.deletekit")) {
            final String name = args[0].toUpperCase();
            this.kitManager.deleteKit(name);
            sender.sendMessage(ChatColor.GREEN + "Creaste el kit correctamente!");
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }
}
