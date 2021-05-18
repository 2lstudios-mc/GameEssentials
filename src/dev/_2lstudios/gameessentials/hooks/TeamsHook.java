package dev._2lstudios.gameessentials.hooks;

import twolovers.teams.team.TPlayer;
import org.bukkit.entity.Player;
import twolovers.teams.Teams;
import twolovers.teams.managers.TPlayerManager;

public class TeamsHook {
    private TPlayerManager tPlayerManager;
    private boolean hooked;

    public TeamsHook() {
        this.hooked = false;
    }

    public void hook() {
        this.tPlayerManager = Teams.getTeamsManager().getTPlayerManager();
        this.hooked = true;
    }

    public boolean isSameTeam(final Player player1, final Player player2) {
        if (this.hooked) {
            final TPlayer tPlayer1 = this.tPlayerManager.getPlayer(player1.getName());
            final TPlayer tPlayer2 = this.tPlayerManager.getPlayer(player2.getName());
            if (tPlayer1 != null && tPlayer2 != null) {
                final String team1 = tPlayer1.getTeam();
                final String team2 = tPlayer2.getTeam();
                if (team1 != null && team2 != null && team1.equals(team2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isHooked() {
        return this.hooked;
    }
}
