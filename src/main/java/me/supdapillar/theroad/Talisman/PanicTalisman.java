package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class PanicTalisman extends Talisman{

    public PanicTalisman(){
        name = "Panic Talisman";
        price = 200;
        lores.add(ChatColor.LIGHT_PURPLE + "The less health the player ");
        lores.add(ChatColor.LIGHT_PURPLE + "has the faster they move ");


        inventoryIcon = new ItemStack(Material.HOWL_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
    @Override
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3f - (0.01 * player.getHealth()) );
    }

    @Override
    public void onTalismanDeselect(Player player){
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1f);
    }
}
