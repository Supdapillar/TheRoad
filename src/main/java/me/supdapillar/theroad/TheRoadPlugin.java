package me.supdapillar.theroad;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import me.supdapillar.theroad.Commands.*;
import me.supdapillar.theroad.Helpers.DatabaseHandler;
import me.supdapillar.theroad.Managers.GameManager;
import me.supdapillar.theroad.Talisman.*;
import me.supdapillar.theroad.Tasks.CounterLoop;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Listeners.*;
import me.supdapillar.theroad.gameClasses.*;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public final class TheRoadPlugin extends JavaPlugin {

    private static TheRoadPlugin mainPlugin;
    public static TheRoadPlugin getInstance(){
        return mainPlugin;
    }

    public HashMap<Player, Integer> PlayerScores = new HashMap<Player, Integer>();
    public HashMap<Player, Integer> TalismanSlots = new HashMap<Player, Integer>();
    public HashMap<Player, List<Classes>> PlayerUnlockedClasses = new HashMap<Player, List<Classes>>();
    public HashMap<Player, Classes> PlayerClass = new HashMap<Player, Classes>();
    public HashMap<Player, List<Talisman>> PlayerActiveTalismans = new HashMap<Player, List<Talisman>>();
    public HashMap<Player, List<Talisman>> PlayerUnlockedTalisman = new HashMap<Player, List<Talisman>>();
    public ArrayList<String> playersInMatch = new ArrayList<>();
    public CounterLoop counterLoop = new CounterLoop();

    public GameManager gameManager;
    public String nextBossIs = "";
    public GameClass[] gameClasses = new GameClass[]{
            new Swordsman(TheRoadPlugin.getInstance()),
            new Archer(TheRoadPlugin.getInstance()),
            new Healer(TheRoadPlugin.getInstance()),
            new Assassin(TheRoadPlugin.getInstance()),
            new WolfTamer(TheRoadPlugin.getInstance()),
            new Merchant(TheRoadPlugin.getInstance()),
            new Defender(TheRoadPlugin.getInstance()),
            new Executioner(TheRoadPlugin.getInstance()),
            new Mage(TheRoadPlugin.getInstance()),
    };

    public Talisman[] talismans = new Talisman[]{
            new CritTalisman(),
            new SlownessTalisman(),
            new KnockbackTalisman(),
            new AgonyTalisman(),
            new PlunderTalisman(),
            new ArrowTalisman(),
            new MendingTalisman(),
            new SkullTalisman(),
            new PotionTalisman(),
            new ShieldTalisman(),
            new PanicTalisman(),
            new TreasureTalisman(),
            new BarrageTalisman(),
            new HellFireTalisman(),
            new FrostTalisman(),
            new VampireTalisman(),
            new PrizeTalisman(),
            new InfernoTalisman(),
            new VigorTalisman(),
            new SoulTalisman(),
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
        new EntityRegainHealthListener(this);
        new PlayerInteractEntityListener(this);
        new InventoryOpenListener(this);
        new EntityChangeBlockListener(this);
        new PlayerToggleSneakListener(this);
        new EntityTargetListener(this);
        new EntityChangeBlockListener(this);
        new AsyncPlayerChatListener(this);

        ScoreboardHandler.updateScoreboard(this);

        getCommand("MakeSpawn").setExecutor(new EntitySpawnPointCommand());
        getCommand("MakeBlockage").setExecutor(new MakeBlockageCommand());
        getCommand("ChangeWorld").setExecutor(new ChangeWorldCommand());
        getCommand("MakeRespawnBeacon").setExecutor(new RespawnBeaconCommand());
        getCommand("MakeCursedTreasure").setExecutor(new CursedTreasureCommand());
        getCommand("MakeChallenge").setExecutor(new ChallengeCommand());
        getCommand("MakeLootGenerator").setExecutor(new MakeLootGeneratorCommand());
        getCommand("MakeShrine").setExecutor(new ConjuringShrineCommand());
        getCommand("Debug").setExecutor(new DebugCommand());
        counterLoop.runTaskTimer(this,0,10);

        World map1 = Bukkit.getServer().createWorld(new WorldCreator("SkyRoad"));
        Bukkit.getServer().getWorlds().add(map1);

        World map2 = Bukkit.getServer().createWorld(new WorldCreator("HauntedRoad"));
        Bukkit.getServer().getWorlds().add(map2);

        World map3 = Bukkit.getServer().createWorld(new WorldCreator("TheCore"));
        Bukkit.getServer().getWorlds().add(map3);


        gameManager = new GameManager();
        //So I get no null exceptions
        for(Player player : Bukkit.getOnlinePlayers()){
            PlayerClass.putIfAbsent(player, Classes.Swordsman);
            PlayerUnlockedClasses.putIfAbsent(player, new ArrayList<Classes>());
            PlayerScores.putIfAbsent(player,0);
            mainPlugin.PlayerActiveTalismans.putIfAbsent(player, new ArrayList<Talisman>());
            mainPlugin.PlayerUnlockedTalisman.putIfAbsent(player, new ArrayList<Talisman>());
            TalismanSlots.putIfAbsent(player,1);
        }
    }

    public void createWorld(){
        WorldCreator worldCreator = new WorldCreator("TheCore");
        worldCreator.generateStructures(false);
        //worldCreator.copy(Bukkit.getWorld("minigame"));
        worldCreator.type(WorldType.FLAT);


        worldCreator.createWorld();


    }
@Override
    public void onDisable() {
    }
}
