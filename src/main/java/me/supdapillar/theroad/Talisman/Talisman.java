package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
                Talisman talismanToBeRemoved = null;
                for (Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)) {
                    if (talisman.equals(this)) talismanToBeRemoved = talisman;
                }
                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).remove(talismanToBeRemoved);
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                player.sendMessage(ChatColor.RED + "You've deactivated the " + name);
            }
            else {
                if (TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).size() < 3){ // If the player hasnt hit the cap on talismans
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                    player.sendMessage(ChatColor.AQUA + "You've activated the " + name + "!");
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(this);
                }
                else {
                    player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
                    player.sendMessage(ChatColor.AQUA + "You've activated the " + name);
                    player.sendMessage(ChatColor.RED + "but you've unactivated the " + name + "!");
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).remove(0);
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(this);
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
    }

    private boolean containsTalisman(final List<Talisman> list){
        return list.stream().anyMatch(o -> Objects.equals(o.name, name));
    }

    public void onMobDeath(EntityDeathEvent event) {};
    public void onMobDamage(EntityDamageByEntityEvent event) {};
    public void onPlayerMove(PlayerMoveEvent event) {};
}
