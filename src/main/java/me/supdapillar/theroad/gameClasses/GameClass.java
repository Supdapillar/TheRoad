package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClass {
    public GameClass(TheRoadPlugin plugin){

    }
    public ItemStack inventoryIcon;
    public String className;
    public int price;
    public Classes representingClass;
    protected ArrayList<ItemStack> classItems = new ArrayList<ItemStack>();
    protected ItemStack[] classArmor = new ItemStack[4];
    public List<Talisman> starterTalismans = new ArrayList<>();


    public ItemStack makeIcon(Player player){
        ItemStack item = inventoryIcon;

        String[] lore = new String[1];
        ItemMeta itemMeta = item.getItemMeta();

        if (TheRoadPlugin.getInstance().PlayerClass.get(player) == representingClass){
            lore[0] = ChatColor.GREEN + "Currently Selected!";
            itemMeta.addEnchant(Enchantment.ARROW_INFINITE , 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else if (TheRoadPlugin.getInstance().PlayerUnlockedClasses.get(player).contains(representingClass) || price == 0){
            lore[0] = ChatColor.AQUA + "Unlocked!";
            itemMeta.removeEnchant(Enchantment.ARROW_INFINITE);
        }
        else
        {
            lore[0] = "" + ChatColor.RED + price + "$ " + ChatColor.GREEN + "[Click] " + ChatColor.RED + "To Unlock";
            itemMeta.removeEnchant(Enchantment.ARROW_INFINITE);
        }
        itemMeta.setLore(Arrays.asList(lore));

        item.setItemMeta(itemMeta);
        return item;
    }

    public void givePlayerClassItems(Player player){
        player.getInventory().setArmorContents(classArmor);
        for(ItemStack itemStack : classItems){
            player.getInventory().addItem(itemStack);
        }
    }

    public static GameClass getClassFromEnum(Classes classes){
        for(GameClass gameclass : TheRoadPlugin.getInstance().gameClasses){
            if (gameclass.representingClass == classes){
                return gameclass;
            }
        }
        return null;
    }

    public void processClick(Player player){
        if (TheRoadPlugin.getInstance().PlayerUnlockedClasses.get(player).contains(representingClass) || price == 0){
            //Removes all previoius class talisman
            TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).removeAll(GameClass.getClassFromEnum(TheRoadPlugin.getInstance().PlayerClass.get(player)).starterTalismans);

            TheRoadPlugin.getInstance().PlayerClass.put(player, representingClass);

            TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).addAll(GameClass.getClassFromEnum(TheRoadPlugin.getInstance().PlayerClass.get(player)).starterTalismans);

            player.playSound(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
            player.sendMessage(ChatColor.AQUA + "You're now the " + className + " class!");

        }
        else
        {
            if (TheRoadPlugin.getInstance().PlayerScores.get(player) >= price){
                TheRoadPlugin.getInstance().PlayerScores.put(player, TheRoadPlugin.getInstance().PlayerScores.get(player) - price);
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                TheRoadPlugin.getInstance().PlayerUnlockedClasses.get(player).add(representingClass);
                player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BELL,999,0.75f);
                player.sendMessage(ChatColor.GREEN + "You've unlocked the " + className + " for " + price + "$");
            }
            else {
                player.sendMessage(ChatColor.RED + "You do not have enough to buy this class!");
                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
            }
        }
    }
}
