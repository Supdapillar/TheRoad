package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {
    public PlayerToggleSneakListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }




    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        //Bukkit.broadcastMessage("Sneaking is now: " + event.isSneaking());
    }
}
