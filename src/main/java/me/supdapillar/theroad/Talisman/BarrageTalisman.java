package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Tasks.BarrageTimer;
import me.supdapillar.theroad.Tasks.ShieldTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarrageTalisman extends Talisman{
    public static List<BarrageTimer> currentActiveTimers = new ArrayList<>();

    public BarrageTalisman(){
        name = "Barrage Talisman";
        price = 250;
        lores.add(ChatColor.LIGHT_PURPLE + "Hitting an enemy has a 33% ");
        lores.add(ChatColor.LIGHT_PURPLE + "chance to cause the enemy to ");
        lores.add(ChatColor.LIGHT_PURPLE + "take multiple hits over time! ");


        inventoryIcon = new ItemStack(Material.ANGLER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDamage(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof LivingEntity)) return;
        Player player = (Player) event.getDamager();

        if (Math.random() > 0.66f){
            if (event.getEntity() instanceof Mob){
                if (currentActiveTimers.stream().filter(o -> o.Victim == event.getEntity()).toArray().length == 0)
                {
                    BarrageTimer barrageTimer = new BarrageTimer(player, (Mob) event.getEntity());
                    barrageTimer.runTaskTimer(TheRoadPlugin.getInstance(),0, 10);
                    currentActiveTimers.add(barrageTimer);
                }

            }
        }


    }
}
