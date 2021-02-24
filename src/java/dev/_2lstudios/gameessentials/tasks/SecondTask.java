// 
// Decompiled by Procyon v0.5.36
// 

package dev._2lstudios.gameessentials.tasks;

import org.bukkit.Location;
import org.bukkit.scoreboard.Team;
import dev._2lstudios.gameessentials.utils.VersionUtil;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.GameMode;
import org.bukkit.scoreboard.Score;
import org.bukkit.World;
import dev._2lstudios.gameessentials.managers.SidebarManager;
import dev._2lstudios.gameessentials.instanceables.EssentialsPlayer;
import org.bukkit.scoreboard.Objective;
import org.bukkit.ChatColor;
import java.util.HashSet;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitScheduler;
import java.util.Iterator;
import org.bukkit.Server;
import dev._2lstudios.gameessentials.managers.EssentialsManager;
import org.bukkit.plugin.Plugin;
import dev._2lstudios.gameessentials.hooks.TeamsHook;
import org.bukkit.entity.Player;
import java.util.Collection;
import dev._2lstudios.gameessentials.managers.PlaceholderAPIManager;
import dev._2lstudios.gameessentials.managers.PlayerManager;
import dev._2lstudios.gameessentials.managers.VariableManager;

import dev._2lstudios.gameessentials.utils.SideboardExpressions;

public class SecondTask {
    private final VariableManager variableManager;
    private final PlayerManager playerManager;
    private final PlaceholderAPIManager placeholderAPIManager;
    private final Collection<Player> autofeedPlayers;
    private final TeamsHook teamsHook;
    private final Thread thread;
    private static final String PREFIX_FORMAT = "%vault_prefix%";
    private static final String SUFFIX_FORMAT = "%vault_suffix%";
    private static final String TEAMS_PREFIX_FORMAT = "%vault_prefix%%teams_color_%player_name%%";
    private boolean running;

