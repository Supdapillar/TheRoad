package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.CritTalisman;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobDeathListener implements Listener {
    private TheRoadPlugin mainPlugin;
    public static final HashMap<EntityType, Integer> moneyValues = new HashMap<EntityType, Integer>() {{
        put(EntityType.ZOMBIE, 5);
        put(EntityType.SKELETON, 10);
        put(EntityType.WITHER_SKELETON, 15);
    }};
    public MobDeathListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        mainPlugin = plugin;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        Bukkit.broadcastMessage("" + event.getEntity().getKiller());
        Bukkit.broadcastMessage("" + event.getEntity().getKiller());

        Player player = null;

        NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "killer");
        if (event.getEntity().getPersistentDataContainer().has(namespacedKey, PersistentDataType.STRING))
        {
            player = Bukkit.getPlayer(event.getEntity().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING));
        }
        else if (event.getEntity().getKiller() != null)
        {
            player = event.getEntity().getKiller();
        }





        if (player != null){
            mainPlugin.PlayerScores.putIfAbsent(player, 0);

            mainPlugin.PlayerScores.put(player,mainPlugin.PlayerScores.get(player) + moneyValues.get(event.getEntity().getType()));
            ScoreboardHandler.updateScoreboard(mainPlugin);
            player.sendMessage(ChatColor.GREEN + "+" + moneyValues.get(event.getEntity().getType())+"$");
            //Talisman
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                talisman.onMobDeath(event);
            }
        }
    }
}
