package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Tasks.ShieldTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MendingTalisman extends Talisman{

    public MendingTalisman(){
        name = "Mending Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "All healing also gives the ");
        lores.add(ChatColor.LIGHT_PURPLE + "player absorption hearts! ");


        inventoryIcon = new ItemStack(Material.MINER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }


    @Override
    public void onPlayerHealthRegain(EntityRegainHealthEvent event){
        Player player = (Player) event.getEntity();
        double pastAbsorption = player.getAbsorptionAmount();

        //player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 9999999, 255, true));
        player.getAttribute(Attribute.GENERIC_MAX_ABSORPTION).setBaseValue(100);
        player.setAbsorptionAmount(pastAbsorption + event.getAmount());

        player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0,0.5,0), 24,0.5,1,0.5, new Particle.DustOptions(Color.YELLOW, (float) (1+Math.random())));
        player.sendMessage(ChatColor.GOLD + "+" + event.getAmount()/2 + "♥");
    }
}
