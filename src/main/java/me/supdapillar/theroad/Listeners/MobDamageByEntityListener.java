package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.Tasks.CrystalSpellAttackTimer;
import me.supdapillar.theroad.Tasks.RootSpellAttackTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class MobDamageByEntityListener implements Listener {
    public MobDamageByEntityListener(TheRoadPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }



    @EventHandler
    public void onMobDamagedByEntity(EntityDamageByEntityEvent event){

        //All stuff related to players
        if (event.getEntity() instanceof Player)
        {
            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get((Player) event.getEntity())){
                talisman.onPlayerDamage(event);
            }


            //Player wither skull bug fix
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
        else { // everything that isn't a boss

            mobEntity.setCustomName("[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }




        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();

        //Mage attacks
        if (TheRoadPlugin.getInstance().PlayerClass.get(player) == Classes.Mage){
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                switch (player.getInventory().getItemInMainHand().getType()){
                    case AMETHYST_SHARD:
                        if (player.getAttackCooldown() == 1){
                            new CrystalSpellAttackTimer(event).runTaskLater(TheRoadPlugin.getInstance(), 1);
                        }
                        break;
                    case BLAZE_ROD:
                        event.getEntity().getWorld().spawnParticle(Particle.FLAME, event.getEntity().getLocation().add(0,0.5,0), 12, 0.5, 1, 0.5, 0);
                        event.getEntity().setFireTicks(9999);
                        break;
                    case MANGROVE_PROPAGULE:
                        new RootSpellAttackTimer(mobEntity).runTaskTimer(TheRoadPlugin.getInstance(),0, 10);
                        break;

                }
            }
        }



        //Talisman
        for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
            talisman.onMobDamage(event);
        }
    }
}
