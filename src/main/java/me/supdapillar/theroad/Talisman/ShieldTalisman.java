package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Tasks.ShieldTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ShieldTalisman extends Talisman{

    public List<ShieldTimer> currentActiveTimers = new ArrayList<>();
    public ShieldTalisman(){
        name = "Shield Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "Every 10 seconds you charge ");
        lores.add(ChatColor.LIGHT_PURPLE + "a shield that can block enemy damage! ");


        inventoryIcon = new ItemStack(Material.SHELTER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }


    @Override
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();
        //Shield is recharged and will get used
        if (!currentActiveTimers.stream().anyMatch(o -> o.shieldOwner == player)){

            Entity entity = event.getDamager();
            //KnocksBack the damamger
            Vector vector = new Vector(entity.getLocation().subtract(player.getLocation()).getX(), 0.75f, player.getLocation().subtract(player.getLocation()).getZ());
            entity.setVelocity(vector.normalize().multiply(0.8));

            //Particles
            for(int i = 0; i < 10; i++){
                player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().add(0,0.5,0), 5,0.5,1,0.5, new Particle.DustOptions(Color.BLUE, (float) (2+Math.random())));
            }
            player.sendMessage(ChatColor.GRAY + "Shield Broken!");
            player.playSound(player, Sound.ITEM_SHIELD_BREAK, 9999, 1);
            ShieldTimer shieldTimer = new ShieldTimer(player, this);
            shieldTimer.runTaskLater(TheRoadPlugin.getInstance(), 200   );
            currentActiveTimers.add(shieldTimer);
            event.setCancelled(true);

        }

    }
}
