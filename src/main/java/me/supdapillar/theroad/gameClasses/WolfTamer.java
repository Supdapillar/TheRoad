package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.WolfTamerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class WolfTamer extends GameClass {

    public WolfTamer(TheRoadPlugin plugin) {
        super(plugin);
        super.className = "Wolf Tamer";

        //Icon
        ItemStack newItem = new ItemStack(Material.WOLF_SPAWN_EGG);
        ItemMeta newItemMeta = newItem.getItemMeta();
        //Name
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        starterTalismans.add(new WolfTamerClassTalisman());

        //Starter Items
        classItems.add(new ItemStack(Material.WOODEN_AXE));
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.AIR);
        classArmor[1] = new ItemStack(Material.AIR);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);




        ItemStack wolfHead = Heads.Wolf.getItemStack();
        SkullMeta skullMeta = (SkullMeta) wolfHead.getItemMeta();
        skullMeta.setDisplayName("Wolf Helmet");

        classArmor[3] = new ItemStack(wolfHead);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 0;
        super.representingClass = Classes.WolfTamer;

    }


}