package me.supdapillar.theroad.Helpers;

import me.supdapillar.theroad.Talisman.LootableReviveTalisman;
import me.supdapillar.theroad.Talisman.ShopEchoShieldTalisman;
import me.supdapillar.theroad.Talisman.ShopExplosiveChargeTalisman;
import me.supdapillar.theroad.Talisman.ShopFireRootTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ConjuringShrineHelper {

    private static Random random = new Random();
    public static HashMap<String, Integer> itemCosts = new HashMap<String, Integer>() {{
        put("Golden Apple",15);
        put("Echo Shield",12);
        put("Explosive Charge",20);
        put("Spare Revive",60);
        put("Bundle Of Arrows",5);
        put("Healing Potion",11);
        put("Fire Root",12);
    }};




    public static void generateShrines(World world){
        for (TextDisplay textDisplay : world.getEntitiesByClass(TextDisplay.class)){
            if (textDisplay.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(),"ShrineItem"), PersistentDataType.STRING )){
                ArrayList<String> allNames = new ArrayList<>();
                for(String s : itemCosts.keySet()){
                    allNames.add(s);
                }
                int RandomIndex = random.nextInt(allNames.size());
                textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "CONJURING SHRINE [CLICK] FOR A " +ChatColor.RESET + allNames.get(RandomIndex).toUpperCase() +" " + ChatColor.BLUE + itemCosts.get(allNames.get(RandomIndex))+"XP");
                textDisplay.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(),"ShrineItem"),PersistentDataType.STRING, allNames.get(RandomIndex));

                makeShrineCollision(textDisplay.getLocation());
                makeShrineDisplay(textDisplay, textDisplay.getPersistentDataContainer().get(new NamespacedKey(TheRoadPlugin.getInstance(), "ShrineItem"),PersistentDataType.STRING    ));


            }
        }
    }

    public static void makeShrineCollision(Location location){
        //Collision box
        BlockData barrelData = Material.BARRIER.createBlockData();
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location.subtract(0,1,0), barrelData);
        fallingBlock.setVelocity(new Vector(0,0,0));
        fallingBlock.setGravity(false);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setRotation(90,33);
        fallingBlock.setPersistent(true);
        fallingBlock.setCancelDrop(true);
        fallingBlock.setDropItem(true);
    }

    public static void makeShrineDisplay(TextDisplay textDisplay,String string){
        ItemDisplay itemDisplay = (ItemDisplay) textDisplay.getWorld().spawnEntity(textDisplay.getLocation().subtract(0,0.5,0), EntityType.ITEM_DISPLAY,true);
        itemDisplay.setItemStack(makeItemFromString(string));
        Transformation transformation = itemDisplay.getTransformation();
        itemDisplay.setRotation(textDisplay.getLocation().getYaw(),0);
        itemDisplay.setTransformation(transformation);
    }

    public static ItemStack makeItemFromString(String string){
        ItemStack chosenItem = new ItemStack(Material.AIR);

        switch (string){
            case "Golden Apple":
                chosenItem = new ItemStack(Material.GOLDEN_APPLE,1);
                break;
            case "Echo Shield":
                chosenItem = new ItemStack(Material.ECHO_SHARD);
                break;
            case "Explosive Charge":
                chosenItem = new ItemStack(Material.GUNPOWDER);
                break;
            case "Spare Revive":
                chosenItem = new ItemStack(Material.NETHER_STAR);
                break;
            case "Bundle Of Arrows":
                chosenItem = new ItemStack(Material.ARROW,16);
                break;
            case "Healing Potion":
                chosenItem = new ItemStack(Material.POTION);
                PotionMeta potionMeta = (PotionMeta) chosenItem.getItemMeta();
                potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
                chosenItem.setItemMeta(potionMeta);
                break;
            case "Fire Root":
                chosenItem = new ItemStack(Material.TORCHFLOWER);
                break;
        }
        return chosenItem;
    }
    public static void processClick(Player player, TextDisplay textDisplay){
        String item = textDisplay.getPersistentDataContainer().get(new NamespacedKey(TheRoadPlugin.getInstance(),"ShrineItem"), PersistentDataType.STRING);
        int Cost = itemCosts.get(item);

        if (player.getLevel() >= Cost){
            player.playSound(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,0.75f);
            player.sendMessage(ChatColor.GREEN + "You purchased a " + item +"!");
            player.setLevel(player.getLevel() - Cost);
            switch (item){
                case "Golden Apple":
                    player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                    break;
                case "Echo Shield":
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopEchoShieldTalisman());
                    break;
                case "Explosive Charge":
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopExplosiveChargeTalisman());
                    break;
                case "Spare Revive":
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new LootableReviveTalisman());
                    break;
                case "Bundle Of Arrows":
                    player.getInventory().addItem(new ItemStack(Material.ARROW,16));
                    break;
                case "Healing Potion":
                    ItemStack HealingPotion = new ItemStack(Material.POTION);
                    PotionMeta potionMeta = (PotionMeta) HealingPotion.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
                    HealingPotion.setItemMeta(potionMeta);
                    player.getInventory().addItem(new ItemStack(HealingPotion));
                    break;
                case "Fire Root":
                    TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopFireRootTalisman());
                    break;
            }
        }
        else {
            player.playSound(player,Sound.ENTITY_VILLAGER_NO, 999,1);
            player.sendMessage(ChatColor.RED + "You don't have enough xp for this item!");
        }
    }
}
