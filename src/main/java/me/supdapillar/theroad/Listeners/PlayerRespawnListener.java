package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {
    public PlayerRespawnListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Bukkit.broadcastMessage("Respawned");
        Player player = event.getPlayer();
        player.getInventory().clear();

        switch(TheRoadPlugin.getInstance().gameManager.gamestates){
            case lobby:
            case starting:
                StarterItems.GiveClassCompass(player);
                StarterItems.GiveUnreadyConcrete(player);
                StarterItems.GiveTalismanTotem(player);
                StarterItems.GiveMapSelection(player);
                player.setGameMode(GameMode.ADVENTURE);
                double randomAngle = (Math.PI*2) * Math.random();
                Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
                event.setRespawnLocation(location);
                break;
            case inGame:
                player.setGameMode(GameMode.SPECTATOR);
                event.setRespawnLocation(TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation);
                break;
        }
    }
}

