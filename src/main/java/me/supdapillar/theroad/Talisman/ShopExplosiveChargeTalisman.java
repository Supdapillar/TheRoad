package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class ShopExplosiveChargeTalisman extends Talisman{

    private boolean isUsed = false;

    public ShopExplosiveChargeTalisman(){
        countsAsActive = false;
        name = "explosive charge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "you musn't see ");

        inventoryIcon = new ItemStack(Material.BLUE_CANDLE);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }

    @Override
    public void onPlayerDamage(EntityDamageByEntityEvent event){

        Player player = (Player) event.getEntity();
        if (!isUsed){
            //Checks to make sure it does activate with multiple talisman active
            if (!event.isCancelled()){
                if (event.getDamager() instanceof Mob){
                    isUsed = true;
                    event.setCancelled(true);
                    player.getWorld().createExplosion(event.getEntity().getLocation().add(0, 0.5, 0), (float) 4, false, false, player);
                    player.getWorld().spawnParticle(Particle.SMALL_FLAME,player.getLocation(),80,5,5,5,0.25f);
                }
            }
        }
    }
}
