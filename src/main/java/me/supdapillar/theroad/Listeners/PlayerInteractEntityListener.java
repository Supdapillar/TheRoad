package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ConjuringShrineHelper;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.gameClasses.Merchant;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PlayerInteractEntityListener implements Listener {
    public PlayerInteractEntityListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if ((event.getRightClicked() instanceof FallingBlock)){
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE){
                FallingBlock fallingBlock = (FallingBlock) event.getRightClicked();
                if (fallingBlock.getBlockData().getMaterial() == Material.BARREL){
                    Player player = event.getPlayer();
                    Inventory lootInventory = Bukkit.createInventory(player, InventoryType.CHEST, "Loot Chest");
                    NamespacedKey tierKey = new NamespacedKey(TheRoadPlugin.getInstance(),"LootTier");
                    int tier = fallingBlock.getPersistentDataContainer().get(tierKey, PersistentDataType.INTEGER);
                    player.playSound(player, Sound.BLOCK_CHEST_OPEN, 9999, 1);

                    //Random Chest Generator
                    Random random = new Random();
                    int randomAmountOfItems = random.nextInt(2+(tier*2),5+(tier*4));

                    for (int i = 0; i < randomAmountOfItems; i++){
                        int randomChestSlot = random.nextInt(27);
                        while(lootInventory.getItem(randomChestSlot) != null){
                            randomChestSlot = random.nextInt(27);
                        }
                        lootInventory.setItem(randomChestSlot,GenerateRandomLoot(tier));
                    }



                    player.openInventory(lootInventory);
                    fallingBlock.remove();

                    //Talisman
                    for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                        talisman.onLootChestOpen(lootInventory, tier);
                    }
                }
                else if (fallingBlock.getBlockData().getMaterial() == Material.BARRIER){
                    for (Entity entity: fallingBlock.getNearbyEntities(1,1,1)){
                        if (entity instanceof TextDisplay){
                            if (event.getHand() == EquipmentSlot.HAND) {
                                ConjuringShrineHelper.processClick(event.getPlayer(), (TextDisplay) entity);
                            }
                        }
                    }
                }
            }
        }
        else if (event.getRightClicked() instanceof Player){
            //For the merchant class
            Player clickedPlayer = (Player) event.getRightClicked();
            if (TheRoadPlugin.getInstance().PlayerClass.get(clickedPlayer) == Classes.Merchant){
                Merchant.openMerchantShop(event.getPlayer());
            }
        }
        else if (event.getRightClicked() instanceof ItemFrame){
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE){
                event.setCancelled(true);
            }
        }
    }

    public static ItemStack GenerateRandomLoot(int tier) {
        Random random = new Random();
        ItemStack choosenItem = null;

        //Generates a random item
        while (choosenItem == null) {
            switch (random.nextInt(14+tier)) {

                case 0:// Golden Apple
                    choosenItem = new ItemStack(Material.GOLDEN_APPLE, random.nextInt(1+tier) + 1);
                    break;
                case 1://Potion of healing
                    ItemStack healingPotionStack = new ItemStack(Material.POTION, 1);
                    PotionMeta healingPotionMeta = (PotionMeta) healingPotionStack.getItemMeta();
                    healingPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
                    if ((Math.random()*2)+tier > 1){
                        healingPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 0, 1, true, true, true),true);
                    }
                    healingPotionStack.setItemMeta(healingPotionMeta);
                    choosenItem = healingPotionStack;
                    break;
                case 2: // Arrows
                    choosenItem = new ItemStack(Material.ARROW, random.nextInt(16+(tier*4)) + 6);
                    if (tier == 2){
                        choosenItem= new ItemStack(Material.SPECTRAL_ARROW, random.nextInt(16+(tier*4)) + 6);
                    }
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
                    if (random.nextInt(5-tier) == 0) {
                        ItemStack bowStack = new ItemStack(Material.BOW, 1);

                        Damageable bowMeta = (Damageable) bowStack.getItemMeta();
                        bowMeta.setDamage(random.nextInt(324-(tier*20), 377-(tier*20)));

                        bowStack.setItemMeta(bowMeta);
                        choosenItem = bowStack;
                    }
                    break;
                case 5://mostly broken crossbow
                    if (random.nextInt(6) == 0) {
                        ItemStack crossbowStack = new ItemStack(Material.CROSSBOW, 1);

                        Damageable crossbowMeta = (Damageable) crossbowStack.getItemMeta();
                        crossbowMeta.setDamage(random.nextInt(425-(tier*15), 460-(tier*15)));

                        crossbowStack.setItemMeta(crossbowMeta);
                        choosenItem = crossbowStack;
                    }
                    break;
                case 6://Cracked axe but damaged
                    if (random.nextInt(5-tier) == 0) {
                        ItemStack sharpnessAxeStack = new ItemStack(Material.STONE_AXE, 1);

                        Damageable sharpnessAxeMeta = (Damageable) sharpnessAxeStack.getItemMeta();
                        sharpnessAxeMeta.setDamage(random.nextInt(122-(tier*5), 129-(tier*5)));
                        sharpnessAxeMeta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);

                        sharpnessAxeStack.setItemMeta(sharpnessAxeMeta);
                        choosenItem = sharpnessAxeStack;
                    }
                    break;

                case 7: //Gives the player extra money
                    ItemStack coins = new ItemStack(Material.SUNFLOWER, random.nextInt(2+(tier*3), 8+(tier*4)));
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
                    if (random.nextInt(7-tier) == 0) {
                        ItemStack permanentHealth = new ItemStack(Material.GLOW_BERRIES);
                        ItemMeta permanentHealthMeta = permanentHealth.getItemMeta();

                        permanentHealthMeta.setDisplayName(ChatColor.GOLD + "+1 MAX HP");
                        permanentHealth.setItemMeta(permanentHealthMeta);

                        choosenItem = permanentHealth;
                    }

                    break;
                case 10:
                    //Extra revive but at half the health
                    if (random.nextInt(20-(tier*2)) == 0) {
                        ItemStack extraRevive = new ItemStack(Material.NETHER_STAR);
                        ItemMeta extraReviveMeta = extraRevive.getItemMeta();

                        extraReviveMeta.setDisplayName(ChatColor.BOLD + "+1 Spare Revive");
                        extraRevive.setItemMeta(extraReviveMeta);


                        choosenItem = extraRevive;
                    }
                    break;
                case 11:
                    //Wolf minions
                    if (random.nextInt(13-(tier*2)) == 0) {
                        ItemStack wolfMinions = new ItemStack(Material.WOLF_SPAWN_EGG, random.nextInt(1, 3));
                        ItemMeta wolfMinionsMeta = wolfMinions.getItemMeta();

                        wolfMinionsMeta.setDisplayName(ChatColor.GREEN + "Wolf Summoner");
                        wolfMinions.setItemMeta(wolfMinionsMeta);

                        choosenItem = wolfMinions;
                    }
                    break;
                case 12:
                    //Xp bottles
                    choosenItem = new ItemStack(Material.EXPERIENCE_BOTTLE, random.nextInt(1+(tier*3),7+(tier*4)));
                    break;
                case 13:
                    //Echo Shield
                    if (random.nextInt(10-tier) == 0) {
                        ItemStack echoShield = new ItemStack(Material.ECHO_SHARD);
                        ItemMeta echoMeta = echoShield.getItemMeta();
                        echoMeta.setDisplayName(ChatColor.DARK_BLUE + "Echo Shield");
                        echoMeta.setLore(Collections.singletonList(ChatColor.LIGHT_PURPLE + "This crystal makes to where damage you take is given to the enemy instead!"));
                        echoShield.setItemMeta(echoMeta);
                        choosenItem = echoShield;
                    }
                    break;
                case 14: // Only tier 1+
                    //Explosive charge
                    if (random.nextInt(4-tier) == 0) {
                        ItemStack explosiveCharge = new ItemStack(Material.GUNPOWDER);
                        ItemMeta explosiveChargeMeta = explosiveCharge.getItemMeta();
                        explosiveChargeMeta.setDisplayName(ChatColor.GOLD + "Explosive Charge");
                        explosiveChargeMeta.setLore(Collections.singletonList(ChatColor.LIGHT_PURPLE + "Next time you take damage you release a catastrophic explosion!"));
                        explosiveCharge.setItemMeta(explosiveChargeMeta);
                        choosenItem = explosiveCharge;
                    }
                    break;
                case 15:// Only max tier
                    //God potion
                    if (random.nextInt(5) == 0) {
                        ItemStack godPotionStack = new ItemStack(Material.POTION, 1);
                        PotionMeta godPotionMeta = (PotionMeta) godPotionStack.getItemMeta();
                        godPotionMeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "God potion");
                        godPotionMeta.setLore(Collections.singletonList(ChatColor.LIGHT_PURPLE + "The power of the calamity courses through your veins!"));
                        godPotionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
                        godPotionMeta.setColor(Color.BLACK);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 900, 0, true, true, true),true);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900, 1, true, true, true),true);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 0, true, true, true),true);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1200, 0, true, true, true),true);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1200, 0, true, true, true),true);
                        godPotionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 900, 1, true, true, true),true);
                        godPotionStack.setItemMeta(godPotionMeta);
                        choosenItem = godPotionStack;

                    }
                    break;
            }

        }
        return choosenItem;
    }
}
