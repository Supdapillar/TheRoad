package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MobSpawnListener implements Listener {

    public MobSpawnListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if (!(event.getEntity() instanceof Mob)) return;
        if (event.getEntity() instanceof Player) return;
        if (event.getEntity() instanceof ArmorStand) return;
        Mob mobEntity = (Mob) event.getEntity();


        //For summons
        if (TheRoadPlugin.getInstance().nextMobIsSummoned){
                mobEntity.setCustomName(ChatColor.BLUE + "[" + Math.round(mobEntity.getHealth()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
                mobEntity.setCustomNameVisible(true);
                TheRoadPlugin.getInstance().nextMobIsSummoned = false;
                //Trys to set the target if there is a living entity for it to target,
                //or it becomes a cripple

                NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");

            List<Entity> attackableList = new ArrayList<>();

            for (Entity o : mobEntity.getNearbyEntities(20, 5, 20)) {
                if (!(o instanceof HumanEntity)
                        && (o instanceof Mob)
                        && !(o.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)
                        && !(o == mobEntity))) {
                    attackableList.add(o);
                }
            }

            //Set the target
            if (!attackableList.isEmpty() ){
                mobEntity.setTarget((LivingEntity) attackableList.get(0));
                Bukkit.broadcastMessage("Target adquired");
            }
        }
        else {
            mobEntity.setCustomName("[" + Math.round(mobEntity.getHealth()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
            //Get all inactive summons and make them target the new spawn
            NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");

            for (LivingEntity livingEntity : mobEntity.getWorld().getLivingEntities()){
                if (livingEntity instanceof Mob) {
                    Mob mob = (Mob) livingEntity;

                    if (mob.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)){
                        if (mob.getTarget() == null){
                            mob.setTarget(mobEntity);
                            Bukkit.broadcastMessage("TARGET ADQUIRED");
                        }
                    }
                }
            }
        }
    }
}
