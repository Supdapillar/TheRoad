package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;
import java.util.List;

public class AgonyTalisman extends Talisman{

    public AgonyTalisman(){
        name = "Agony Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "When the player heals it ");
        lores.add(ChatColor.LIGHT_PURPLE + "damages enemies around them! ");


        inventoryIcon = new ItemStack(Material.HEARTBREAK_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
    @Override
    public void onPlayerHealthRegain(EntityRegainHealthEvent event){
        Player player = (Player) event.getEntity();


        List<LivingEntity> livingEntities = player.getWorld().getLivingEntities();

        for (LivingEntity livingEntity : livingEntities){
            if (!(livingEntity instanceof HumanEntity)){
                //No damage summons
                if (livingEntity.getLocation().distance(player.getLocation()) < 5){
                    livingEntity.damage(event.getAmount(), player);
                    player.spawnParticle(Particle.DAMAGE_INDICATOR, livingEntity.getLocation(), 20, 0.5 ,0.5 ,0.5 ,0);
                }
            }

        }

    }

}
