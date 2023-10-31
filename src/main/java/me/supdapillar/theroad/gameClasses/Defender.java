package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

public class Defender extends GameClass {

    public Defender(TheRoadPlugin plugin) {
        super(plugin);
        className = "Defender";


        //Stone
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);
        stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 0,true);
        classItems.add(new ItemStack(stoneSword));
        //Shield Manifester
        ItemStack defenderCrystal = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta itemMeta = defenderCrystal.getItemMeta();
        itemMeta.setDisplayName(ChatColor.AQUA +"Shield Manifester");
        itemMeta.setLore(Collections.singletonList(ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE + " to manifest a shield, capable of blocking enemies!" + ChatColor.BLUE + " (10xp)"));
        defenderCrystal.setItemMeta(itemMeta);
        classItems.add(defenderCrystal);
        //Boots
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
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
        ItemStack chest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Hel
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        classArmor[3] = helmet;
        //Extra
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new HealerClassTalisman());
        //For icon
        ItemStack newItem = new ItemStack(Material.WARPED_DOOR);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        price = 500;
        representingClass = Classes.Defender;
    }


}