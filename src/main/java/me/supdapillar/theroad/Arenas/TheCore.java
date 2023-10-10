package me.supdapillar.theroad.Arenas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TheCore extends Arena {

    public TheCore(){
        arenaName = "The Core";

        iconLore.add(ChatColor.BLUE + "The heart of the infection, buried ");
        iconLore.add(ChatColor.BLUE + "in the magmatic centre of the world. ");

        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Extreme");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 40 Rounds");

        spawnLocation = new Location(Bukkit.getWorld("SkyRoad"), -7.5, 10, -6.5 );
        finalRound = 20;

        ItemStack skyroadIcon = new ItemStack(Material.MAGMA_BLOCK);
        ItemMeta skyroadIconMeta = skyroadIcon.getItemMeta();

        skyroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        skyroadIconMeta.setLore(iconLore);

        skyroadIcon.setItemMeta(skyroadIconMeta);

        inventoryIcon = skyroadIcon;
    }
}
