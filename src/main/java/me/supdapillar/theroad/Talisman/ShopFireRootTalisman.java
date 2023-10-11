package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopFireRootTalisman extends Talisman{

    private double AttacksRemaining = 30;

    public ShopFireRootTalisman(){
        countsAsActive = false;
        name = "fire root";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "you musn't see ");

        inventoryIcon = new ItemStack(Material.ORANGE_CANDLE);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (AttacksRemaining > 0){
            event.getEntity().setFireTicks(99999);
            AttacksRemaining--;
            if (AttacksRemaining == 0){
                event.getDamager().sendMessage(ChatColor.RED + "Fire root wore out!");
            }
        }
    }
}
