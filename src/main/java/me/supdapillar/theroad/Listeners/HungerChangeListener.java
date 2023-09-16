package me.supdapillar.theroad.Listeners;

import jdk.tools.jlink.plugin.Plugin;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerChangeListener implements Listener {
    public HungerChangeListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }




    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent event){
        event.getEntity().setFoodLevel(20);
        event.setCancelled(true);
    }
}
