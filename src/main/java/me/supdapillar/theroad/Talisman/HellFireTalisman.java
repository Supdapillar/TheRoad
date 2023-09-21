package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.List;

public class HellFireTalisman extends Talisman{

    public HellFireTalisman(){
        name = "Hellfire Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "When an enemy takes damage ");
        lores.add(ChatColor.LIGHT_PURPLE + "it sets all nearby creatures ");
        lores.add(ChatColor.LIGHT_PURPLE + "ablaze! ");


        inventoryIcon = new ItemStack(Material.BURN_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }


    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player) {
            Entity damagedEntity = event.getEntity();

            List<LivingEntity> livingEntities = damagedEntity.getWorld().getLivingEntities();

            Location pLocation = damagedEntity.getLocation();
            double Angle = 0;
            for(int i = 0; i < 40; i++){
                Angle -= Math.PI/20f + new Date(System.currentTimeMillis()).getTime()*80;;

                Location particleLocation = new Location(damagedEntity.getWorld(), pLocation.getX() + (Math.cos(Angle) * 2.5f), pLocation.getY()+0.25f, pLocation.getZ()+ (Math.sin(Angle) * 2.5f));
                damagedEntity.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, particleLocation, 1, 0 ,0.25f ,0 ,0);
            }


            for (LivingEntity livingEntity : livingEntities){
                if (livingEntity != damagedEntity && !(livingEntity instanceof Player)){
                    if (livingEntity.getLocation().distance(damagedEntity.getLocation()) < 2.5f){
                        livingEntity.setFireTicks(livingEntity.getFireTicks() + 40);
                        Bukkit.broadcastMessage("I set this ablaze: " + livingEntity);
                    }
                }
            }
        }
    }
}
