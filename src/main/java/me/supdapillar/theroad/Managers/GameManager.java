package me.supdapillar.theroad.Managers;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Arenas.HauntedRoad;
import me.supdapillar.theroad.Arenas.SkyRoad;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Tasks.DelayedSpawn;
import me.supdapillar.theroad.Tasks.GameEndDelayer;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
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
    public int currentArena = 0;
    public int currentRound = 0;
    public List<DelayedSpawn> currentActiveSpawners = new ArrayList<>();

    public GameManager(){
    }

    public void startGame(){
        Bukkit.broadcastMessage(ChatColor.YELLOW + "The game has begun!");
        gamestates = Gamestates.inGame;



        //Deletes all from the previous round
        for (Entity entity : TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)].spawnLocation.getWorld().getEntities()){
            boolean SkipDeletion = entity instanceof Player;
            if (entity instanceof ArmorStand || entity instanceof Painting || entity instanceof ItemFrame){
                SkipDeletion = true;
            }
            if (!SkipDeletion){
                entity.remove();
            }
        }


        //Sets the current arena to the most voted
        Arena mostVoted = gameArenas[0];
        for(Arena arena : gameArenas){
            if (arena.votedPlayers.size() > mostVoted.votedPlayers.size()){
                mostVoted = arena;
            }
        }
        for (int i = 0; i < gameArenas.length; i++)
        {
            if (gameArenas[i] == mostVoted){
                currentArena = i;
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()){
            //Teleport the player to selected arena
            Arena currentSelectedArena = TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)];

            player.teleport(currentSelectedArena.spawnLocation);

            TheRoadPlugin plugin = TheRoadPlugin.getInstance();
            player.getInventory().clear();
            GameClass.getClassFromEnum(plugin.PlayerClass.get(player)).givePlayerClassItems(player);
            player.sendTitle(ChatColor.BOLD + currentSelectedArena.arenaName, "", 0 , 1, 1);
            player.setGameMode(GameMode.ADVENTURE);
            player.playSound(player, Sound.ENTITY_GUARDIAN_DEATH, 9999, 1);

            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());

        }


        //Generates all the loot chests
        NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "SpawnChance");
        for (Entity entity : gameArenas[currentArena].spawnLocation.getWorld().getEntities()){
            if (entity instanceof ArmorStand && entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)){
                ArmorStand armorStand = (ArmorStand) entity;
                Random random = new Random();
                if (random.nextInt(100)+1 < armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) ){
                    makeLootChest(entity);
                }

            }
            //Resets all the beacon names
            if (entity.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(), "IsAbleToRespawn"), PersistentDataType.BOOLEAN)){
                entity.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "IsAbleToRespawn"), PersistentDataType.BOOLEAN, true);
                entity.setCustomName(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "RESPAWN BEACON [CLICK] TO START EVENT");
            }

        }

    }

    public void makeLootChest(Entity entity){

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

    public void respawnPlayer(Player player){
        //Teleport the player to selected arena
        TheRoadPlugin plugin = TheRoadPlugin.getInstance();
        player.getInventory().clear();
        GameClass.getClassFromEnum(plugin.PlayerClass.get(player)).givePlayerClassItems(player);
        player.sendTitle(ChatColor.LIGHT_PURPLE + "You have been respawned", "", 0 , 1, 1);
        player.setGameMode(GameMode.ADVENTURE);
    }

    public void resetGame(boolean gameWasWon){
        //Stuff to reset nomatter the win case
        for(Entity entity : gameArenas[currentArena].spawnLocation.getWorld().getEntities()){
            if (entity instanceof FallingBlock){
                entity.remove();
            }
        }
        currentRound = 0;
        TheRoadPlugin.getInstance().counterLoop.counter = 10;






        if (gameWasWon){
            gamestates = Gamestates.endGame;

            new GameEndDelayer(this).runTaskTimer(TheRoadPlugin.getInstance(), 0, 20);
        }
        else
        {
            gamestates = Gamestates.lobby;
            for (Player player : Bukkit.getOnlinePlayers()){

                player.getInventory().clear();
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                player.setGameMode(GameMode.ADVENTURE);
                StarterItems.GiveClassCompass(player);

                StarterItems.GiveUnreadyConcrete(player);
                StarterItems.GiveTalismanTotem(player);
                StarterItems.GiveMapSelection(player);
                double randomAngle = (Math.PI*2) * Math.random();
                Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
                player.teleport(location);
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

                    currentActiveSpawners.add(new DelayedSpawn(armorstand));

                }
            }
        }

        //Activate all the delayed spawners
        for (DelayedSpawn delayedSpawn : currentActiveSpawners){
            Random random = new Random();
            delayedSpawn.runTaskTimer(TheRoadPlugin.getInstance(), random.nextInt(0,100), 1);
        }
    }
}
