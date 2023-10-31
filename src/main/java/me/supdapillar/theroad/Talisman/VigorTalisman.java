package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VigorTalisman extends Talisman{

    public VigorTalisman(){
        name = "Vigor Talisman";
        price = 500;

        lores.add(ChatColor.LIGHT_PURPLE + "Hitting an enemy causes ");
        lores.add(ChatColor.LIGHT_PURPLE + "explosions to appear, and the ");
        lores.add(ChatColor.LIGHT_PURPLE + "the lower your health the ");
        lores.add(ChatColor.LIGHT_PURPLE + "bigger the explosion! ");


        inventoryIcon = new ItemStack(Material.DANGER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) return;
        if (!(event.getDamager() instanceof Player)) return;
            Player player = (Player) event.getDamager();

            event.getEntity().getWorld().createExplosion(event.getEntity().getLocation().add(0, 0.5, 0), (float) (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - player.getHealth())/20, false, false, player);

    }
}
