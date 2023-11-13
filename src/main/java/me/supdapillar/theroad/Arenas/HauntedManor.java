package me.supdapillar.theroad.Arenas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HauntedManor extends Arena {

    public HauntedManor(){
        arenaName = "Haunted Manor";

        iconLore.add(ChatColor.BLUE + "Surrounded by dead trees with windows that leave shadows dancing in your eyes, ");
        iconLore.add(ChatColor.BLUE + "this map will lead you down a winding road of undead fun.");

        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Medium");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 20 Rounds");

        spawnLocation = new Location(Bukkit.getWorld("HauntedRoad"), -185.5, -52, 188 );
        finalRound = 21;

        ItemStack hauntedroadIcon = new ItemStack(Material.COBWEB);
        ItemMeta hauntedroadIconMeta = hauntedroadIcon.getItemMeta();

        hauntedroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        hauntedroadIconMeta.setLore(iconLore);

        hauntedroadIcon.setItemMeta(hauntedroadIconMeta);
        victoryCash = 125;
        inventoryIcon = hauntedroadIcon;
    }
}
