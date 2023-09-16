package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SummonerClassTalisman extends Talisman{

    public SummonerClassTalisman(){
        name = "You shouldn't see this";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "STILL SHOULNDT SEE THIS");

        inventoryIcon = new ItemStack(Material.CACTUS);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        ItemStack silverFishSpawner = new ItemStack(Material.SILVERFISH_SPAWN_EGG);
        ItemMeta silverFishMeta = silverFishSpawner.getItemMeta();
        silverFishMeta.setDisplayName("Silverfish Summoner");

        silverFishSpawner.setItemMeta(silverFishMeta);
        event.getEntity().getKiller().getInventory().addItem(silverFishSpawner);
    }
}
