package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnListener implements Listener {
    private TheRoadPlugin mainPlugin;
    public PlayerSpawnListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event){
        //Randomize the player somewhere in the ring of the lobby
        switch(TheRoadPlugin.getInstance().gameManager.gamestates){
            case lobby:
            case starting:
                double randomAngle = (Math.PI*2) * Math.random();
                Bukkit.broadcastMessage("Spawned");
                Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
                event.setSpawnLocation(location);
                event.getPlayer().setBedSpawnLocation(location);
                event.getPlayer().setSaturation(9999f);
                break;
        }
    }
}
