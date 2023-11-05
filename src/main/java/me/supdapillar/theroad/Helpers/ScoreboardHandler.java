package me.supdapillar.theroad.Helpers;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ScoreboardHandler {
    public static void updateScoreboard(TheRoadPlugin plugin){

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Points", Criteria.DUMMY, ChatColor.GREEN + "" + ChatColor.BOLD + "$$ Cash $$");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player X : Bukkit.getOnlinePlayers()){
            plugin.PlayerScores.putIfAbsent(X, 0);

            Object[] array = plugin.PlayerScores.values().stream().sorted().toArray();

            Score score = objective.getScore(X.getDisplayName() + ": " + ChatColor.GREEN + plugin.PlayerScores.get(X) + "$");
            score.setScore(returnIndex(plugin.PlayerScores.get(X), array));
            X.setScoreboard(scoreboard);
        }
    }

    private static int returnIndex(int i, Object[] array){
        int c = 0;
        for(Object object : array){
            if (object instanceof Integer){
                int x = (int) object;
                if (i == x){
                    return c;
                }
            }
            c++;
        }
        return 0;
    }
}
