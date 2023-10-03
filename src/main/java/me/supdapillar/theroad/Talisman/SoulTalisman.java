package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Date;

public class SoulTalisman extends Talisman{

    public SoulTalisman(){
        name = "Soul Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "If an ally takes fatal ");
        lores.add(ChatColor.LIGHT_PURPLE + "damage then you permanently ");
        lores.add(ChatColor.LIGHT_PURPLE + "Sacrifice 1♥ to save them!");



        inventoryIcon = new ItemStack(Material.ARMS_UP_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }


    @Override
    public void onTalismanSelect(Player player){
        //Gives all the players a talisman to try and revive them at death
        for (Player player1 : Bukkit.getOnlinePlayers() ){
            if (player1 != player){
                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).add(new SoulChecker(player));
            }
        }
    }
    @Override
    public void onTalismanDeselect(Player player){
        //Makes sure all the temperary talismans are deleted

        for(Player player1 : Bukkit.getOnlinePlayers() ){
            //Checks all the talisman
            for (int i = 0; i < TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).size(); i++){
                Bukkit.broadcastMessage(TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).get(i).name);
                //Finds any soul Checkers
                if (TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).get(i) instanceof SoulChecker){
                    SoulChecker soulChecker  = (SoulChecker) TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).get(i);
                    //Removes the talisman if the Host is the one who clicked
                    if (soulChecker.Host == player){
                        TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).remove(i);
                    }
                }
            }
        }
    }
}
