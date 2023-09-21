package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {
    public EntityRegainHealthListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }




    @EventHandler
    public void onEntityReganHealth(EntityRegainHealthEvent event){
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();


        //Talisman
        for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
            talisman.onPlayerHealthRegain(event);
        }


    }
}
