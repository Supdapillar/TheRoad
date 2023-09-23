package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Random;

public class PlayerInteractEntityListener implements Listener {
    public PlayerInteractEntityListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if (event.getRightClicked() instanceof FallingBlock){
            Player player = event.getPlayer();
            FallingBlock fallingBlock = (FallingBlock) event.getRightClicked();
            Inventory lootInventory = Bukkit.createInventory(player, InventoryType.CHEST, "Loot Chest");

            //Random Chest Generator
            Random random = new Random();
            int randomAmountOfItems = random.nextInt(2,5);
            for (int i = 0; i < randomAmountOfItems; i++){

                ItemStack choosenItem = null;


                //Generates a random item
                while (choosenItem == null){
                    switch (random.nextInt(7)){

                        case 0:// Golden Apple
                            choosenItem = new ItemStack(Material.GOLDEN_APPLE, random.nextInt(2)+1);
                            break;
                        case 1://Potion of healing
                            ItemStack healingPotionStack = new ItemStack(Material.POTION, 1);
                            PotionMeta healingPotionMeta = (PotionMeta) healingPotionStack.getItemMeta();
                            healingPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));

                            healingPotionStack.setItemMeta(healingPotionMeta);
                            choosenItem = healingPotionStack;

                            break;
                        case 2: // Arrows
                            choosenItem = new ItemStack(Material.ARROW, random.nextInt(16)+6);
                            break;
                        case 3://Mostly broken Fishing rod
                            ItemStack fishingStack = new ItemStack(Material.FISHING_ROD, 1);

                            Damageable fishingMeta = (Damageable) fishingStack.getItemMeta();
                            fishingMeta.setDamage(random.nextInt(24, 59));

                            fishingStack.setItemMeta(fishingMeta);
                            choosenItem = fishingStack;
                            break;
                        case 4://mostly broken bow
                            ItemStack bowStack = new ItemStack(Material.BOW, 1);

                            Damageable bowMeta = (Damageable) bowStack.getItemMeta();
                            bowMeta.setDamage(random.nextInt(324, 377));

                            bowStack.setItemMeta(bowMeta);
                            choosenItem = bowStack;
                            break;
                        case 5://mostly broken crossbow
                            ItemStack crossbowStack = new ItemStack(Material.CROSSBOW, 1);

                            Damageable crossbowMeta = (Damageable) crossbowStack.getItemMeta();
                            crossbowMeta.setDamage(random.nextInt(425, 460));

                            crossbowStack.setItemMeta(crossbowMeta);
                            choosenItem = crossbowStack;
                            break;
                        case 6://Cracked axe but damaged
                            ItemStack sharpnessAxeStack = new ItemStack(Material.STONE_AXE, 1);

                            Damageable sharpnessAxeMeta = (Damageable) sharpnessAxeStack.getItemMeta();
                            sharpnessAxeMeta.setDamage(random.nextInt(122, 129));
                            sharpnessAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);

                            sharpnessAxeStack.setItemMeta(sharpnessAxeMeta);
                            choosenItem = sharpnessAxeStack;
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;
                        case 11:
                            break;
                        case 12:
                            break;
                        case 13:
                            break;
                        case 14:
                            break;
                    }
                }





                int randomChestSlot = random.nextInt(27)+1;

                while(lootInventory.getItem(randomChestSlot) != null){
                    randomChestSlot = random.nextInt(27)+1;
                }

                lootInventory.setItem(randomChestSlot,choosenItem);
            }



            player.openInventory(lootInventory);
            fallingBlock.remove();
        }
    }
}
