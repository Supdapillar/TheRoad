package me.supdapillar.theroad.Listeners;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import me.supdapillar.theroad.Helpers.DatabaseHandler;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.*;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;

public class PlayerJoinListener implements Listener {
    private final TheRoadPlugin mainPlugin;
    public PlayerJoinListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        mainPlugin = plugin;
    }



    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //Sets default values
        mainPlugin.PlayerClass.putIfAbsent(player, Classes.Swordsman);
        mainPlugin.PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
        mainPlugin.PlayerScores.putIfAbsent(player,0);
        mainPlugin.TalismanSlots.putIfAbsent(player,1);
        mainPlugin.PlayerActiveTalismans.putIfAbsent(player, new ArrayList<Talisman>());
        mainPlugin.PlayerUnlockedTalisman.putIfAbsent(player, new ArrayList<Talisman>());

        DatabaseHandler.getInstance().loadPLayer(player);

        ScoreboardHandler.updateScoreboard(mainPlugin);


        /////// Loading in the player depending on the current scene
        switch (TheRoadPlugin.getInstance().gameManager.gamestates) {
            case lobby:
            case starting:
                if (!player.getInventory().contains(Material.RECOVERY_COMPASS)) {
                    StarterItems.GiveClassCompass(event.getPlayer());
                }
                if (!player.getInventory().contains(Material.TOTEM_OF_UNDYING)) {
                    StarterItems.GiveTalismanTotem(player);
                }
                if (!player.getInventory().contains(Material.RED_CONCRETE)) {
                    if (!player.getInventory().contains(Material.GREEN_CONCRETE)) {
                        StarterItems.GiveUnreadyConcrete(player);
                    }
                }
                if (!player.getInventory().contains(Material.MAP)) {
                    StarterItems.GiveMapSelection(player);
                }
                break;
            case inGame:
                if (TheRoadPlugin.getInstance().playersInMatch.contains(player.getDisplayName())) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(ChatColor.YELLOW + "You are now spectating");
                    player.teleport(TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation);
                } else {
                    TheRoadPlugin.getInstance().gameManager.respawnPlayer(player, TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation);
                }
                break;

        }
    }
}
