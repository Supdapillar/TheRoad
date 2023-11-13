package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class InfernoTalisman extends Talisman{

    public InfernoTalisman(){
        name = "Inferno Talisman";
        price = 500;
        lores.add(ChatColor.LIGHT_PURPLE + "Create an inferno while ");
        lores.add(ChatColor.LIGHT_PURPLE + "walking, burning all enemies ");
        lores.add(ChatColor.LIGHT_PURPLE + "in the range! ");


        inventoryIcon = new ItemStack(Material.SHEAF_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        List<LivingEntity> livingEntities = player.getWorld().getLivingEntities();

        Location pLocation = player.getLocation();
        double Angle = 0;
        for(int i = 0; i < 25; i++){
            Angle -= Math.PI/12.5f + new Date(System.currentTimeMillis()).getTime()*80;

            Location particleLocation = new Location(player.getWorld(), pLocation.getX() + (Math.cos(Angle) * 3.75f), pLocation.getY(), pLocation.getZ()+ (Math.sin(Angle) * 3.75f));
            player.getWorld().spawnParticle(Particle.FLAME, particleLocation, 1, 0 ,0 ,0 ,0);
        }


        for (LivingEntity livingEntity : livingEntities){
            if (livingEntity.getLocation().distance(player.getLocation()) < 3.75){
                livingEntity.setFireTicks(livingEntity.getFireTicks() + 15);
                livingEntity.damage(0, player);
                livingEntity.setNoDamageTicks(1);
            }
        }
    }
}
