package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlunderTalisman extends Talisman{

    public PlunderTalisman(){
        name = "Plunder Talisman";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "Every kill has a small ");
        lores.add(ChatColor.LIGHT_PURPLE + "chance to spawn a loot chest! ");


        inventoryIcon = new ItemStack(Material.EXPLORER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }


    @Override
    public void onMobDeath(EntityDeathEvent event) {
        if (Math.random() < 0.075){
            //Make loot chest appear
            TheRoadPlugin.getInstance().gameManager.makeLootChest(event.getEntity());
        }
    }

}
