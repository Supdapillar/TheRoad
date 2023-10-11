package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.LootableReviveTalisman;
import me.supdapillar.theroad.Talisman.ShopEchoShieldTalisman;
import me.supdapillar.theroad.Talisman.ShopFireRootTalisman;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class InventoryInteractionListener implements Listener {
    private TheRoadPlugin mainPlugin;
    public InventoryInteractionListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        mainPlugin = plugin;
    }



    @EventHandler
    public void onInventoryInteraction(InventoryClickEvent event){
        if (event.getClickedInventory() == null) return;
        if (event.getCurrentItem() == null) return;
        Player player = (Player)event.getWhoClicked();

        switch (event.getClickedInventory().getType()){
            case HOPPER:

                //else {
                    //Map selection UI
                    event.setCancelled(true);

                    for (Arena arena : TheRoadPlugin.getInstance().gameManager.gameArenas){
                        if (event.getCurrentItem().getType() == arena.inventoryIcon.getType()){
                            arena.processClick(player);
                        }
                    }
                    StarterItems.refreshMapInventory(player);

                //}



                break;
            case BARREL:
                //Talisman picker
                event.setCancelled(true);
                for (Talisman talisman : TheRoadPlugin.getInstance().talismans){
                    if (event.getCurrentItem().getType() == talisman.inventoryIcon.getType()){
                        talisman.processClick(player);
                    }
                }
                StarterItems.refreshTalismanMenu(player);
                break;
            case CHEST:
                if (event.getView().getTitle().equals(ChatColor.BOLD + "Class Chooser"))
                {
                    //Class picker ui
                    event.setCancelled(true);

                    for (GameClass gameclass : TheRoadPlugin.getInstance().gameClasses){
                        if (event.getCurrentItem().getType() == gameclass.inventoryIcon.getType()){
                            gameclass.processClick(player);
                        }
                    }
                    StarterItems.refreshClassInventory(player);
                }
                else if (event.getView().getTitle().equals("Loot Chest")){
                    if (event.getCurrentItem() == null) return;

                    switch (event.getCurrentItem().getType()){
                        case SUNFLOWER: // Extra money
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                            mainPlugin.PlayerScores.put(player,mainPlugin.PlayerScores.get(player) + event.getCurrentItem().getAmount());
                            player.sendMessage(ChatColor.GREEN + "+" + event.getCurrentItem().getAmount()+"$");
                            ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                            event.getCurrentItem().setAmount(0);
                            break;
                        case SWEET_BERRIES: // Instant heal
                            player.playSound(player, Sound.ENTITY_PLAYER_BURP, 9999 ,1);
                            if (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()-2){
                                player.setHealth(player.getHealth()+2);
                            }
                            else {
                                player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                            }
                            event.getCurrentItem().setAmount(0);
                            break;
                        case GLOW_BERRIES: // Permanent extra heart
                            player.playSound(player, Sound.ENTITY_PLAYER_BURP, 9999 ,1);
                            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                            event.getCurrentItem().setAmount(0);

                            break;
                        case ECHO_SHARD:
                            player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 9999 ,1);
                            TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopEchoShieldTalisman());
                            event.getCurrentItem().setAmount(0);
                            break;
                        case NETHER_STAR: // Extra Revive
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                            TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new LootableReviveTalisman());
                            event.getCurrentItem().setAmount(0);

                            break;
                    }
                }
                else if (event.getView().getTitle().equals("Merchant Shop"))
                {
                    if (event.getCurrentItem() == null) return;
                    event.setCancelled(true);
                    switch (event.getCurrentItem().getType()){
                        case SPLASH_POTION:
                            switch (event.getCurrentItem().getItemMeta().getDisplayName()){
                                case "Splash Potion of Resistance":
                                    if (player.getLevel() >= 7){
                                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                                        player.setLevel(player.getLevel()-7);
                                        ItemStack ResistancePotion = new ItemStack(Material.SPLASH_POTION);
                                        PotionMeta ResistanceMeta = (PotionMeta) ResistancePotion.getItemMeta();
                                        ResistanceMeta.setColor(Color.fromRGB(139, 128, 227));
                                        ResistanceMeta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 1, true, true, true),true  );
                                        ResistanceMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Resistance");
                                        ResistancePotion.setItemMeta(ResistanceMeta);
                                        player.getInventory().addItem(ResistancePotion);
                                    }
                                    else {
                                        player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                        player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                                    }
                                    break;
                                case "Splash Potion of Swiftness":
                                    if (player.getLevel() >= 7){
                                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                                        player.setLevel(player.getLevel()-7);
                                        ItemStack SpeedPotion = new ItemStack(Material.SPLASH_POTION);
                                        PotionMeta SpeedMeta = (PotionMeta) SpeedPotion.getItemMeta();
                                        SpeedMeta.setColor(Color.fromRGB(51, 235, 255));
                                        SpeedMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 600, 2, true, true, true),true  );
                                        SpeedMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Swiftness");
                                        SpeedPotion.setItemMeta(SpeedMeta);
                                        player.getInventory().addItem(SpeedPotion);
                                    }
                                    else {
                                        player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                        player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                                    }
                                    break;
                                case "Splash Potion of Strength":
                                    if (player.getLevel() >= 10){
                                        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                                        player.setLevel(player.getLevel()-10);
                                        ItemStack StrengthPotion = new ItemStack(Material.SPLASH_POTION);
                                        PotionMeta StrengthMeta = (PotionMeta) StrengthPotion.getItemMeta();
                                        StrengthMeta.setColor(Color.fromRGB(255, 199, 0));
                                        StrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0, true, true, true),true  );
                                        StrengthMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Strength");
                                        StrengthPotion.setItemMeta(StrengthMeta);
                                        player.getInventory().addItem(StrengthPotion);
                                    }
                                    else {
                                        player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                        player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                                    }
                                    break;
                                case "Splash Potion of Invisibility":
                                    if (player.getLevel() >= 8){
                                        ItemStack InvisPotion = new ItemStack(Material.SPLASH_POTION);
                                        PotionMeta InvisMeta = (PotionMeta) InvisPotion.getItemMeta();
                                        InvisMeta.setColor(Color.fromRGB(246, 246, 246));
                                        InvisMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 400, 0, true, true, true),true  );
                                        InvisMeta.setDisplayName(ChatColor.RESET + "Splash Potion of Invisibility");
                                        InvisPotion.setItemMeta(InvisMeta);
                                        player.getInventory().addItem(InvisPotion);
                                    }
                                    else {
                                        player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                        player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                                    }
                                    break;
                            }
                            break;
                        case ARROW:
                            if (player.getLevel() >= 4){
                                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                                player.setLevel(player.getLevel()-4);
                                player.getInventory().addItem(new ItemStack(Material.ARROW, 16));
                            }
                            else {
                                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                            }
                            break;
                        case GLOW_BERRIES: // Permanent extra heart
                            if (player.getLevel() >= 10){
                                player.playSound(player, Sound.ENTITY_PLAYER_BURP, 9999 ,1);
                                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                                player.setLevel(player.getLevel() - 10);
                            }
                            else {
                                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                            }
                            break;
                        case TORCHFLOWER: // Permanent extra heart
                            if (player.getLevel() >= 10){
                                player.playSound(player, Sound.ENTITY_PLAYER_BURP, 9999 ,1);
                                player.setLevel(player.getLevel() - 10);
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopFireRootTalisman());
                            }
                            else {
                                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                            }
                            break;
                        case ECHO_SHARD: // Permanent extra heart
                            if (player.getLevel() >= 6){
                                player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 9999 ,1);
                                player.setLevel(player.getLevel() - 6);
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new ShopEchoShieldTalisman());
                            }
                            else {
                                player.playSound(player,Sound.ENTITY_VILLAGER_NO,999,1);
                                player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                            }
                            break;

                        case NETHER_STAR: // Extra Revive
                            if (player.getLevel() >= 50){
                                player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 9999 ,1);
                                player.setLevel(player.getLevel() - 50);
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new LootableReviveTalisman());
                            }
                            else {
                                player.playSound(player,Sound.ENTITY_EXPERIENCE_ORB_PICKUP,999,1);
                                player.sendMessage(ChatColor.RED + "You don't have enough xp!");
                            }


                            break;
                    }
                }
                break;

        }

    }
}
