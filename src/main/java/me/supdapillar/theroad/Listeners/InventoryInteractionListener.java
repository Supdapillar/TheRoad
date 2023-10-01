package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.LootableReviveTalisman;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
                        case NETHER_STAR: // Extra Revive
                            player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 9999 ,1);
                            TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new LootableReviveTalisman());
                            event.getCurrentItem().setAmount(0);

                            break;
                    }
                }
                break;

        }

    }
}
