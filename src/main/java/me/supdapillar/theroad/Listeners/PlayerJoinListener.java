package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerJoinListener implements Listener {
    private final TheRoadPlugin mainPlugin;
    public PlayerJoinListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        mainPlugin = plugin;
    }



    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        switch (TheRoadPlugin.getInstance().gameManager.gamestates){
            case lobby:
            case starting:
                if (!player.getInventory().contains(Material.RECOVERY_COMPASS)){
                    StarterItems.GiveClassCompass(event.getPlayer());
                }
                if (!player.getInventory().contains(Material.TOTEM_OF_UNDYING)){
                    StarterItems.GiveTalismanTotem(player);
                }
                if (!player.getInventory().contains(Material.RED_CONCRETE)){
                    if (!player.getInventory().contains(Material.GREEN_CONCRETE)){
                        StarterItems.GiveUnreadyConcrete(player);
                    }
                }
                if (!player.getInventory().contains(Material.MAP)){
                    StarterItems.GiveMapSelection(player);
                }
                // Makes sure the player doesn't have null values
                mainPlugin.PlayerClass.putIfAbsent(player, Classes.Swordsman);
                mainPlugin.PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
                mainPlugin.PlayerScores.putIfAbsent(player,0);
                mainPlugin.PlayerActiveTalismans.putIfAbsent(player, new ArrayList<Talisman>());
                mainPlugin.PlayerUnlockedTalisman.putIfAbsent(player, new ArrayList<Talisman>());
                ScoreboardHandler.updateScoreboard(mainPlugin);
                break;
            case inGame:
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.YELLOW + "You are now spectating");
                player.teleport(TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation);
                break;
        }
    }
}
