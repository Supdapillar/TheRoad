package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Swordsman extends GameClass {

    public Swordsman(TheRoadPlugin plugin) {
        super(plugin);
        className = "Swordsman";
        price = 50;
        representingClass = Classes.Swordsman;

        classItems.add(new ItemStack(Material.IRON_SWORD));
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.CHAINMAIL_BOOTS);
        classArmor[1] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        classArmor[2] = new ItemStack(Material.IRON_CHESTPLATE);
        classArmor[3] = new ItemStack(Material.CHAINMAIL_HELMET);

        ItemStack newItem = new ItemStack(Material.STONE_SWORD);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
    }


}