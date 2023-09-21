package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class MobDamageByEntityListener implements Listener {
    public MobDamageByEntityListener(TheRoadPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }



    @EventHandler
    public void onMobDamagedByEntity(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player) return;
        Mob mobEntity = (Mob) event.getEntity();

        //For summons
        NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");
        if (mobEntity.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)) {
            if (event.getDamager() instanceof Player){
                event.setCancelled(true);
                Bukkit.broadcastMessage("Get cancelled nerd: " + mobEntity.getHealth());
                Bukkit.broadcastMessage(mobEntity.getTarget() + "");
            }
            else {
                mobEntity.setCustomName(ChatColor.BLUE + "[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
                mobEntity.setCustomNameVisible(true);
            }
        }
        else { // everything that isnt a summon
            mobEntity.setCustomName("[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
                if (event.getDamager().getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)){
                    Entity entity = event.getDamager();
                    if (entity instanceof Mob){
                        if (entity.getPersistentDataContainer().has(summonedKey, PersistentDataType.STRING)){
                            Player killer = Bukkit.getPlayer(entity.getPersistentDataContainer().get(summonedKey, PersistentDataType.STRING));

                            NamespacedKey killedByKey = new NamespacedKey(TheRoadPlugin.getInstance(), "killer");

                            mobEntity.getPersistentDataContainer().set(killedByKey,PersistentDataType.STRING, killer.getName());

                            Bukkit.broadcastMessage("Summon gots a hit");
                        }

                    }
                }
        }





        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();

        //Talisman
        for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
            talisman.onMobDamage(event);
        }
    }
}
