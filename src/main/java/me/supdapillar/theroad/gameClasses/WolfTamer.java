package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.HealerClassTalisman;
import me.supdapillar.theroad.Talisman.WolfTamerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class WolfTamer extends GameClass {

    public WolfTamer(TheRoadPlugin plugin) {
        super(plugin);
        className = "Wolf Tamer";


        //Axe
        ItemStack woodAxe = new ItemStack(Material.WOODEN_AXE);
        ItemMeta woodAxeMeta = woodAxe.getItemMeta();
        woodAxeMeta.setUnbreakable(true);
        woodAxe.setItemMeta(woodAxeMeta);
        classItems.add(new ItemStack(woodAxe));
        //Chest
        ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Helmet
        ItemStack wolfHead = Heads.Wolf.getItemStack();
        SkullMeta skullMeta = (SkullMeta) wolfHead.getItemMeta();
        skullMeta.setDisplayName("Wolf Helmet");
        classArmor[3] = new ItemStack(wolfHead);
        //Other
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new WolfTamerClassTalisman());
        starterTalismans.add(new HealerClassTalisman());
        //Icon
        ItemStack newItem = new ItemStack(Material.WOLF_SPAWN_EGG);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);



        super.inventoryIcon = newItem;
        super.price = 400;
        super.representingClass = Classes.WolfTamer;

    }


}