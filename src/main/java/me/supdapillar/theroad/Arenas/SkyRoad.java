package me.supdapillar.theroad.Arenas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SkyRoad extends Arena {

    public SkyRoad(){
        arenaName = "Sky Road";

        iconLore.add(ChatColor.BLUE + "A torn city bound to the sky ");
        iconLore.add(ChatColor.BLUE + "populated now with only the infected. ");

        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Medium");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 20 Rounds");

        spawnLocation = new Location(Bukkit.getWorld("SkyRoad"), -7.5, 10, -6.5 );
        finalRound = 21;

        ItemStack skyroadIcon = new ItemStack(Material.WHITE_WOOL);
        ItemMeta skyroadIconMeta = skyroadIcon.getItemMeta();

        skyroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        skyroadIconMeta.setLore(iconLore);

        skyroadIcon.setItemMeta(skyroadIconMeta);
        victoryCash = 125;
        inventoryIcon = skyroadIcon;
    }
}
