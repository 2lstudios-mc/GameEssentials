package dev._2lstudios.gameessentials.commands;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class RepairCMD implements CommandExecutor {
    private float amazonas;

    public RepairCMD() {
        this.amazonas = 0.0f;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("essentials.repair")) {
            if (sender instanceof Player) {
                final Player player = (Player) sender;
                if (player.getWorld().getEnvironment() != World.Environment.THE_END) {
                    if (args.length > 0 && args[0].equals("all")) {
                        if (sender.hasPermission("essentials.repair.all")) {
                            final PlayerInventory playerInventory = player.getInventory();
                            ItemStack[] contents;
                            for (int length = (contents = playerInventory.getContents()).length,
                                    i = 0; i < length; ++i) {
                                final ItemStack itemStack = contents[i];
                                this.repair(itemStack);
                            }
                            ItemStack[] armorContents;
                            for (int length2 = (armorContents = playerInventory.getArmorContents()).length,
                                    j = 0; j < length2; ++j) {
                                final ItemStack itemStack = armorContents[j];
                                this.repair(itemStack);
                            }
                            player.sendMessage(ChatColor.GREEN + "Reparaste todos los items de tu inventario!");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
                        }
                    } else {
                        final PlayerInventory playerInventory = player.getInventory();
                        final ItemStack itemStack = playerInventory.getItem(playerInventory.getHeldItemSlot());
                        if (itemStack != null) {
                            if (this.repair(itemStack)) {
                                player.sendMessage(ChatColor.GREEN + "Reparaste el item en tu mano!");
                            } else {
                                player.sendMessage(ChatColor.RED + "No puedes reparar el item en tu mano!");
                            }
                        } else {
                            this.amazonas += (float) 0.1;
                            player.sendMessage(ChatColor.GREEN
                                    + "Reparaste parte del aire del amazonas! Gracias por ayudar a parar el incendio!\nAire reparado: "
                                    + this.amazonas + "%!");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No puedes reparar desde el end!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "No puedes ejecutar este comando desde la consola!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Permisos insuficientes!");
        }
        return true;
    }

    public boolean repair(final ItemStack itemStack) {
        if (itemStack != null) {
            final String materialName = itemStack.getType().name();
            if (itemStack.getDurability() != 0) {
                if (!materialName.equals("FLINT_AND_STEEL") && !materialName.equals("FISHING_ROD")
                        && !materialName.endsWith("BOW") && !materialName.endsWith("_SWORD")
                        && !materialName.endsWith("_AXE") && !materialName.endsWith("_SPADE")
                        && !materialName.endsWith("_HOE") && !materialName.endsWith("_PICKAXE")
                        && !materialName.endsWith("_HELMET") && !materialName.endsWith("_CHESTPLATE")
                        && !materialName.endsWith("_LEGGINGS") && !materialName.endsWith("_BOOTS")) {
                    return false;
                }
                itemStack.setDurability((short) 0);
            }
        }
        return true;
    }
}
