package me.supdapillar.theroad.Managers;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Arenas.HauntedRoad;
import me.supdapillar.theroad.Arenas.SkyRoad;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameManager {
    public Gamestates gamestates = Gamestates.lobby;
    public Arena[] gameArenas = new Arena[]{
            new SkyRoad(),
            new HauntedRoad(),
    };
    public int currentArena = 1;
    public int currentRound = 0;
    public List<ArmorStand> blockageArmorStands;

    public GameManager(){
    }

    public void startGame(){
        for (Player player : Bukkit.getOnlinePlayers()){
            //Teleport the player to selected arena
            TheRoadPlugin plugin = TheRoadPlugin.getInstance();
            player.getInventory().clear();
            GameClass.getClassFromEnum(plugin.PlayerClass.get(player)).givePlayerClassItems(player);
            player.sendTitle(ChatColor.BOLD + gameArenas[currentArena].arenaName, "", 0 , 1, 1);
            player.setGameMode(GameMode.ADVENTURE);
        }


        //Generates all the loot chests
        NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "SpawnChance");
        for (Entity entity : gameArenas[currentArena].spawnLocation.getWorld().getEntities()){
            if (entity instanceof ArmorStand && entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)){
                ArmorStand armorStand = (ArmorStand) entity;
                Random random = new Random();
                if (random.nextInt(100)+1 < armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) ){
                    BlockData barrelData = Material.BARREL.createBlockData();

                    FallingBlock fallingBlock = entity.getWorld().spawnFallingBlock(entity.getLocation(), barrelData);
                    fallingBlock.setVelocity(new Vector(0,0,0));
                    fallingBlock.setGravity(false);
                    fallingBlock.setInvulnerable(true);
                    fallingBlock.setRotation(90,33);
                    fallingBlock.setCustomName(ChatColor.BOLD + "" + ChatColor.YELLOW + "GOODIES");
                    fallingBlock.setCustomNameVisible(true);
                    fallingBlock.setPersistent(true);
                    fallingBlock.setCancelDrop(true);
                    fallingBlock.setDropItem(true);
                }

            }
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
            StarterItems.GiveMapSelection(player);
            double randomAngle = (Math.PI*2) * Math.random();
            Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
            player.teleport(location);
            currentRound = 0;
        }
        //Removes all loot containers
        for(Entity entity : gameArenas[currentArena].spawnLocation.getWorld().getEntities()){
            if (entity instanceof FallingBlock){
                entity.remove();
            }
        }
    }

    public void summonWave(){
        World world = gameArenas[currentArena].spawnLocation.getWorld();
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
