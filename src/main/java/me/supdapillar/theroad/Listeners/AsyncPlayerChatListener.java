package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;

public class AsyncPlayerChatListener implements Listener {
    public AsyncPlayerChatListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        if (event.getPlayer().getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(),"Supporter"), PersistentDataType.BOOLEAN)){
            event.setFormat(ChatColor.GOLD+ "⭐<" + event.getPlayer().getName() + "> " + event.getMessage());
        }
    }
}
