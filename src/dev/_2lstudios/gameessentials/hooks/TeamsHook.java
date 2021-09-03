package dev._2lstudios.gameessentials.hooks;

import org.bukkit.entity.Player;

import dev._2lstudios.teams.Teams;
import dev._2lstudios.teams.managers.TeamPlayerManager;
import dev._2lstudios.teams.team.TeamPlayer;

public class TeamsHook {
    private TeamPlayerManager tPlayerManager;
    private boolean hooked;

    public TeamsHook() {
        this.hooked = false;
    }

    public void hook() {
        this.tPlayerManager = Teams.getTeamsManager().getTeamPlayerManager();
        this.hooked = true;
    }

    public boolean isSameTeam(final Player player1, final Player player2) {
        if (this.hooked) {
            final TeamPlayer tPlayer1 = this.tPlayerManager.getPlayer(player1.getName());
            final TeamPlayer tPlayer2 = this.tPlayerManager.getPlayer(player2.getName());
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
