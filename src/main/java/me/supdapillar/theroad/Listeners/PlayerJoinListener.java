package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.*;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
        // Makes sure the player doesn't have null values
        mainPlugin.PlayerClass.putIfAbsent(player, Classes.Swordsman);
        mainPlugin.PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
        mainPlugin.PlayerScores.putIfAbsent(player,0);
        mainPlugin.TalismanSlots.putIfAbsent(player,1);
        mainPlugin.PlayerActiveTalismans.putIfAbsent(player, new ArrayList<Talisman>());
        mainPlugin.PlayerUnlockedTalisman.putIfAbsent(player, new ArrayList<Talisman>());
        ScoreboardHandler.updateScoreboard(mainPlugin);
        ///// Load stuff /////
        PersistentDataContainer playerData = player.getPersistentDataContainer();
        NamespacedKey moneyKey = new NamespacedKey(TheRoadPlugin.getInstance(),"Money");
        NamespacedKey unlockedClassesKey = new NamespacedKey(TheRoadPlugin.getInstance(),"unlockedClasses");
        NamespacedKey unlockedTalisman = new NamespacedKey(TheRoadPlugin.getInstance(),"unlockedTalisman");
        NamespacedKey talismanSlotsKey = new NamespacedKey(TheRoadPlugin.getInstance(),"TalismanSlots");
        ////make new save for new player
        if (!playerData.has(moneyKey, PersistentDataType.INTEGER)){
            playerData.set(moneyKey,PersistentDataType.INTEGER, 0);
            playerData.set(unlockedClassesKey,PersistentDataType.INTEGER_ARRAY, new int[9]);
            playerData.set(unlockedTalisman,PersistentDataType.INTEGER_ARRAY, new int[20]);
            playerData.set(moneyKey,PersistentDataType.INTEGER, 0);
            playerData.set(talismanSlotsKey,PersistentDataType.INTEGER, 1);
        }
        //load in old save for reoccurring player
        else {
            //Load Unlocked Classes
            mainPlugin.PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
            int i = 0;
            for( GameClass gameClass : TheRoadPlugin.getInstance().gameClasses){
                if (playerData.get(unlockedClassesKey,PersistentDataType.INTEGER_ARRAY)[i] == 1){
                    TheRoadPlugin.getInstance().PlayerUnlockedClasses.get(player).add(gameClass.representingClass);
                }
                i++;
            }
            //Load Unlocked Talisman
            mainPlugin.PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
            i = 0;
            for(Talisman talisman : TheRoadPlugin.getInstance().talismans){
                if (playerData.get(unlockedTalisman,PersistentDataType.INTEGER_ARRAY)[i] == 1){
                    TheRoadPlugin.getInstance().PlayerUnlockedTalisman.get(player).add(talisman);
                }
                i++;
            }
            //Load cash
            mainPlugin.PlayerScores.put(player,playerData.get(moneyKey,PersistentDataType.INTEGER));
            ScoreboardHandler.updateScoreboard(mainPlugin);
            //Loads talisman count
            if (playerData.has(talismanSlotsKey, PersistentDataType.INTEGER)){
                mainPlugin.TalismanSlots.put(player,playerData.get(talismanSlotsKey,PersistentDataType.INTEGER));
            }
            else {
                playerData.set(talismanSlotsKey,PersistentDataType.INTEGER, 1);
            }

        }
        /////// Loading in the player depending on the current scene
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
                break;
            case inGame:
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(ChatColor.YELLOW + "You are now spectating");
                player.teleport(TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation);
                break;
        }
    }
}
