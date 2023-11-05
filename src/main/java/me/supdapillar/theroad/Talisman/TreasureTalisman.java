package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Listeners.PlayerInteractEntityListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class TreasureTalisman extends Talisman{

    public TreasureTalisman(){
        name = "Treasure Talisman";
        price = 225;
        lores.add(ChatColor.LIGHT_PURPLE + "All loot chests generate ");
        lores.add(ChatColor.LIGHT_PURPLE + "50% more loot!");

        inventoryIcon = new ItemStack(Material.PLENTY_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onLootChestOpen(Inventory inventory, int tier){
        //Random Chest Generator
        Random random = new Random();
        int randomAmountOfItems = random.nextInt(2+tier,3+(tier*2));

        for (int i = 0; i < randomAmountOfItems; i++){

            int randomChestSlot = random.nextInt(27);

            while(inventory.getItem(randomChestSlot) != null){
                randomChestSlot = random.nextInt(27);
            }

            inventory.setItem(randomChestSlot, PlayerInteractEntityListener.GenerateRandomLoot(tier));
        }
    }
}
