package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.enums.Classes;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CritTalisman extends Talisman{

    public CritTalisman(){
        name = "Crit Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "Hits have a 25% chance of ");
        lores.add(ChatColor.LIGHT_PURPLE + "a critical hit dealing 2x damage!");

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if ((int)(Math.random()*4) == 0){
            Player player = (Player) event.getDamager();
            event.setDamage(event.getDamage()*2);
            player.sendMessage(ChatColor.GOLD + "CRITICAL HIT " + event.getDamage());
            player.playSound(player.getLocation(),Sound.BLOCK_NOTE_BLOCK_CHIME,9999, 1 );
            player.getWorld().spawnParticle(Particle.WAX_ON, event.getEntity().getLocation(), 200, 0.5f ,1 ,0.5 ,0);

            LivingEntity livingEntity = (LivingEntity) event.getEntity();

            livingEntity.setCustomName("[" + Math.ceil(livingEntity.getHealth() - event.getDamage()) + "❤/" + livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            livingEntity.setCustomNameVisible(true);

        }
    }
}
