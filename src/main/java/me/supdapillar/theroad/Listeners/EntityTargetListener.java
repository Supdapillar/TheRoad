package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Random;

public class EntityTargetListener implements Listener {
    public EntityTargetListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onEntityTarget(EntityTargetEvent event){
        if (event.getEntity() instanceof Tameable) return;
        if (event.getTarget() == null){

            Object[] array = Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.ADVENTURE).toArray();
            Random random = new Random();
            event.setTarget((LivingEntity) array[random.nextInt(array.length)]);
        }
    }
}
