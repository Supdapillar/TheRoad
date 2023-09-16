package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.SummonerClassTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;

import java.util.UUID;

public class Summoner extends GameClass {

    public Summoner(TheRoadPlugin plugin) {
        super(plugin);
        super.className = "Summoner";

        ItemStack newItem = new ItemStack(Material.SILVERFISH_SPAWN_EGG);
        ItemMeta newItemMeta = newItem.getItemMeta();

        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        starterTalismans.add(new SummonerClassTalisman());

        classItems.add(new ItemStack(Material.WOODEN_AXE));
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        classArmor[0] = new ItemStack(Material.AIR);
        classArmor[1] = new ItemStack(Material.AIR);
        classArmor[2] = new ItemStack(Material.CHAINMAIL_CHESTPLATE);

        ItemStack silverFishHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) silverFishHead.getItemMeta();
        headMeta.setOwningPlayer(Bukkit.getOfflinePlayer("Dunga"));

        silverFishHead.setItemMeta(headMeta);

        classArmor[3] = new ItemStack(silverFishHead);

        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        super.price = 0;
        super.representingClass = Classes.Summoner;

    }


}