package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class ArrowTalisman extends Talisman{

    public ArrowTalisman(){
        name = "Arrow Talisman";
        price = 150;
        lores.add(ChatColor.LIGHT_PURPLE + "Killing an enemy causes a ");
        lores.add(ChatColor.LIGHT_PURPLE + "arrow to shoot out at the ");
        lores.add(ChatColor.LIGHT_PURPLE + "nearest enemy!");


        inventoryIcon = new ItemStack(Material.ARCHER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
    @Override
    public void onMobDeath(EntityDeathEvent event){
        if (event.getEntity().getKiller() == null) return;
        LivingEntity victim = event.getEntity();
        Player player = victim.getKiller();

        Object[] array = victim.getNearbyEntities(20,20,20).stream().filter(o -> !(o instanceof Tameable) && !(o instanceof ArmorStand)&& (o instanceof LivingEntity) && !(o instanceof Player)).toArray();

        if (!(array.length < 1)){
            LivingEntity ClosestEnemy = (LivingEntity) array[0];
            for (Entity entity : victim.getNearbyEntities(20,20,20)){
                if (entity instanceof LivingEntity){
                    if (entity != event.getEntity()){
                        if (!(entity instanceof HumanEntity || entity instanceof Tameable || entity instanceof ArmorStand)){
                            if (victim.getLocation().distance(entity.getLocation()) < victim.getLocation().distance(ClosestEnemy.getLocation())){
                                ClosestEnemy = (LivingEntity) entity;
                            }
                        }
                    }
                }
            }

            Vector vector = new Vector(ClosestEnemy.getLocation().subtract(victim.getLocation()).getX(), ClosestEnemy.getLocation().subtract(victim.getLocation()).getY(), ClosestEnemy.getLocation().subtract(victim.getLocation()).getZ());
            Arrow arrow = event.getEntity().getLocation().getWorld().spawnArrow(victim.getEyeLocation(), vector.normalize(), 7, 0);
            arrow.setShooter(player);

            //Spawn particles
            for(int i = 0; i < (int)victim.getLocation().distance(ClosestEnemy.getLocation()) * 4; i++){
                Location particleLocation = new Location(victim.getWorld(), victim.getEyeLocation().getX() + vector.normalize().getX() * i/4, victim.getEyeLocation().getY() +  vector.normalize().getY() * i/4,victim.getEyeLocation().getZ() +  vector.normalize().getZ() * i/4);
                victim.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,1, new Particle.DustOptions(Color.WHITE, 1));
            }
        }
    }
}
