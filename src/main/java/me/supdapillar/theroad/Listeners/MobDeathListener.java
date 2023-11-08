package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.CritTalisman;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.Tasks.BeaconEventLoop;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobDeathListener implements Listener {
    private TheRoadPlugin mainPlugin;
    public static final HashMap<EntityType, Integer> moneyValues = new HashMap<EntityType, Integer>() {{
        put(EntityType.ZOMBIE, 5);
        put(EntityType.DROWNED, 6);
        put(EntityType.SKELETON, 8);
        put(EntityType.WITHER_SKELETON, 7);
        put(EntityType.STRAY, 9);
        put(EntityType.SPIDER, 5);
        put(EntityType.CHICKEN, 2);
        put(EntityType.CREEPER, 8);
    }};
    public MobDeathListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        mainPlugin = plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){

        Player player = null;

        NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "killer");
        if (event.getEntity().getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING))
        {
            player = Bukkit.getPlayer(event.getEntity().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING));
        }
        else if (event.getEntity().getKiller() != null)
        {
            player = event.getEntity().getKiller();
        }


        //Check to see if the mob died in the area of beacon event
        if (BeaconEventLoop.beaconEventLoop != null){
            if (event.getEntity().getWorld() == BeaconEventLoop.beaconEventLoop.centerPointArmorStand.getWorld()){ // Make sure they are in the same world
                if (event.getEntity().getLocation().distance(BeaconEventLoop.beaconEventLoop.centerPointArmorStand.getLocation()) < 12){
                    BeaconEventLoop.beaconEventLoop.SoulsNeeded--;
                    BeaconEventLoop.beaconEventLoop.centerPointArmorStand.setCustomName(ChatColor.LIGHT_PURPLE + "Souls Needed: " + BeaconEventLoop.beaconEventLoop.SoulsNeeded);
                    Vibration vibration = new Vibration(event.getEntity().getLocation(), new Vibration.Destination.EntityDestination(BeaconEventLoop.beaconEventLoop.centerPointArmorStand), 5);
                    event.getEntity().getWorld().spawnParticle(Particle.VIBRATION, event.getEntity().getLocation(), 1, vibration);
                }
            }
        }

        if (player != null){
            mainPlugin.PlayerScores.putIfAbsent(player, 0);

            mainPlugin.PlayerScores.put(player,mainPlugin.PlayerScores.get(player) + moneyValues.get(event.getEntity().getType()));
            ScoreboardHandler.updateScoreboard(mainPlugin);
            player.sendMessage(ChatColor.GREEN + "+" + moneyValues.get(event.getEntity().getType())+"$");
            //For supporters
            if (player.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(),"Supporter"),PersistentDataType.BOOLEAN)){
                player.sendMessage(ChatColor.GOLD + "+" + moneyValues.get(event.getEntity().getType())+"$");
                mainPlugin.PlayerScores.put(player,mainPlugin.PlayerScores.get(player) + moneyValues.get(event.getEntity().getType()));
            }
            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                talisman.onMobDeath(event);
            }
        }
    }
}
