package me.supdapillar.theroad.gameClasses;

import me.supdapillar.theroad.Talisman.ClassExperienceTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collections;

public class Merchant extends GameClass {

    public Merchant(TheRoadPlugin plugin) {
        super(plugin);
        className = "Merchant";
        iconLore.add(ChatColor.BLUE + "A greedy merchant still charging");
        iconLore.add(ChatColor.BLUE + "for his wares in times of calamity!");

        //Merchant Shop
        ItemStack merchantShop = new ItemStack(Material.CHEST);
        ItemMeta itemMeta = merchantShop.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Merchant Shop");
        String[] merchantLore = {ChatColor.GREEN + "[Right Click]" + ChatColor.WHITE +" to open a shop to spend your xp!"};
        itemMeta.setLore(Arrays.asList(merchantLore));
        merchantShop.setItemMeta(itemMeta);
        classItems.add(merchantShop);
        //Sword
        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setUnbreakable(true);
        stoneSword.setItemMeta(stoneSwordMeta);
        stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 0,true);
        classItems.add(new ItemStack(stoneSword));
        //Boots
        ItemStack boots = new ItemStack(Material.GOLDEN_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setUnbreakable(true);
        boots.setItemMeta(bootsMeta);
        classArmor[0] = boots;
        //Legs
        ItemStack legs = new ItemStack(Material.GOLDEN_LEGGINGS);
        ItemMeta legsMeta = legs.getItemMeta();
        legsMeta.setUnbreakable(true);
        legs.setItemMeta(legsMeta);
        classArmor[1] = legs;
        //Chest
        ItemStack chest = new ItemStack(Material.GOLDEN_CHESTPLATE);
        ItemMeta chestMeta = chest.getItemMeta();
        chestMeta.setUnbreakable(true);
        chest.setItemMeta(chestMeta);
        classArmor[2] = chest;
        //Hel
        ItemStack helmet = new ItemStack(Material.CHEST);
        classArmor[3] = helmet;
        //Extra
        classItems.add(new ItemStack(Material.GOLDEN_APPLE,2));
        //Talisman
        starterTalismans.add(new ClassExperienceTalisman());
        //For icon
        ItemStack newItem = new ItemStack(Material.CHEST);
        ItemMeta newItemMeta = newItem.getItemMeta();
        newItemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + className);
        newItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        newItem.setItemMeta(newItemMeta);

        super.inventoryIcon = newItem;
        price = 400;
        representingClass = Classes.Merchant;
    }

    public static void openMerchantShop(Player player ){

        Inventory inventory = Bukkit.createInventory(player, 9, "Merchant Shop");

        //Resistance potion
        ItemStack ResistancePotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta ResistanceMeta = (PotionMeta) ResistancePotion.getItemMeta();
        ResistanceMeta.setColor(Color.fromRGB(139, 128, 227));
        ResistanceMeta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 800, 1, true, true, true),true  );
        ResistanceMeta.setLore(Arrays.asList("Lasts 40 Seconds!", ChatColor.GREEN + "(7xp)"));
        ResistanceMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Resistance");
        ResistancePotion.setItemMeta(ResistanceMeta);
        inventory.addItem(ResistancePotion);
        //Swift potion
        ItemStack SpeedPotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta SpeedMeta = (PotionMeta) SpeedPotion.getItemMeta();
        SpeedMeta.setColor(Color.fromRGB(51, 235, 255));
        SpeedMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 800, 2, true, true, true),true  );
        SpeedMeta.setLore(Arrays.asList("Lasts 40 Seconds!", ChatColor.GREEN + "(7xp)"));
        SpeedMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Swiftness");
        SpeedPotion.setItemMeta(SpeedMeta);
        inventory.addItem(SpeedPotion);
        //Strength Potion
        ItemStack StrengthPotion = new ItemStack(Material.SPLASH_POTION);
        PotionMeta StrengthMeta = (PotionMeta) StrengthPotion.getItemMeta();
        StrengthMeta.setColor(Color.fromRGB(255, 199, 0));
        StrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 0, true, true, true),true  );
        StrengthMeta.setLore(Arrays.asList("Lasts 30 Seconds!", ChatColor.GREEN + "(10xp)"));
        StrengthMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Strength");
        StrengthPotion.setItemMeta(StrengthMeta);
        inventory.addItem(StrengthPotion);
        //Arrows
        ItemStack Arrows = new ItemStack(Material.ARROW, 16);
        ItemMeta ArrowMeta = Arrows.getItemMeta();
        ArrowMeta.setLore(Collections.singletonList(ChatColor.GREEN + "(4xp)"));
        Arrows.setItemMeta(ArrowMeta);
        inventory.addItem(Arrows);
        //Hp Berry
        ItemStack extraHp = new ItemStack(Material.GLOW_BERRIES);
        ItemMeta extraHpMeta = extraHp.getItemMeta();
        extraHpMeta.setDisplayName(ChatColor.GOLD + "+1 MAX HP");
        extraHpMeta.setLore(Collections.singletonList(ChatColor.GREEN + "(10xp)"));
        extraHp.setItemMeta(extraHpMeta);
        inventory.addItem(extraHp);
        //Explosive Charge
        ItemStack explosiveCharge = new ItemStack(Material.GUNPOWDER);
        ItemMeta explosiveChargeMeta = explosiveCharge.getItemMeta();
        explosiveChargeMeta.setDisplayName(ChatColor.GOLD + "Explosive Charge");
        explosiveChargeMeta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "Next time you take damage you release a catastrophic explosion!","1 Use" + ChatColor.GREEN + " (9xp)"));
        explosiveCharge.setItemMeta(explosiveChargeMeta);
        inventory.addItem(explosiveCharge);
        //FireThing
        ItemStack fireRoot = new ItemStack(Material.TORCHFLOWER);
        ItemMeta fireRootMeta = fireRoot.getItemMeta();
        fireRootMeta.setDisplayName(ChatColor.GOLD + "Fire Root");
        fireRootMeta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "Eating this root causes your attacks",ChatColor.LIGHT_PURPLE + "to ignite all enemies permanently! ","Lasts 30 Attacks" + ChatColor.GREEN + " (10xp)"));
        fireRoot.setItemMeta(fireRootMeta);
        inventory.addItem(fireRoot);
        //Echo Shard
        ItemStack echoShield = new ItemStack(Material.ECHO_SHARD);
        ItemMeta echoMeta = echoShield.getItemMeta();
        echoMeta.setDisplayName(ChatColor.DARK_BLUE + "Echo Shield");
        echoMeta.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE + "This crystal makes the next time your hit, damage the enemy instead!","1 Use" + ChatColor.GREEN + " (6xp)"));
        echoShield.setItemMeta(echoMeta);
        inventory.addItem(echoShield);
        //Revive
        ItemStack extraRevive = new ItemStack(Material.NETHER_STAR);
        ItemMeta extraReviveMeta = extraRevive.getItemMeta();
        extraReviveMeta.setDisplayName(ChatColor.BOLD + "+1 Spare Revive");
        extraReviveMeta.setLore(Collections.singletonList(ChatColor.LIGHT_PURPLE + "Revives the player and is consumed on death!" + ChatColor.GREEN + " (50xp)"));
        extraRevive.setItemMeta(extraReviveMeta);
        inventory.addItem(extraRevive);





        player.openInventory(inventory);
    }


}