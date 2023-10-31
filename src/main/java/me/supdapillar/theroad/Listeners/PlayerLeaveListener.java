package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Gamestates;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class PlayerLeaveListener implements Listener {

    public PlayerLeaveListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }





    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        ///// Save stuff /////
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        NamespacedKey moneyKey = new NamespacedKey(TheRoadPlugin.getInstance(),"Money");
        NamespacedKey unlockedClassesKey = new NamespacedKey(TheRoadPlugin.getInstance(),"unlockedClasses");
        NamespacedKey unlockedTalisman = new NamespacedKey(TheRoadPlugin.getInstance(),"unlockedTalisman");
        //Default values
        playerData.set(moneyKey,PersistentDataType.INTEGER, 0);
        playerData.set(unlockedClassesKey,PersistentDataType.INTEGER_ARRAY, new int[9]);
        playerData.set(unlockedTalisman,PersistentDataType.INTEGER_ARRAY, new int[20]);
        //Writes all the data
        //Saves Money
        playerData.set(moneyKey,PersistentDataType.INTEGER,TheRoadPlugin.getInstance().PlayerScores.get(player));
        int[] classesArray = new int[9];
        int[] talismansArray = new int[20];
        //Save Unlocked Classes
        int i = 0;
        for(GameClass gameClass : TheRoadPlugin.getInstance().gameClasses){
            if (TheRoadPlugin.getInstance().PlayerUnlockedClasses.get(player).contains(gameClass.representingClass)){
                classesArray[i] = 1;
            }
            else {
                classesArray[i] = 0;

            }
            playerData.set(unlockedClassesKey,PersistentDataType.INTEGER_ARRAY,classesArray);
            i++;
        }
        //TODO BUG WHEERE THEY AINT GETTING SAVED
        //Save Unlocked Talisman
        i = 0;
        for(Talisman talisman : TheRoadPlugin.getInstance().talismans){
            if (TheRoadPlugin.getInstance().PlayerUnlockedTalisman.get(player).contains(talisman)){
                talismansArray[i] = 1;

            }
            else {
                talismansArray[i] = 0;


            }
            playerData.set(unlockedTalisman,PersistentDataType.INTEGER_ARRAY,talismansArray );
            i++;
        }



        //Checks if the game counter should run
        if (TheRoadPlugin.getInstance().gameManager.gamestates == Gamestates.inGame){
            int NumOfPlayers = 0;
            for (Player player1 : Bukkit.getOnlinePlayers()){
                if (player1.getGameMode() == GameMode.SPECTATOR){
                    NumOfPlayers++;
                }
            }
            if (NumOfPlayers < 1){
                TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.lobby;
            }
        }
    }
}
