package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Date;
import java.util.List;

public class LootableReviveTalisman extends Talisman{

    private boolean canRevive = true;
    public LootableReviveTalisman(){
        countsAsActive = false;
        name = "Internal Revive";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "STILL SHOULDN'T SEE THIS");



        inventoryIcon = new ItemStack(Material.ACACIA_SAPLING);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
        countsAsActive = false;
    }


    @Override
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();

        if (player.getHealth() - event.getDamage() <= 0){
            if (canRevive){
                player.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "You Died! -1 Revive");
                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()/2);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 0, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 5, 2, true));
                player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation().add(0,0.5, 0), 25, 0.5, 1, 0.5, 0);
                canRevive = false;
            }
        }

    }
}
