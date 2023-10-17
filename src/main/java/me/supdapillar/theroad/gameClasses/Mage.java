package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public class Mage extends GameClass {

    public Mage(TheRoadPlugin plugin) {
        super(plugin);
        className = "Mage";
        price = 0;
        representingClass = Classes.Mage;

        AttributeModifier fourDamage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier threeDamage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier slowerAttackSpeed = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", -2.7, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);


        //Firestaff
        ItemStack fireStaff = new ItemStack(Material.BLAZE_ROD);
        ItemMeta fireStaffItemMeta = fireStaff.getItemMeta();
        fireStaffItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fireStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,fourDamage);
        fireStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,slowerAttackSpeed);
        fireStaffItemMeta.setDisplayName(ChatColor.GOLD + "Fire Staff");
        String[] fireLore = {ChatColor.GREEN + "[Left Click]" + ChatColor.WHITE + " for a blazing attack to set enemies on fire! ", ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE + " to cast infernal eruption!" + ChatColor.BLUE + " (9xp)"};
        fireStaffItemMeta.setLore(Arrays.asList(fireLore));
        fireStaff.setItemMeta(fireStaffItemMeta);
        //CrystalStaff
        ItemStack crystalStaff = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta crystalStaffItemMeta = crystalStaff.getItemMeta();
        crystalStaffItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        crystalStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,threeDamage);
        crystalStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,slowerAttackSpeed);
        crystalStaffItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Crystal Staff");
        String[] crystalLore = {ChatColor.GREEN + "[Left Click]" + ChatColor.WHITE + " for an echoing attack to hit multiple enemies! ", ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE + " to cast chain lightning!" + ChatColor.BLUE + " (12xp)"};
        crystalStaffItemMeta.setLore(Arrays.asList(crystalLore));
        crystalStaff.setItemMeta(crystalStaffItemMeta);
        //NatureStaff
        ItemStack natureStaff = new ItemStack(Material.MANGROVE_PROPAGULE);
        ItemMeta natureStaffItemMeta = natureStaff.getItemMeta();
        natureStaffItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        natureStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,fourDamage);
        natureStaffItemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,slowerAttackSpeed);
        natureStaffItemMeta.setDisplayName(ChatColor.GREEN + "Nature Staff");
        String[] natureLore = {ChatColor.GREEN + "[Left Click]" + ChatColor.WHITE + " for a rooting attack to slow enemies! ", ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE + " to cast violent overgrowth!" + ChatColor.BLUE + " (like 2million xp)"};
        natureStaffItemMeta.setLore(Arrays.asList(natureLore));
        natureStaff.setItemMeta(natureStaffItemMeta);


        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classItems.add(fireStaff);
        classItems.add(crystalStaff);
        classItems.add(natureStaff);

        starterTalismans.add(new HealerClassTalisman());

        classArmor[0] = new ItemStack(Material.LEATHER_BOOTS);
        classArmor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        classArmor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
        //Helmet
            ItemStack mageHead = Heads.Mage.getItemStack();
            SkullMeta skullMeta = (SkullMeta) mageHead.getItemMeta();
            skullMeta.setDisplayName("Mage Helmet");
            classArmor[3] = new ItemStack(mageHead);

        //For icon
        ItemStack newItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
    }


}