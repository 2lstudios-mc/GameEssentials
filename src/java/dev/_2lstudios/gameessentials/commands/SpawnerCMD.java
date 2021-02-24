// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.commands;

import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.ChatColor;
import org.bukkit.block.CreatureSpawner;
import java.util.Set;
import java.util.Collections;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class SpawnerCMD implements CommandExecutor {
    public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
        if (arg0.hasPermission("essentials.spawner")) {
            if (arg0 instanceof Player) {
                final Player player = (Player) arg0;
                if (arg3.length > 0) {
                    final String entityTypeName = arg3[0].toUpperCase();
                    try {
                        final EntityType entityType = EntityType.valueOf(entityTypeName);
                        final Block block = player.getTargetBlock((Set) Collections.singleton(Material.AIR), 10);
                        final Material type = block.getType();
                        if (type != Material.AIR) {
                            final BlockState blockState = block.getState();
                            if (blockState instanceof CreatureSpawner) {
                                ((CreatureSpawner) blockState).setSpawnedType(entityType);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        "&aCambiaste el spawner al tipo &b" + entityTypeName + "&a!"));
                            } else {
                                player.sendMessage(ChatColor.RED + "El bloque que estas apuntando no es un spawner: "
                                        + type + "!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "No estas apuntando a ningun bloque!");
                        }
                    } catch (IllegalArgumentException | NullPointerException ex2) {
                        // final RuntimeException ex;
                        // final RuntimeException e = ex;
                        player.sendMessage(ChatColor.RED + "Entidad invalida: " + entityTypeName + "!");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "/" + arg2 + " <entidad>");
                }
            } else {
                arg0.sendMessage(ChatColor.RED + "No puedes usar este comando desde la consola!");
            }
        } else {
            arg0.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cTe falta el permiso &4essentials.spawner &cpara usar este comando!"));
        }
        return true;
    }
}
