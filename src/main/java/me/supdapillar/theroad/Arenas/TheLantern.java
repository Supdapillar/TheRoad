package me.supdapillar.theroad.Arenas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TheLantern extends Arena {

    public TheLantern(){
        arenaName = "The Lantern";

        iconLore.add(ChatColor.BLUE + "The heart of all knowledge, may ");
        iconLore.add(ChatColor.BLUE + "it act as your beacon of hope. ");
        iconLore.add(ChatColor.BLUE + "[Tutorial]. ");

        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Easy");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 10 Rounds");

        spawnLocation = new Location(Bukkit.getWorld("SkyRoad"), -7.5, 10, -6.5 );
        finalRound = 20;

        ItemStack skyroadIcon = new ItemStack(Material.SOUL_LANTERN);
        ItemMeta skyroadIconMeta = skyroadIcon.getItemMeta();

        skyroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        skyroadIconMeta.setLore(iconLore);

        skyroadIcon.setItemMeta(skyroadIconMeta);

        inventoryIcon = skyroadIcon;
    }
}
