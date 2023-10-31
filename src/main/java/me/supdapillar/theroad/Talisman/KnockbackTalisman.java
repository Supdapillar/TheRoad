package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class KnockbackTalisman extends Talisman{

    public KnockbackTalisman(){
        name = "Knockback Talisman";
        price = 100;
        lores.add(ChatColor.LIGHT_PURPLE + "Killing an enemy causes all ");
        lores.add(ChatColor.LIGHT_PURPLE + "nearby enemies to get ");
        lores.add(ChatColor.LIGHT_PURPLE + "launched back! ");


        inventoryIcon = new ItemStack(Material.MOURNER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
    @Override
    public void onMobDeath(EntityDeathEvent event){
        LivingEntity victim = event.getEntity();
        Player player = victim.getKiller();

        List<Entity> nearbyEntities = player.getNearbyEntities(10, 5, 10);
        List<LivingEntity> nearbyLivingEntities = new ArrayList<>();
        for(Entity entity : nearbyEntities){
            if (!(entity instanceof Player) && (entity instanceof LivingEntity)){
                nearbyLivingEntities.add((LivingEntity) entity);
            }
        }
        for(LivingEntity livingEntity : nearbyLivingEntities){
            Vector vector = new Vector(livingEntity.getLocation().subtract(victim.getLocation()).getX(), 0.75f, livingEntity.getLocation().subtract(victim.getLocation()).getZ());
            livingEntity.setVelocity(vector.normalize().multiply(0.8));
        }
    }
}
