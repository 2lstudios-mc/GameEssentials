package dev._2lstudios.gameessentials.commands.initializers;

import org.bukkit.Server;
import dev._2lstudios.gameessentials.commands.VanishCMD;
import dev._2lstudios.gameessentials.commands.TpallCMD;
import dev._2lstudios.gameessentials.commands.TpaCMD;
import dev._2lstudios.gameessentials.commands.TpacceptCMD;
import dev._2lstudios.gameessentials.commands.ToggleTPCMD;
import dev._2lstudios.gameessentials.commands.TeleportCMD;
import dev._2lstudios.gameessentials.commands.SpeedCMD;
import dev._2lstudios.gameessentials.commands.SpawnerCMD;
import dev._2lstudios.gameessentials.commands.SpawnCMD;
import dev._2lstudios.gameessentials.commands.SetSpawnCMD;
import dev._2lstudios.gameessentials.commands.SetHomeCMD;
import dev._2lstudios.gameessentials.commands.RepairCMD;
import dev._2lstudios.gameessentials.commands.MoreCMD;
import dev._2lstudios.gameessentials.commands.ItemInfoCMD;
import dev._2lstudios.gameessentials.commands.HomeCMD;
import dev._2lstudios.gameessentials.commands.DelHomeCMD;
import dev._2lstudios.gameessentials.commands.HelpopCMD;
import dev._2lstudios.gameessentials.commands.HealCMD;
import dev._2lstudios.gameessentials.commands.HatCMD;
import dev._2lstudios.gameessentials.commands.GamemodeCMD;
import dev._2lstudios.gameessentials.commands.GameEssentialsCMD;
import dev._2lstudios.gameessentials.commands.FlyCMD;
import dev._2lstudios.gameessentials.commands.FeedCMD;
import dev._2lstudios.gameessentials.commands.EnderChestCMD;
import dev._2lstudios.gameessentials.commands.CraftCMD;
import dev._2lstudios.gameessentials.commands.ClearCMD;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.gameessentials.commands.BroadcastCMD;
import dev._2lstudios.gameessentials.commands.AutoFeedCMD;
import org.bukkit.command.CommandExecutor;
import dev._2lstudios.gameessentials.commands.AnvilCMD;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandInitializer {
    public CommandInitializer(final JavaPlugin plugin, final EssentialsManager essentialsManager) {
        final Server server = plugin.getServer();
        plugin.getCommand("anvil").setExecutor((CommandExecutor) new AnvilCMD(essentialsManager));
        plugin.getCommand("autofeed")
                .setExecutor((CommandExecutor) new AutoFeedCMD(server, essentialsManager.getAutoFeedPlayers()));
        plugin.getCommand("broadcast").setExecutor((CommandExecutor) new BroadcastCMD((Plugin) plugin));
        plugin.getCommand("clear").setExecutor((CommandExecutor) new ClearCMD((Plugin) plugin));
        plugin.getCommand("craft").setExecutor((CommandExecutor) new CraftCMD());
        plugin.getCommand("enderchest").setExecutor((CommandExecutor) new EnderChestCMD());
        plugin.getCommand("feed").setExecutor((CommandExecutor) new FeedCMD((Plugin) plugin));
        plugin.getCommand("fly").setExecutor((CommandExecutor) new FlyCMD((Plugin) plugin));
        plugin.getCommand("gameessentials").setExecutor((CommandExecutor) new GameEssentialsCMD(essentialsManager));
        plugin.getCommand("gamemode").setExecutor((CommandExecutor) new GamemodeCMD());
        plugin.getCommand("hat").setExecutor((CommandExecutor) new HatCMD());
        plugin.getCommand("heal").setExecutor((CommandExecutor) new HealCMD((Plugin) plugin));
        plugin.getCommand("helpop")
                .setExecutor((CommandExecutor) new HelpopCMD(server, essentialsManager.getPlayerManager()));
        plugin.getCommand("delhome").setExecutor((CommandExecutor) new DelHomeCMD(essentialsManager));
        plugin.getCommand("home").setExecutor((CommandExecutor) new HomeCMD((Plugin) plugin, essentialsManager));
        plugin.getCommand("iteminfo").setExecutor((CommandExecutor) new ItemInfoCMD());
        plugin.getCommand("more").setExecutor((CommandExecutor) new MoreCMD());
        plugin.getCommand("repair").setExecutor((CommandExecutor) new RepairCMD());
        plugin.getCommand("sethome").setExecutor((CommandExecutor) new SetHomeCMD(essentialsManager));
        plugin.getCommand("setspawn").setExecutor((CommandExecutor) new SetSpawnCMD(essentialsManager));
        plugin.getCommand("spawn").setExecutor((CommandExecutor) new SpawnCMD(essentialsManager));
        plugin.getCommand("spawner").setExecutor((CommandExecutor) new SpawnerCMD());
        plugin.getCommand("speed").setExecutor((CommandExecutor) new SpeedCMD());
        plugin.getCommand("teleport").setExecutor((CommandExecutor) new TeleportCMD());
        plugin.getCommand("toggletp").setExecutor((CommandExecutor) new ToggleTPCMD(essentialsManager));
        plugin.getCommand("tpaccept").setExecutor((CommandExecutor) new TpacceptCMD(essentialsManager));
        plugin.getCommand("tpa").setExecutor((CommandExecutor) new TpaCMD(essentialsManager));
        plugin.getCommand("tpall").setExecutor((CommandExecutor) new TpallCMD());
        plugin.getCommand("vanish").setExecutor((CommandExecutor) new VanishCMD(essentialsManager));
    }
}
