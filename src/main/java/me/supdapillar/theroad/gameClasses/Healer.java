package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.ClassExperienceTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Healer extends GameClass {

    public Healer(TheRoadPlugin plugin) {
        super(plugin);
        className = "Healer";
        iconLore.add(ChatColor.BLUE + "Through the magic of nature, the healer");
        iconLore.add(ChatColor.BLUE + "is able to mend their allies' wounds!");





        //Staff
        ItemStack healingStaff = new ItemStack(Material.ALLIUM);
        ItemMeta itemMeta = healingStaff.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "Healing Staff");
        String[] healingLore = {ChatColor.GREEN + "[Left Click]" + ChatColor.WHITE + " for a healing beam!" + ChatColor.BLUE + " (1xp)", ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE +" for a healing aura!"+ ChatColor.BLUE+ " (3xp)"};
        itemMeta.setLore(Arrays.asList(healingLore));
        healingStaff.setItemMeta(itemMeta);
        classItems.add(healingStaff);
        //Sword
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);
        stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 0,true);
        classItems.add(new ItemStack(stoneSword));
        //Boots
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);
        classArmor[0] = boots;
        //Legs
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setUnbreakable(true);
        legs.setItemMeta(legsMeta);
        classArmor[1] = legs;
        //Chest
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Hel
        ItemStack helmet = new ItemStack(Material.PEONY);
        classArmor[3] = helmet;
        //Other
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new ClassExperienceTalisman());


        //For icon
        ItemStack newItem = new ItemStack(Material.ALLIUM);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        price = 250;
        representingClass = Classes.Healer;
    }


}