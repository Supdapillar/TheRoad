package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.ClassExperienceTalisman;
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
        iconLore.add(ChatColor.BLUE + "Equipped with a legendary axe, the");
        iconLore.add(ChatColor.BLUE + "executioner is able to 1 shot most enemies! ");




        //Axe
        ItemStack ExecutionerAxe = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta ExecutionerAxeMeta = ExecutionerAxe.getItemMeta();
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 20, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", -3.56f, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifier3 = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", -0.05f, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        ExecutionerAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
        ExecutionerAxeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, modifier2);
        ExecutionerAxeMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier3);
        ExecutionerAxeMeta.setUnbreakable(true);
        ExecutionerAxe.setItemMeta(ExecutionerAxeMeta);
        classItems.add(ExecutionerAxe);
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
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Hel
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        classArmor[3] = helmet;
        //Extra items
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new ClassExperienceTalisman());
        //Icon
        ItemStack newItem = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 500;
        super.representingClass = Classes.Executioner;
    }


}