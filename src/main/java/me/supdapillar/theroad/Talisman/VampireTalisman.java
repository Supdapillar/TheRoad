package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class VampireTalisman extends Talisman{

    public VampireTalisman(){
        name = "Vampire Talisman";
        price = 350;
        lores.add(ChatColor.LIGHT_PURPLE + "Hitting an enemy causes ");
        lores.add(ChatColor.LIGHT_PURPLE + "the player to regain health! ");


        inventoryIcon = new ItemStack(Material.HEART_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof LivingEntity)) return;
        Player player = (Player) event.getDamager();

        if (player.getHealth()+0.5f >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())
        {
            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }
        else
        {
            player.setHealth(player.getHealth()+0.5f);

        }
        event.getEntity().getWorld().spawnParticle(Particle.HEART, player.getLocation(), 5, 0.5, 1, 0.5);

    }
}
