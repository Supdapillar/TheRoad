package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    public PlayerLeaveListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }





    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        event.setQuitMessage(ChatColor.YELLOW + event.getPlayer().getDisplayName() + " shit the bed!");

        if (TheRoadPlugin.getInstance().gameManager.gamestates == Gamestates.inGame){
            int NumOfPlayers = 0;
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.getGameMode() == GameMode.SPECTATOR){
                    NumOfPlayers++;
                }
            }
            Bukkit.broadcastMessage(NumOfPlayers + "");
            if (NumOfPlayers < 1){
                TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.lobby;
            }

        }
    }
}
