package me.supdapillar.theroad.gameClasses;

import com.mojang.authlib.GameProfile;
import me.supdapillar.theroad.Talisman.SummonerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;


import java.lang.reflect.Field;
import java.util.Properties;
import java.util.UUID;

public class Summoner extends GameClass {

    public Summoner(TheRoadPlugin plugin) {
        super(plugin);
        super.className = "Summoner";

        //Icon
        ItemStack newItem = new ItemStack(Material.SILVERFISH_SPAWN_EGG);
        ItemMeta newItemMeta = newItem.getItemMeta();
        //Name
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        starterTalismans.add(new SummonerClassTalisman());

        //Starter Items
        classItems.add(new ItemStack(Material.WOODEN_AXE));
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.AIR);
        classArmor[1] = new ItemStack(Material.AIR);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);




        ItemStack silverFishHead = Heads.Silverfish.getItemStack();
        SkullMeta skullMeta = (SkullMeta) silverFishHead.getItemMeta();
        skullMeta.setDisplayName("Silverfish Helmet");

        classArmor[3] = new ItemStack(silverFishHead);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 0;
        super.representingClass = Classes.Summoner;

    }


}