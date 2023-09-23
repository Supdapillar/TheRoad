package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class InventoryOpenListener implements Listener {
    public  InventoryOpenListener(TheRoadPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this,  plugin);
    }
}
