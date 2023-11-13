package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.DatabaseHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.Collections;

public class PlayerLeaveListener implements Listener {

    public PlayerLeaveListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }





    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        DatabaseHandler.getInstance().savePlayer(player);

        //Checks if the game counter should run
        if (TheRoadPlugin.getInstance().gameManager.gamestates == Gamestates.inGame){
            int NumOfPlayers = 0;
            for (Player player1 : Bukkit.getOnlinePlayers()){
                if (player1.getGameMode() == GameMode.ADVENTURE){
                    NumOfPlayers++;
                }
            }
            if (NumOfPlayers < 1){
                TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.lobby;
            }
        }
    }
}
