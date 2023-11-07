package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
    public EntityExplodeListener(TheRoadPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }




    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if (event.getEntity() instanceof WitherSkull)
        {
            event.setCancelled(true);
        }
        if (event.getEntity() instanceof Creeper){
            event.setCancelled(true);
        }
    }
}
