package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    public PlayerDeathListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        event.getDrops().clear();

        if (TheRoadPlugin.getInstance().gameManager.gamestates != Gamestates.inGame) return;
        Player player = event.getEntity();

        //Removes challenge on death
        TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).removeIf(o -> o.isChallenge);


        //Get the number of living players
        int NumberOfAlivePlayer = 0;
        for (Player alivePlayer : Bukkit.getOnlinePlayers()){
            if (alivePlayer.getGameMode() != GameMode.SPECTATOR) {
                if (alivePlayer != event.getEntity()){
                    NumberOfAlivePlayer++;
                }

            }
        }

        //Checks if the dead player should become a ghost
        if (NumberOfAlivePlayer < 1){
            TheRoadPlugin.getInstance().gameManager.resetGame(false);
        }
    }
}
