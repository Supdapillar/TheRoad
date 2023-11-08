package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassExperienceTalisman extends Talisman{

    public ClassExperienceTalisman(){
        countsAsActive = false;
        name = "Internal Experience";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "STILL SHOULDN'T SEE THIS");

        inventoryIcon = new ItemStack(Material.CACTUS);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        player.setLevel(player.getLevel()+1);
    }
}
