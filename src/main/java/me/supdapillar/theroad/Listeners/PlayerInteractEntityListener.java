package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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
import org.bukkit.inventory.meta.ItemMeta;
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
        if (!(event.getRightClicked() instanceof FallingBlock)) return;
            Player player = event.getPlayer();
            FallingBlock fallingBlock = (FallingBlock) event.getRightClicked();
            Inventory lootInventory = Bukkit.createInventory(player, InventoryType.CHEST, "Loot Chest");

            player.playSound(player, Sound.BLOCK_CHEST_OPEN, 9999, 1);

            //Random Chest Generator
            Random random = new Random();
            int randomAmountOfItems = random.nextInt(2,5);

            for (int i = 0; i < randomAmountOfItems; i++){

                int randomChestSlot = random.nextInt(27);

                while(lootInventory.getItem(randomChestSlot) != null){
                    randomChestSlot = random.nextInt(27);
                }

                lootInventory.setItem(randomChestSlot,GenerateRandomLoot());
            }



            player.openInventory(lootInventory);
            fallingBlock.remove();

            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                talisman.onLootChestOpen(lootInventory);
            }
    }

    public static ItemStack GenerateRandomLoot() {
        Random random = new Random();
        ItemStack choosenItem = null;

        //Generates a random item
        while (choosenItem == null) {
            switch (random.nextInt(12)) {

                case 0:// Golden Apple
                    choosenItem = new ItemStack(Material.GOLDEN_APPLE, random.nextInt(2) + 1);
                    break;
                case 1://Potion of healing
                    ItemStack healingPotionStack = new ItemStack(Material.POTION, 1);
                    PotionMeta healingPotionMeta = (PotionMeta) healingPotionStack.getItemMeta();
                    healingPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));

                    healingPotionStack.setItemMeta(healingPotionMeta);
                    choosenItem = healingPotionStack;
                    break;
                case 2: // Arrows
                    choosenItem = new ItemStack(Material.ARROW, random.nextInt(16) + 6);
                    break;
                case 3://Mostly broken Fishing rod
                    if (random.nextInt(5) == 0) {
                        ItemStack fishingStack = new ItemStack(Material.FISHING_ROD, 1);

                        Damageable fishingMeta = (Damageable) fishingStack.getItemMeta();
                        fishingMeta.setDamage(random.nextInt(24, 59));

                        fishingStack.setItemMeta(fishingMeta);
                        choosenItem = fishingStack;
                    }
                    break;
                case 4://mostly broken bow
                    if (random.nextInt(5) == 0) {
                        ItemStack bowStack = new ItemStack(Material.BOW, 1);

                        Damageable bowMeta = (Damageable) bowStack.getItemMeta();
                        bowMeta.setDamage(random.nextInt(324, 377));

                        bowStack.setItemMeta(bowMeta);
                        choosenItem = bowStack;
                    }
                    break;
                case 5://mostly broken crossbow
                    if (random.nextInt(6) == 0) {
                        ItemStack crossbowStack = new ItemStack(Material.CROSSBOW, 1);

                        Damageable crossbowMeta = (Damageable) crossbowStack.getItemMeta();
                        crossbowMeta.setDamage(random.nextInt(425, 460));

                        crossbowStack.setItemMeta(crossbowMeta);
                        choosenItem = crossbowStack;
                    }
                    break;
                case 6://Cracked axe but damaged
                    if (random.nextInt(5) == 0) {
                        ItemStack sharpnessAxeStack = new ItemStack(Material.STONE_AXE, 1);

                        Damageable sharpnessAxeMeta = (Damageable) sharpnessAxeStack.getItemMeta();
                        sharpnessAxeMeta.setDamage(random.nextInt(122, 129));
                        sharpnessAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);

                        sharpnessAxeStack.setItemMeta(sharpnessAxeMeta);
                        choosenItem = sharpnessAxeStack;
                    }
                    break;

                case 7: //Gives the player extra money
                    ItemStack coins = new ItemStack(Material.SUNFLOWER, random.nextInt(2, 8));
                    ItemMeta coinsMeta = coins.getItemMeta();
                    coinsMeta.setDisplayName(ChatColor.GOLD + "Extra Cash");
                    coins.setItemMeta(coinsMeta);

                    choosenItem = coins;
                    break;
                case 8:
                    //Insant extra heart
                    ItemStack extraHealth = new ItemStack(Material.SWEET_BERRIES);
                    ItemMeta extraHealthMeta = extraHealth.getItemMeta();

                    extraHealthMeta.setDisplayName(ChatColor.RED + "+1 HP");
                    extraHealth.setItemMeta(extraHealthMeta);

                    choosenItem = extraHealth;
                    break;
                case 9:
                    /////Perminent extra health
                    if (random.nextInt(7) == 0) {
                        ItemStack permanentHealth = new ItemStack(Material.GLOW_BERRIES);
                        ItemMeta permanentHealthMeta = permanentHealth.getItemMeta();

                        permanentHealthMeta.setDisplayName(ChatColor.GOLD + "+1 MAX HP");
                        permanentHealth.setItemMeta(permanentHealthMeta);

                        choosenItem = permanentHealth;
                    }

                    break;
                case 10:
                    //Extra revive but at half the health
                    if (random.nextInt(16) == 0) {
                        ItemStack extraRevive = new ItemStack(Material.NETHER_STAR);
                        ItemMeta extraReviveMeta = extraRevive.getItemMeta();

                        extraReviveMeta.setDisplayName(ChatColor.BOLD + "+1 Spare Revive");
                        extraRevive.setItemMeta(extraReviveMeta);


                        choosenItem = extraRevive;
                    }
                    break;
                case 11:
                    //Wolf minions
                    if (random.nextInt(1) == 0) {
                        ItemStack wolfMinions = new ItemStack(Material.WOLF_SPAWN_EGG, random.nextInt(2, 4));
                        ItemMeta wolfMinionsMeta = wolfMinions.getItemMeta();

                        wolfMinionsMeta.setDisplayName(ChatColor.GREEN + "Wolf Summoner");
                        wolfMinions.setItemMeta(wolfMinionsMeta);

                        choosenItem = wolfMinions;
                    }
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
            }

        }
        return choosenItem;
    }
}
