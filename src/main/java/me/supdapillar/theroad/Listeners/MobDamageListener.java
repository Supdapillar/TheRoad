package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class MobDamageListener implements Listener {
    public MobDamageListener(TheRoadPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }



    @EventHandler
    public void onMobDamage(EntityDamageEvent event){
        if (!(event.getEntity() instanceof Mob)) return;
        if (event.getEntity() instanceof Player) return;
        Mob mobEntity = (Mob) event.getEntity();



        //For tamed mobs
        if (mobEntity instanceof Tameable) {
            mobEntity.setCustomName(ChatColor.BLUE + "[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }
        // everything that isnt a summon or a boss
        else if (!mobEntity.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(), "IsBoss"),PersistentDataType.BOOLEAN))
        {
            mobEntity.setCustomName("[" + Math.ceil(mobEntity.getHealth() - event.getDamage()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }
    }
}
