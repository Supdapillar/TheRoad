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

public class Executioner extends GameClass {

    public Executioner(TheRoadPlugin plugin) {
        super(plugin);
        className = "Executioner";

        ItemStack ExecutionerAxe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta ExecutionerAxeMeta = ExecutionerAxe.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", -3.56f, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        ExecutionerAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);

        ExecutionerAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);

        ExecutionerAxe.setItemMeta(ExecutionerAxeMeta);

        classItems.add(ExecutionerAxe);
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.LEATHER_BOOTS);
        classArmor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        classArmor[3] = new ItemStack(Material.LEATHER_HELMET);

        ItemStack newItem = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 0;
        super.representingClass = Classes.Executioner;
    }


}