package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
                else {
                    //Map selection UI
                    event.setCancelled(true);

                    for (Arena arena : TheRoadPlugin.getInstance().gameManager.gameArenas){
                        if (event.getCurrentItem().getType() == arena.inventoryIcon.getType()){
                            arena.processClick(player);
                        }
                    }
                    StarterItems.refreshMapInventory(player);

                }



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
        }

    }
}
