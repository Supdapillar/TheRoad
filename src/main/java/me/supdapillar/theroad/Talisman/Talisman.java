package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Talisman {
    public ItemStack inventoryIcon;
    public String name;
    public int price;
    public ArrayList<String> lores = new ArrayList<String>();
    public boolean isChallenge = false;
    public boolean countsAsActive = true;
    public ItemStack makeIcon(Player player){
        ItemStack item = inventoryIcon;

        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> subLore = new ArrayList<String>();

        if (containsTalisman(TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player) )){
            subLore.add(ChatColor.GREEN + "Currently Selected!");
            itemMeta.addEnchant(Enchantment.ARROW_INFINITE , 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else if (containsTalisman(TheRoadPlugin.getInstance().PlayerUnlockedTalisman.get(player)) || price == 0){
            subLore.add(ChatColor.AQUA + "Unlocked!");
            itemMeta.removeEnchant(Enchantment.ARROW_INFINITE);
        }
        else
        {
            subLore.add("" + ChatColor.RED + price + "$ " + ChatColor.GREEN + "[Click] " + ChatColor.RED + "To Unlock");
            itemMeta.removeEnchant(Enchantment.ARROW_INFINITE);
        }
        List<String> newList = new ArrayList<String>(lores);
        newList.addAll(subLore);

        itemMeta.setLore(newList);
        item.setItemMeta(itemMeta);
        return item;
    }

    public void processClick(Player player){
        if (containsTalisman(TheRoadPlugin.getInstance().PlayerUnlockedTalisman.get(player)) || price == 0){
            if (containsTalisman(TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player))){
                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).remove(this);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                player.sendMessage(ChatColor.RED + "You've deactivated the " + name);
                this.onTalismanDeselect(player);

            }
            else {
                if (getPlayerActiveTalismans(player) < TheRoadPlugin.getInstance().TalismanSlots.get(player)){ // If the player hasnt hit the cap on talismans
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                    player.sendMessage(ChatColor.AQUA + "You've activated the " + name + "!");
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(this);
                    this.onTalismanSelect(player);
                }
                else {
                    Talisman talismanToBeRemoved = getFirstActiveTalisman(player);
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                    player.sendMessage(ChatColor.AQUA + "You've activated the " + name);
                    player.sendMessage(ChatColor.RED + "but you've unactivated the " + talismanToBeRemoved.name + "!");
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).remove(talismanToBeRemoved);
                    talismanToBeRemoved.onTalismanDeselect(player);
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(this);
                    this.onTalismanSelect(player);
                }
            }

        }
        else
        {
            if (TheRoadPlugin.getInstance().PlayerScores.get(player) >= price){
                TheRoadPlugin.getInstance().PlayerScores.put(player, TheRoadPlugin.getInstance().PlayerScores.get(player) - price);
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                TheRoadPlugin.getInstance().PlayerUnlockedTalisman.get(player).add(this);
                player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BELL,999,0.75f);
                player.sendMessage(ChatColor.GREEN + "You've unlocked the " + name + " for " + price + "$");
            }
            else {
                player.sendMessage(ChatColor.RED + "You do not have enough to buy this talisman!");
                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
            }
        }
        //Talisman slots upgrade

    }

    public static int getPlayerActiveTalismans(Player player){
        return TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).stream().filter(o -> o.countsAsActive).toArray().length;
    }

    private boolean containsTalisman(final List<Talisman> list){
        return list.stream().anyMatch(o -> Objects.equals(o.name, name));
    }

    public void onTalismanDeselect(Player player) {};
    public void onTalismanSelect(Player player) {};


    public void onMobDeath(EntityDeathEvent event) {};
    public void onMobDamage(EntityDamageByEntityEvent event) {};
    public void onPlayerMove(PlayerMoveEvent event) {};
    public void onPlayerHealthRegain(EntityRegainHealthEvent event) {};
    public void onPlayerDamage(EntityDamageByEntityEvent event){};
    public void onLootChestOpen(Inventory inventory, int tier){};
    public static void processSlotsUpgrade(Player player){
        if (TheRoadPlugin.getInstance().TalismanSlots.get(player) < 5){
            //Player has the money
            int Cost = TheRoadPlugin.getInstance().TalismanSlots.get(player)*1000;
            if (TheRoadPlugin.getInstance().PlayerScores.get(player) >= Cost) {
                TheRoadPlugin.getInstance().PlayerScores.put(player, TheRoadPlugin.getInstance().PlayerScores.get(player)-Cost);
                TheRoadPlugin.getInstance().TalismanSlots.put(player, TheRoadPlugin.getInstance().TalismanSlots.get(player)+1);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                player.sendMessage(ChatColor.AQUA + "You've purchased another talisman slot!");
                player.sendMessage(ChatColor.AQUA + "You now have " + TheRoadPlugin.getInstance().TalismanSlots.get(player) + " slots!");
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
            }
            else {
                player.playSound(player, Sound.ENTITY_VILLAGER_NO,999,1f);
                player.sendMessage(ChatColor.RED + "You do not have enough to buy more talisman slots!");
            }
        }
    }
    public static Talisman getFirstActiveTalisman(Player player){
        for (Talisman talisman: TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
            if (!talisman.isChallenge && talisman.countsAsActive){
                return talisman;
            }
        }
        return null;
    }
}
