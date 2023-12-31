package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Tasks.FrostTalismanUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.Random;

public class FrostTalisman extends Talisman{

    public FrostTalisman(){
        name = "Frost Talisman";
        price = 300;
        lores.add(ChatColor.LIGHT_PURPLE + "When an enemy gets killed ");
        lores.add(ChatColor.LIGHT_PURPLE + "it freezes the enemies around them! ");


        inventoryIcon = new ItemStack(Material.FRIEND_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity mainEntity = event.getEntity();
        double Angle = 0;
        for(int i = 0; i < 100; i++){
            Angle -= Math.PI/50f;;

            Location EntityLocation = mainEntity.getLocation();
            Location particleLocation = new Location(mainEntity.getWorld(), EntityLocation.getX() + (Math.cos(Angle) * 4f), EntityLocation.getY()+0.3, EntityLocation.getZ()+ (Math.sin(Angle) * 4f));
            Random random = new Random();
            Particle.DustOptions dustOptions = null;
            switch (random.nextInt(4)){
                case 0:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(231, 237, 235),2.5f);
                    break;
                case 1:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(142, 206, 206),2.5f);
                    break;
                case 2:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(98, 161, 199),2.5f);
                    break;
                case 3:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(63, 110, 204),2.5f);
                    break;

            }

            mainEntity.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 3,0,0.3,0, dustOptions);


        }

        for(Entity entity : mainEntity.getNearbyEntities(5,5,5)){
            if (entity instanceof Mob){
                if (entity.getLocation().distance(mainEntity.getLocation()) < 4){
                    Mob mob = (Mob) entity;
                    //mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.00);
                    new FrostTalismanUpdater(mob).runTaskTimer(TheRoadPlugin.getInstance(), 0, 10);
                    for (int i = 0; i< 5; i++){
                        mob.getWorld().spawnParticle(Particle.BLOCK_CRACK,mob.getEyeLocation().add(0,-0.5,0),8,0.5,1,0.5, Material.ICE.createBlockData());

                    }
                }
            }
        }

    }

}
