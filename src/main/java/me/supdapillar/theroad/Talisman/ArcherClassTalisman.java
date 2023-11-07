package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArcherClassTalisman extends Talisman{

    public ArcherClassTalisman(){
        countsAsActive = false;
        name = "Internal Archer";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "STILL SHOULDN'T SEE THIS");

        inventoryIcon = new ItemStack(Material.CACTUS);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);
    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.ARROW,1));
    }
}
