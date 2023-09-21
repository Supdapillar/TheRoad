package me.supdapillar.theroad.Arenas;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class SkyRoad extends Arena {

    public SkyRoad(){
        arenaName = "Sky Road";
        iconLore.add(ChatColor.LIGHT_PURPLE + "Difficulty: Medium");
        iconLore.add(ChatColor.LIGHT_PURPLE + "Length: 30 Rounds");


        ItemStack skyroadIcon = new ItemStack(Material.WHITE_WOOL);
        ItemMeta skyroadIconMeta = skyroadIcon.getItemMeta();

        skyroadIconMeta.setDisplayName(ChatColor.BOLD + arenaName);
        skyroadIconMeta.setLore(iconLore);

        skyroadIcon.setItemMeta(skyroadIconMeta);

        inventoryIcon = skyroadIcon;
    }



    //Inventory Stuff

    //Ingame stuff
    public Location spawnLocation;
    public int finalRound;
}
