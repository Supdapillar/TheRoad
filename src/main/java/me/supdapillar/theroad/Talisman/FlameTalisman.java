package me.supdapillar.theroad.Talisman;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FlameTalisman extends Talisman{

    public FlameTalisman(){
        name = "Flame Talisman";
        price = 50;
        lores.add(ChatColor.LIGHT_PURPLE + "SOMTHING TO DO WITH BURNING ENEMIES");

        inventoryIcon = new ItemStack(Material.BURN_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
}
