package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.Inventory;
import dev._2lstudios.gameessentials.instanceables.EssentialsKit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import dev._2lstudios.gameessentials.managers.KitManager;
import org.bukkit.Server;
import org.bukkit.command.CommandExecutor;

public class KitPreviewCMD implements CommandExecutor {
    private final Server server;
    private final KitManager kitManager;

    public KitPreviewCMD(final Server server, final EssentialsManager essentialsManager) {
        this.server = server;
        this.kitManager = essentialsManager.getKitManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final int length = args.length;
        if (length > 0) {
            if (sender instanceof Player) {
                final String kitName = args[0].toUpperCase();
                final EssentialsKit essentialsKit = this.kitManager.getKit(kitName);
                if (essentialsKit != null) {
                    final Player player = (Player) sender;
                    final Inventory previewInventory = this.server.createInventory((InventoryHolder) player, 54,
                            "Preview de " + args[0]);
                    this.kitManager.addPreviewInventory(previewInventory);
                    previewInventory.setContents(essentialsKit.getContents());
                    player.openInventory(previewInventory);
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cEse kit no existe!"));
                }
            } else {
                sender.sendMessage(
                        ChatColor.translateAlternateColorCodes('&', "&cNo puedes revisar kits desde la consola!"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/kitpreview <kit>!"));
        }
        return true;
    }
}
