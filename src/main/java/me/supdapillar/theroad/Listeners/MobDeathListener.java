package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.CritTalisman;
import me.supdapillar.theroad.Talisman.Talisman;
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
        put(EntityType.SKELETON, 10);
        put(EntityType.WITHER_SKELETON, 15);
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


        //Retargeting any summon who is locked on to the dead mob
        NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");


        for (Entity entity : event.getEntity().getNearbyEntities(20, 5, 20)) {
            if (entity instanceof Mob){
                Mob mob = (Mob) entity;
                if (mob.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)){

                    //The mob that needs to be retargeted
                    if (mob.getTarget() == event.getEntity() ){

                        List<Entity> attackableList = new ArrayList<>();
                        //List of entities its allowed to target
                        for (Entity o : event.getEntity().getNearbyEntities(20, 5, 20)) {
                            if (!(o instanceof HumanEntity)
                                    && (o instanceof Mob)
                                    && !(o.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)
                                    && !(o == event.getEntity()))) {
                                attackableList.add(o);
                            }
                        }

                        if (!attackableList.isEmpty()) {
                            mob.setTarget((LivingEntity) attackableList.get(0));
                        }
                    }
                }
            }
        }

        //Check to see if the mob died in the area of beacon event
        if (TheRoadPlugin.getInstance().respawnBeaconActive){
            if (event.getEntity().getWorld() == TheRoadPlugin.getInstance().beaconEventLoop.centerPointArmorStand.getWorld()){ // Make sure they are in the same world
                if (event.getEntity().getLocation().distance(TheRoadPlugin.getInstance().beaconEventLoop.centerPointArmorStand.getLocation()) < 12){
                    TheRoadPlugin.getInstance().beaconEventLoop.SoulsNeeded--;
                    TheRoadPlugin.getInstance().beaconEventLoop.centerPointArmorStand.setCustomName(ChatColor.LIGHT_PURPLE + "Souls Needed: " + TheRoadPlugin.getInstance().beaconEventLoop.SoulsNeeded);
                    Vibration vibration = new Vibration(event.getEntity().getLocation(), new Vibration.Destination.EntityDestination(TheRoadPlugin.getInstance().beaconEventLoop.centerPointArmorStand), 5);
                    event.getEntity().getWorld().spawnParticle(Particle.VIBRATION, event.getEntity().getLocation(), 1, vibration);
                }
            }
        }

        if (player != null){
            mainPlugin.PlayerScores.putIfAbsent(player, 0);

            mainPlugin.PlayerScores.put(player,mainPlugin.PlayerScores.get(player) + moneyValues.get(event.getEntity().getType()));
            ScoreboardHandler.updateScoreboard(mainPlugin);
            player.sendMessage(ChatColor.GREEN + "+" + moneyValues.get(event.getEntity().getType())+"$");
            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                talisman.onMobDeath(event);
            }
        }
    }
}
