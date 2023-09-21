package me.supdapillar.theroad.Arenas;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HauntedRoad extends Arena {

    public HauntedRoad(){
        arenaName = "Haunted Road";
        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Insane");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 300 Rounds");


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
