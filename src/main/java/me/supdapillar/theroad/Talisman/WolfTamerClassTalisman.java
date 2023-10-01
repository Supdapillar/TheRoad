package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WolfTamerClassTalisman extends Talisman{

    public WolfTamerClassTalisman(){
        name = "You shouldn't see this";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "STILL SHOULDN'T SEE THIS");

        inventoryIcon = new ItemStack(Material.CACTUS);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        ItemStack wolfSpawner = new ItemStack(Material.WOLF_SPAWN_EGG);
        ItemMeta wolfMeta = wolfSpawner.getItemMeta();
        wolfMeta.setDisplayName(ChatColor.GREEN + "Wolf Summoner");

        wolfSpawner.setItemMeta(wolfMeta);
        event.getEntity().getKiller().getInventory().addItem(wolfSpawner);
    }
}
