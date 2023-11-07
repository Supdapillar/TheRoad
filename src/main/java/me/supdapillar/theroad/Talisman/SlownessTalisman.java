package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class SlownessTalisman extends Talisman{

    public SlownessTalisman(){
        name = "Slowness Talisman";
        price = 100;
        lores.add(ChatColor.LIGHT_PURPLE + "Every hit an enemy takes ");
        lores.add(ChatColor.LIGHT_PURPLE + "makes them move 10% slower!");

        inventoryIcon = new ItemStack(Material.SNORT_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!event.getEntity().getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(), "BossName"), PersistentDataType.STRING)){
            LivingEntity entity = (LivingEntity) event.getEntity();
            entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).
                setBaseValue(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()*0.9f);
                entity.getWorld().spawnParticle(Particle.BLOCK_CRACK,entity.getEyeLocation(),12, Material.ICE.createBlockData());

        }
    }
}
