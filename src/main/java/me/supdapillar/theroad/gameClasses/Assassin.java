package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
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



        //Assassin blade
        ItemStack AssassinBlade = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta AssassinBladeMeta = AssassinBlade.getItemMeta();
        AttributeModifier modifier1 = new AttributeModifier(UUID.randomUUID(), "generic.movementSpeed", 0.1f, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AttributeModifier modifier2 = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        AssassinBladeMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, modifier1);
        AssassinBladeMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier2);
        AssassinBladeMeta.setUnbreakable(true);
        AssassinBlade.setItemMeta(AssassinBladeMeta);
        classItems.add(AssassinBlade);
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
        starterTalismans.add(new HealerClassTalisman());
        //Icon
        ItemStack newItem = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 300;
        super.representingClass = Classes.Assassin;

    }


}