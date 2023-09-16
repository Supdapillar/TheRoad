package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.List;

public class SkullTalisman extends Talisman{

    public SkullTalisman(){
        name = "Skull Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "The faster you move the higher ");
        lores.add(ChatColor.LIGHT_PURPLE + "chance you have of launching ");
        lores.add(ChatColor.LIGHT_PURPLE + "a skull at the nearest enemy! ");


        inventoryIcon = new ItemStack(Material.SKULL_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location from = new Location(event.getPlayer().getWorld(), event.getFrom().getX(), event.getTo().getY(), event.getFrom().getZ());
        Location to = new Location(event.getPlayer().getWorld(), event.getTo().getX(), event.getTo().getY(), event.getTo().getZ());
        double randomDouble = (from.distance(to) + (Math.random()*5));

            if (randomDouble > 5f){
                //Spawning the skull
                Object[] array = player.getWorld().getEntities().stream().filter(o -> o instanceof LivingEntity && !(o instanceof Player)).toArray();

                if (!(array.length < 1.25)){

                    LivingEntity ClosestEnemy = (LivingEntity) array[0];
                    for (Entity entity : player.getWorld().getEntities()){
                        if (entity instanceof LivingEntity){
                            if (!(entity instanceof HumanEntity)){
                                if (player.getLocation().distance(entity.getLocation()) < player.getLocation().distance(ClosestEnemy.getLocation())){
                                    ClosestEnemy = (LivingEntity) entity;
                                }
                            }
                        }
                    }

                    WitherSkull witherSkull = player.launchProjectile(WitherSkull.class);
                    witherSkull.setIsIncendiary(true);
                    witherSkull.setBounce(true);

                }
            }
        }
    }
