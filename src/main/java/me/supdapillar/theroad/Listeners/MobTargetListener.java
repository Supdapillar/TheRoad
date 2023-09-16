package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MobTargetListener implements Listener {
    public MobTargetListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onMobTarget(EntityTargetEvent event){
        if (!(event.getEntity() instanceof Mob)) return;
        Mob mobEntity = (Mob) event.getEntity();
        NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");
        //Checks to see if the mob is a summoned one
        if (mobEntity.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)){
            if (event.getTarget() instanceof HumanEntity){
                event.setCancelled(true);

                //Set target
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
                    event.setTarget(attackableList.get(0));
                }
            }
        }





    }
}
