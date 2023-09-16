package me.supdapillar.theroad.Managers;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameManager {
    public Gamestates gamestates = Gamestates.lobby;
    public ArrayList<Arena> gameArenas = new ArrayList<>();
    public int currentArena = 1;
    public int currentRound = 0;
    public List<ArmorStand> blockageArmorStands;

    public GameManager(){
        gameArenas.add(new Arena("The Sky Road", new Location(Bukkit.getWorld("SkyRoad"), 0.5, 2, 0.5),10));
        gameArenas.add(new Arena("The Haunted Road", new Location(Bukkit.getWorld("HauntedRoad"), 34.5, -59, 3.5),10));

    }

    public void startGame(){
        for (Player player : Bukkit.getOnlinePlayers()){
            //Teleport the player to selected arena
            TheRoadPlugin plugin = TheRoadPlugin.getInstance();
            player.getInventory().clear();
            GameClass.getClassFromEnum(plugin.PlayerClass.get(player)).givePlayerClassItems(player);
            player.sendTitle(ChatColor.BOLD + gameArenas.get(currentArena).arenaName, "", 0 , 1, 1);
            player.setGameMode(GameMode.ADVENTURE);
            }
    }

    public void resetGame(){
        gamestates = Gamestates.lobby;
        TheRoadPlugin.getInstance().counterLoop.counter = 10;
        for (Player player : Bukkit.getOnlinePlayers()){

            player.getInventory().clear();

            player.setGameMode(GameMode.ADVENTURE);
            StarterItems.GiveClassCompass(player);

            StarterItems.GiveUnreadyConcrete(player);
            StarterItems.GiveTalismanTotem(player);
            double randomAngle = (Math.PI*2) * Math.random();
            Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
            player.teleport(location);
            currentRound = 0;
        }
    }

    public void summonWave(){
        World world = gameArenas.get(currentArena).spawnLocation.getWorld();
        for(Entity entity : (world.getEntities())){
            if (entity instanceof ArmorStand){
                ArmorStand armorstand = (ArmorStand) entity;
                System.out.println("Attempted to spawn");
                PersistentDataContainer data = armorstand.getPersistentDataContainer();
                if (Objects.equals(data.get(new NamespacedKey(TheRoadPlugin.getInstance(), "Round"), PersistentDataType.INTEGER), currentRound)) {
                    armorstand.getLocation().getWorld().spawnEntity(armorstand.getLocation(),EntityType.valueOf(data.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING)), true);
                }
            }
        }
    }
}