    public SecondTask(final Plugin plugin, final EssentialsManager essentialsManager,
            final Collection<TeleportTask> teleportTasks, final TeamsHook teamsHook) {
        this.running = true;
        final Server server = plugin.getServer();
        final BukkitScheduler scheduler = server.getScheduler();
        this.variableManager = essentialsManager.getVariableManager();
        this.playerManager = essentialsManager.getPlayerManager();
        this.placeholderAPIManager = essentialsManager.getPlaceholderAPIManager();
        this.autofeedPlayers = essentialsManager.getAutoFeedPlayers();
        this.teamsHook = teamsHook;

        scheduler.runTaskTimer(plugin, () -> {
            final Iterator<TeleportTask> teleportTaskIterator = teleportTasks.iterator();
            TeleportTask teleportTask;

            while (teleportTaskIterator.hasNext()) {
                teleportTask = teleportTaskIterator.next();
                if (teleportTask == null || teleportTask.tick()) {
                    teleportTaskIterator.remove();
                }
            }
            return;
        }, 20L, 20L);
        (this.thread = new Thread() {
            @Override
            public void run() {
                int skipTicks = 10;
                while (SecondTask.this.running) {
                    try {
                        --skipTicks;
                        for (final Player player : server.getOnlinePlayers()) {
                            SecondTask.this.update(player, skipTicks);
                        }
                        if (skipTicks <= 0) {
                            skipTicks = 10;
                        }
                        Thread.sleep(1000L);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private String trimToLength(final String string, final int length) {
        return string.substring(0, Math.min(length, string.length()));
    }

    private String getTeamsPrefix(final Player player, final String playerName) {
        final String prefix = this.placeholderAPIManager.setPlaceholders((OfflinePlayer) player,
                "%vault_prefix%%teams_color_%player_name%%".replace("%player_name%", playerName));
        return this.trimToLength(prefix, 16);
    }

    private String getPrefix(final Player player, final String playerName) {
        final String prefix = this.placeholderAPIManager.setPlaceholders((OfflinePlayer) player,
                "%vault_prefix%".replace("%player_name%", playerName));
        return this.trimToLength(prefix, 16);
    }

    private String getSuffix(final Player player, final String playerName) {
        final String suffix = this.placeholderAPIManager.setPlaceholders((OfflinePlayer) player,
                "%vault_suffix%".replace("%player_name%", playerName));
        return this.trimToLength(suffix, 16);
    }

    public void update(final Player player, final int skipTicks) {
        if (player != null && player.isOnline() && !player.isDead()) {
            if (this.variableManager.isTabEnabled()) {
                this.updateTab(player);
            }
            if (this.autofeedPlayers.contains(player)) {
                player.setFoodLevel(20);
                player.setSaturation(4.0f);
            }
            final Scoreboard scoreboard = player.getScoreboard();
            if (scoreboard != null) {
                synchronized (scoreboard) {
                    if (this.variableManager.getSidebarManager().isEnabled()) {
                        this.updateScoreboard(player, scoreboard);
                    }
                    if (this.variableManager.isNametagEnabled()) {
                        this.updateHealthBelow(player, scoreboard);
                        if (skipTicks <= 0) {
                            this.updateNametag(player, scoreboard);
                        }
                    }
                }
                // monitorexit(scoreboard)
            }
        }
    }

    private void updateTab(final Player player) {
        final String playerName = player.getName();
        final String prefix = this.getPrefix(player, playerName);
        final String suffix = this.getSuffix(player, playerName);
        final String listName = String.valueOf(prefix) + player.getDisplayName() + suffix;
        if (!player.getPlayerListName().equals(listName)) {
            player.setPlayerListName(listName);
        }
    }

    private void updateScoreboard(final Player player, final Scoreboard scoreboard) {
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
        if (essentialsPlayer == null) {
            return;
        }
        if (essentialsPlayer.isScoreboardEnabled()) {
            final Objective lastScoreboardObjective = essentialsPlayer.getScoreboardObjective();
            Objective scoreboardObjective;
            if (lastScoreboardObjective == null) {
                final Objective objective = scoreboard.getObjective("2LSScoreboard");
                if (objective == null) {
                    scoreboardObjective = scoreboard.registerNewObjective("2LSScoreboard", "dummy");
                    scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    essentialsPlayer.setScoreboardObjective(scoreboardObjective);
                } else {
                    scoreboardObjective = objective;
                }
            } else {
                scoreboardObjective = lastScoreboardObjective;
            }
            if (scoreboardObjective != null) {
                final SidebarManager boardManager = this.variableManager.getSidebarManager();
                final World world = player.getWorld();
                Collection<String> list;
                if (world != null) {
                    list = boardManager.getSidebars(player, world.getName().toLowerCase());
                } else {
                    list = boardManager.getSidebars(player, "default");
                }
                if (list != null && !list.isEmpty()) {
                    final Collection<String> actualLines = new HashSet<String>();
                    int size = list.size();
                    if (size > 15) {
                        size = 15;
                    }
                    int actualSize = size;
                    for (String line : list) {
                        line = ChatColor.translateAlternateColorCodes('&',
                                this.placeholderAPIManager.setPlaceholders((OfflinePlayer) player, line));

                        final boolean displayLine = SideboardExpressions.evaluateExpression(line);
                        if (!displayLine) {
                            continue;
                        } else {
                            String expression = SideboardExpressions.extractExpression(line);
                            if (expression != null) {
                                line = line.replace("[" + expression + "]", "");
                            }
                        }

                        /*
                         * if (line.startsWith("[not_") && line.contains("]")) { final String
                         * placeholder = line.split("\\]")[0].split("\\[")[1]; if
                         * (ChatColor.stripColor(line).endsWith( " " +
                         * placeholder.replace(String.valueOf(placeholder.split("_")[0]) + "_", ""))) {
                         * continue; } line = line.replace("[" + placeholder + "]", ""); }
                         * 
                         * else if (line.startsWith("[end_") && line.contains("]")) { final String
                         * placeholder = line.split("\\]")[0].split("\\[")[1]; if
                         * (!ChatColor.stripColor(line).endsWith( " " +
                         * placeholder.replace(String.valueOf(placeholder.split("_")[0]) + "_", ""))) {
                         * continue; } line = line.replace("[" + placeholder + "]", ""); }
                         */

                        if (!line.isEmpty() && !line.contains("[display=false]")) {
                            if (line.length() > 40) {
                                line = line.substring(0, 40);
                            }
                            if (actualSize == size) {
                                final String displayName = scoreboardObjective.getDisplayName();
                                if (displayName == null || !displayName.equals(line)) {
                                    scoreboardObjective.setDisplayName(line);
                                }
                            } else {
                                final Score score = scoreboardObjective.getScore(line);
                                if (score.getScore() != actualSize) {
                                    score.setScore(actualSize);
                                }
                            }
                            actualLines.add(line);
                            --actualSize;
                        }
                    }
                    final Collection<String> scoreboardEntries = (Collection<String>) scoreboard.getEntries();
                    for (final String entry : scoreboardEntries) {
                        final Score score2 = scoreboardObjective.getScore(entry);
                        if (score2 == null || (!actualLines.contains(entry) && score2.isScoreSet())) {
                            scoreboard.resetScores(entry);
                        }
                    }
                }
            }
        } else {
            final Objective objective2 = essentialsPlayer.getScoreboardObjective();
            if (objective2 != null) {
                objective2.unregister();
                essentialsPlayer.setScoreboardObjective(null);
            }
        }
    }

    private void updateNametag(final Player player, final Scoreboard scoreboard) {
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
        if (essentialsPlayer == null) {
            return;
        }
        final Collection<Team> teams = (Collection<Team>) scoreboard.getTeams();
        final Collection<String> nametagWhitelist = this.variableManager.getNametagWhitelist();
        final World world = player.getWorld();
        final String playerName = player.getName();
        for (final Player ply : world.getPlayers()) {
            if (ply.isDead() || !ply.isOnline()) {
                break;
            }
            if (world != ply.getWorld()) {
                break;
            }
            final EssentialsPlayer essentialsPly = this.playerManager.getPlayer(ply.getUniqueId());
            if (essentialsPly == null) {
                break;
            }
            final World plyWorld = ply.getWorld();
            final GameMode plyGameMode = ply.getGameMode();
            Team team = essentialsPlayer.getNametagTeam(ply);
            if (plyGameMode == GameMode.SPECTATOR || essentialsPly.isVanished()
                    || (!nametagWhitelist.isEmpty() && !nametagWhitelist.contains(plyWorld.getName()))
                    || (!this.teamsHook.isSameTeam(player, ply)
                            && ply.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
                if (team == null || !teams.contains(team)) {
                    continue;
                }
                team.unregister();
                essentialsPlayer.removeNametagTeam(ply);
            } else {
                if (!teams.contains(team)) {
                    final String plyName = ply.getName();
                    final String teamName = this.trimToLength(ply.getName(), 16);
                    team = scoreboard.getTeam(teamName);
                    if (team == null) {
                        team = scoreboard.registerNewTeam(teamName);
                    }
                    if (VersionUtil.isOneDotNine()) {
                        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
                    }
                    team.addEntry(plyName);
                    essentialsPlayer.addNametagTeam(ply, team);
                }
                String prefix;
                String suffix;
                if (essentialsPly.isNametag()) {
                    if (this.teamsHook.isHooked()) {
                        prefix = this.getTeamsPrefix(ply, playerName);
                    } else {
                        prefix = this.getPrefix(ply, playerName);
                    }
                    suffix = this.getSuffix(ply, playerName);
                } else {
                    prefix = ChatColor.translateAlternateColorCodes('&', "&7");
                    suffix = "";
                }
                if (!team.getPrefix().equals(prefix)) {
                    team.setPrefix(prefix);
                }
                if (team.getSuffix().equals(suffix)) {
                    continue;
                }
                team.setSuffix(suffix);
            }
        }
    }

    private void updateHealthBelow(final Player player, final Scoreboard scoreboard) {
        final EssentialsPlayer essentialsPlayer = this.playerManager.getPlayer(player.getUniqueId());
        if (essentialsPlayer == null) {
            return;
        }
        final World world = player.getWorld();
        final String worldName = world.getName();
        final GameMode gameMode = player.getGameMode();
        if (!worldName.equalsIgnoreCase("lobby") && gameMode != GameMode.SPECTATOR) {
            final Objective lastHealthObjective = essentialsPlayer.getHealthObjective();
            Objective healthObjective;
            if (lastHealthObjective == null) {
                final Objective objective = scoreboard.getObjective("2LSHealth");
                if (objective == null) {
                    healthObjective = scoreboard.registerNewObjective("2LSHealth", "dummy");
                    healthObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
                    essentialsPlayer.setHealthObjective(objective);
                } else {
                    healthObjective = objective;
                }
            } else {
                healthObjective = lastHealthObjective;
            }
            if (healthObjective != null) {
                final Location location = player.getLocation();
                if (!healthObjective.getDisplayName().equals(ChatColor.RED + "\u2764")) {
                    healthObjective.setDisplayName(ChatColor.RED + "\u2764");
                }
                for (final Player ply : world.getPlayers()) {
                    if (ply.isOnline() && !ply.isDead() && world == ply.getWorld()
                            && location.distance(ply.getLocation()) < 12.0) {
                        final int health = (int) ply.getHealth();
                        final Score score = healthObjective.getScore(ply.getName());
                        if (score.getScore() == health) {
                            continue;
                        }
                        score.setScore(health);
                    }
                }
            }
        } else {
            final Objective objective2 = essentialsPlayer.getHealthObjective();
            if (objective2 != null) {
                objective2.unregister();
                essentialsPlayer.setHealthObjective(null);
            }
        }
    }

    public void stop() {
        this.running = false;
    }
}
