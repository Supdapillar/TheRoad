package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Archer extends GameClass {

    public Archer(TheRoadPlugin plugin) {
        super(plugin);
        className = "Archer";
        price = 0;
        representingClass = Classes.Archer;

        classItems.add(new ItemStack(Material.WOODEN_SWORD));
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1,true);
        bow.setItemMeta(bowMeta);

        classItems.add(bow);
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classItems.add(new ItemStack(Material.ARROW));

        classArmor[0] = new ItemStack(Material.LEATHER_BOOTS);
        classArmor[1] = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        classArmor[3] = new ItemStack(Material.CHAINMAIL_HELMET);

        ItemStack newItem = new ItemStack(Material.BOW);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;

    }


}