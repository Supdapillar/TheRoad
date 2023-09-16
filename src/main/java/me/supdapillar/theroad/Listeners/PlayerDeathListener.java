package me.supdapillar.theroad.Listeners;

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

        //Get the number of living players
        int NumberOfAlivePlayer = 0;
        for (Player alivePlayer : Bukkit.getOnlinePlayers()){
            if (alivePlayer.getGameMode() != GameMode.SPECTATOR) {
                if (alivePlayer != event.getEntity()){
                    NumberOfAlivePlayer++;
                    Bukkit.broadcastMessage(alivePlayer.getDisplayName() + " Is alive");
                }

            }
        }

        Bukkit.broadcastMessage(NumberOfAlivePlayer + "");
        //Checks if the dead player should become a ghost
        if (NumberOfAlivePlayer < 1){
            TheRoadPlugin.getInstance().gameManager.resetGame();

        }
    }
}
