package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {
    public EntityChangeBlockListener (TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }




    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event){
    }
}
