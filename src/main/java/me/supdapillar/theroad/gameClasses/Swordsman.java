package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Swordsman extends GameClass {

    public Swordsman(TheRoadPlugin plugin) {
        super(plugin);
        className = "Swordsman";

        //Sword
        ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta ironSwordMeta = ironSword.getItemMeta();
        ironSwordMeta.setUnbreakable(true);
        ironSword.setItemMeta(ironSwordMeta);
        ironSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 0,true);
        classItems.add(new ItemStack(ironSword));
        //Boots
        ItemStack boots = new ItemStack(Material.CHAINMAIL_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);
        classArmor[0] = boots;
        //Legs
        ItemStack legs = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setUnbreakable(true);
        legs.setItemMeta(legsMeta);
        classArmor[1] = legs;
        //Chest
        ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Hel
        ItemStack helmet = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        classArmor[3] = helmet;
        //Other
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new HealerClassTalisman());
        //Potions
        ItemStack strengthPot = new ItemStack(Material.POTION);
        PotionMeta strengthMeta = (PotionMeta) strengthPot.getItemMeta();
        strengthMeta.setColor(Color.fromRGB(255, 199, 0));
        strengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0, true, true, true),true);
        strengthMeta.setDisplayName("Potion of Strength");
        strengthPot.setItemMeta(strengthMeta);
        classItems.add(new ItemStack(strengthPot));
        //Icon
        ItemStack newItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        price = 0;
        representingClass = Classes.Swordsman;
    }


}