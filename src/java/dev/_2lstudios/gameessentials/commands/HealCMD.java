// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.potion.PotionEffect;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandExecutor;

public class HealCMD implements CommandExecutor {
    private final Plugin plugin;

    public HealCMD(final Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (args.length < 1 && sender.hasPermission("essentials.heal")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                this.heal(player);
                player.sendMessage(ChatColor.GREEN + "Tu barra de vida fue rellenada!");
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else if (args.length > 0 && sender.hasPermission("essentials.heal.others")) {
            final Player player = this.plugin.getServer().getPlayer(args[0]);
            if (player != null) {
                this.heal(player);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&aRellenaste la barra de vida de " + player.getDisplayName() + "&a!"));
                player.sendMessage(ChatColor.GREEN + "Tu barra de vida fue rellenada!");
            } else {
                sender.sendMessage(ChatColor.RED + "El jugador no esta en linea!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    private void heal(final Player player) {
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
            player.removePotionEffect(potionEffect.getType());
        }
    }
}
