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
        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Insane");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 300 Rounds");


        spawnLocation = new Location(Bukkit.getWorld("HauntedRoad"), 4.5, -59, 1.5 );

        ItemStack hauntedroadIcon = new ItemStack(Material.COBWEB);
        ItemMeta hauntedroadIconMeta = hauntedroadIcon.getItemMeta();

        hauntedroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        hauntedroadIconMeta.setLore(iconLore);

        hauntedroadIcon.setItemMeta(hauntedroadIconMeta);

        inventoryIcon = hauntedroadIcon;
    }
    //Inventory Stuff

    //Ingame stuff
    public Location spawnLocation;
    public int finalRound;
}
