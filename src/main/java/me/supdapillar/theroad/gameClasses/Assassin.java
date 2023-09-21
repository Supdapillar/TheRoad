package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class Assassin extends GameClass {

    public Assassin(TheRoadPlugin plugin) {
        super(plugin);
        super.className = "Assassin";

        ItemStack newItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


        ItemStack AssassinBlade = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta AssassinBladeMeta = AssassinBlade.getItemMeta();
        AttributeModifier modifier1 = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", 0.1f, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);

        AssassinBladeMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier1);
        AssassinBladeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        AssassinBladeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier3);


        AssassinBlade.setItemMeta(AssassinBladeMeta);
        classItems.add(AssassinBlade);

        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.AIR);
        classArmor[1] = new ItemStack(Material.AIR);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        classArmor[3] = new ItemStack(Material.AIR);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 250;
        super.representingClass = Classes.Assassin;

    }


}