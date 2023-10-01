package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
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

        if (event.getEntity() instanceof Player)
        {
            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get((Player) event.getEntity())){
                talisman.onPlayerDamage(event);
            }



            if (event.getDamager() instanceof WitherSkull ){
                event.setCancelled(true);
            }
            return;
        }

        if (!(event.getEntity() instanceof Mob)) return;

        Mob mobEntity = (Mob) event.getEntity();

        //For Wolves
        if (mobEntity instanceof Tameable) {
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
        //For bosses
        else if (mobEntity.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(), "IsBoss"),PersistentDataType.BOOLEAN)){
            mobEntity.setCustomName(ChatColor.WHITE + "Sky Guardian" + "[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }
        else { // everything that isnt a boss

            mobEntity.setCustomName("[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }





        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();

        //Talisman
        for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
            talisman.onMobDamage(event);
        }
    }
}
