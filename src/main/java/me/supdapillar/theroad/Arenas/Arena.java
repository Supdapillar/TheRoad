package me.supdapillar.theroad.Arenas;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Managers.GameManager;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    public Arena(){
    }

    //Inventory Stuff
    public ItemStack inventoryIcon;
    public ArrayList<String> iconLore = new ArrayList<String>();
    public String arenaName;

    //Ingame stuff
    public Location spawnLocation = new Location(Bukkit.getWorld("HauntedRoad"), 4.5, -59, 1.5 );
    public int finalRound;
    public List<Player> votedPlayers = new ArrayList<>();


    public ItemStack makeIcon(Player player){
        ItemStack item = inventoryIcon;

        ItemMeta itemMeta = item.getItemMeta();
        ArrayList<String> subLore = new ArrayList<String>();

        //Adds all the voted players into the lore
        if (!votedPlayers.isEmpty()){
            subLore.add(ChatColor.LIGHT_PURPLE + "Current Votes: ");
        }
        for(Player votedPlayer : votedPlayers){
            if (votedPlayer == player){//Make inventory owners name green
                subLore.add(ChatColor.GREEN + votedPlayer.getName());
            }
            else {
                subLore.add(ChatColor.WHITE +  votedPlayer.getName());
            }
        }




        if (votedPlayers.contains(player)){ // The player that opened the inventory has voted for this map
            itemMeta.addEnchant(Enchantment.ARROW_INFINITE , 0, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        else {// The player has not voted
            itemMeta.removeEnchant(Enchantment.ARROW_INFINITE);
        }


        List<String> newList = new ArrayList<String>(iconLore);
        newList.addAll(subLore);

        itemMeta.setLore(newList);
        item.setItemMeta(itemMeta);

        return item;
    }
    public void processClick(Player player){

        if (votedPlayers.contains(player)){ // remove vote
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
            player.sendMessage(ChatColor.GREEN + "You removed your vote for " + arenaName);
            votedPlayers.remove(player);
        }
        else { // Add new vote
            //Clears this player from all arena votes
            for (Arena arena : TheRoadPlugin.getInstance().gameManager.gameArenas){
                arena.votedPlayers.remove(player);
            }
            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
            player.sendMessage(ChatColor.GREEN + "You added a vote for " + arenaName);
            votedPlayers.add(player);
        }
    }
}
