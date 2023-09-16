package me.supdapillar.theroad.Helpers;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardHandler {
    public static void updateScoreboard(TheRoadPlugin plugin){

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Points", Criteria.DUMMY, ChatColor.GREEN + "" + ChatColor.BOLD + "$$ Cash $$");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int i =0;
        for (Player player : Bukkit.getOnlinePlayers()){

            plugin.PlayerScores.putIfAbsent(player, 0);


            Score score = objective.getScore(player.getDisplayName() + ": " + ChatColor.GREEN + plugin.PlayerScores.get(player) + "$");
            score.setScore(i);
            player.setScoreboard(scoreboard);
            i++;
        }
    }
}
