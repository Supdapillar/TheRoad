package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Healer extends GameClass {

    public Healer(TheRoadPlugin plugin) {
        super(plugin);
        className = "Healer";
        price = 0;
        representingClass = Classes.Healer;



        ItemStack healingStaff = new ItemStack(Material.ALLIUM);
        ItemMeta itemMeta = healingStaff.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Healing Staff");


        String[] healingLore = {ChatColor.GREEN + "[Left Click]" + ChatColor.WHITE + " for a healing beam!" + ChatColor.BLUE + " (1xp)", ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE +" for a healing aura!"+ ChatColor.BLUE+ " (3xp)"};
        itemMeta.setLore(Arrays.asList(healingLore));
        healingStaff.setItemMeta(itemMeta);


        classItems.add(new ItemStack(Material.STONE_SWORD));
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classItems.add(healingStaff);

        starterTalismans.add(new HealerClassTalisman());

        classArmor[0] = new ItemStack(Material.IRON_BOOTS);
        classArmor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        classArmor[3] = new ItemStack(Material.PEONY);


        //For icon
        ItemStack newItem = new ItemStack(Material.ALLIUM);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
    }


}