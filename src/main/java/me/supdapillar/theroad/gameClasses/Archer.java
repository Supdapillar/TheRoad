package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.ArcherClassTalisman;
import me.supdapillar.theroad.Talisman.ClassExperienceTalisman;
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
        iconLore.add(ChatColor.BLUE + "The highly trained archer, able ");
        iconLore.add(ChatColor.BLUE + "to gain arrows with kills! ");

        //Sword
        ItemStack woodSword = new ItemStack(Material.WOODEN_SWORD);
        ItemMeta woodSwordMeta = woodSword.getItemMeta();
        woodSwordMeta.setUnbreakable(true);
        woodSword.setItemMeta(woodSwordMeta);
        classItems.add(new ItemStack(woodSword));
        //Bow
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setUnbreakable(true);
        bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1,true);
        bow.setItemMeta(bowMeta);
        classItems.add(bow);
        //Boots
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
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
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
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
        classItems.add(new ItemStack(Material.ARROW, 64));
        classItems.add(new ItemStack(Material.ARROW, 64));
        //Talisman
        starterTalismans.add(new ClassExperienceTalisman());
        starterTalismans.add(new ArcherClassTalisman());

        //Icon
        ItemStack newItem = new ItemStack(Material.BOW);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;

    }


}