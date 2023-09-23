package me.supdapillar.theroad;

import me.supdapillar.theroad.Commands.*;
import me.supdapillar.theroad.Managers.GameManager;
import me.supdapillar.theroad.Talisman.*;
import me.supdapillar.theroad.Tasks.CounterLoop;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Listeners.*;
import me.supdapillar.theroad.gameClasses.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TheRoadPlugin extends JavaPlugin {

    private static TheRoadPlugin mainPlugin;
    public static TheRoadPlugin getInstance(){
        return mainPlugin;
    }

    public HashMap<Player, Integer> PlayerScores = new HashMap<Player, Integer>();
    public HashMap<Player, List<Classes>> PlayerUnlockedClasses = new HashMap<Player, List<Classes>>();
    public HashMap<Player, Classes> PlayerClass = new HashMap<Player, Classes>();
    public HashMap<Player, List<Talisman>> PlayerActiveTalismans = new HashMap<Player, List<Talisman>>();
    public HashMap<Player, List<Talisman>> PlayerUnlockedTalisman = new HashMap<Player, List<Talisman>>();
    public CounterLoop counterLoop = new CounterLoop();
    public GameManager gameManager;
    public boolean nextMobIsSummoned = false;
    public GameClass[] gameClasses = new GameClass[]{
            new Swordsman(TheRoadPlugin.getInstance()),
            new Archer(TheRoadPlugin.getInstance()),
            new Executioner(TheRoadPlugin.getInstance()),
            new Assassin(TheRoadPlugin.getInstance()),
            new Summoner(TheRoadPlugin.getInstance())
    };

    public Talisman[] talismans = new Talisman[]{
            new CritTalisman(),
            new ArrowTalisman(),
            new XPTalisman(),
            new SlownessTalisman(),
            new VampireTalisman(),
            new InfernoTalisman(),
            new SkullTalisman(),
            new PotionTalisman(),
            new KnockbackTalisman(),
            new HellFireTalisman(),
            new PanicTalisman(),
            new AgonyTalisman(),
    };

    @Override
    public void onEnable()
    {
        //createWorld();
        mainPlugin = this;
        new PlayerJoinListener(this);
        new InteractListener(this);
        new MobDeathListener(this);
        new InventoryInteractionListener(this);
        new MobSpawnListener(this);
        new MobDamageListener(this);
        new PlayerSpawnListener(this);
        new PlayerLeaveListener(this);
        new MovementListener(this);
        new PlayerDeathListener(this);
        new PlayerRespawnListener(this  );
        new HungerChangeListener(this);
        new MobDamageByEntityListener(this);
        new EntityExplodeListener(this);
        new MobTargetListener(this);
        new EntityRegainHealthListener(this);
        new PlayerInteractEntityListener(this);



        ScoreboardHandler.updateScoreboard(this);

        getCommand("MakeSpawn").setExecutor(new EntitySpawnPointCommand());
        getCommand("MakeBlockage").setExecutor(new MakeBlockageCommand());
        getCommand("ChangeWorld").setExecutor(new ChangeWorldCommand());
        getCommand("MakeRespawnBeacon").setExecutor(new RespawnBeaconCommand());
        getCommand("MakeLootGenerator").setExecutor(new MakeLootGeneratorCommand());
        counterLoop.runTaskTimer(this,0,10);

        World map1 = Bukkit.getServer().createWorld(new WorldCreator("SkyRoad"));
        Bukkit.getServer().getWorlds().add(map1);

        World map2 = Bukkit.getServer().createWorld(new WorldCreator("HauntedRoad"));
        Bukkit.getServer().getWorlds().add(map2);
        gameManager = new GameManager();




        //So I get no null exceptions
        for(Player player : Bukkit.getOnlinePlayers()){
            PlayerClass.putIfAbsent(player, Classes.Swordsman);
            PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
            PlayerScores.putIfAbsent(player,0);
            mainPlugin.PlayerActiveTalismans.putIfAbsent(player, new ArrayList<Talisman>());
            mainPlugin.PlayerUnlockedTalisman.putIfAbsent(player, new ArrayList<Talisman>());
        }
    }

    public void createWorld(){
        WorldCreator worldCreator = new WorldCreator("HauntedRoad");
        worldCreator.generateStructures(false);
        worldCreator.copy(Bukkit.getWorld("minigame"));

        worldCreator.createWorld();

    }

    @Override
    public void onDisable() {
    }
}