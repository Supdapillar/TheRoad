package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Tasks.ShieldTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ShopEchoShieldTalisman extends Talisman{



    private boolean isUsed = false;
    public List<ShieldTimer> currentActiveTimers = new ArrayList<>();
    public ShopEchoShieldTalisman(){
        countsAsActive = false;

        name = "Echo Shield";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "You're eyes don't belong here traveler ");


        inventoryIcon = new ItemStack(Material.ECHO_SHARD);
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
                    Mob mob = (Mob) event.getDamager();
                    Vector vector = new Vector(mob.getLocation().subtract(player.getLocation()).getX(), 0.75f, mob.getLocation().subtract(player.getLocation()).getZ());
                    mob.setVelocity(vector.normalize().multiply(0.8));

                    mob.getWorld().spawnParticle(Particle.SPELL_WITCH, mob.getLocation().add(0,0.5,0), 24, 0.5,1, 0.5, 0);

                    mob.damage(event.getDamage(), player);
                    mob.getWorld().playSound(player, Sound.ENTITY_FOX_TELEPORT,9, 1.2f);
                }
            }
        }

    }
}
