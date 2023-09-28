package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Tasks.BeaconEventLoop;
import me.supdapillar.theroad.Tasks.CounterLoop;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataType;

public class InventoryOpenListener implements Listener {
    public  InventoryOpenListener(TheRoadPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this,  plugin);
    }



    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        if (event.getInventory().getType() != InventoryType.BEACON) return;

        //Tells the player its active
        if (TheRoadPlugin.getInstance().respawnBeaconActive){
            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "The Beacon is already active!");
            event.setCancelled(true);
        }
        else
        {
            Player player = (Player) event.getPlayer();

            ArmorStand armorStand = null;
            NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(),"IsAbleToRespawn");

            for (Entity entity : player.getNearbyEntities(7,7,7)){
                if (entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.BOOLEAN)){
                    armorStand = (ArmorStand) entity;
                }
            }

            //Check if the reviving process can be started
            if (Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.SPECTATOR).toArray().length > 0){ //Checks if it can start
                if (armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN)){
                    TheRoadPlugin.getInstance().respawnBeaconActive = true;

                    int SoulsNeeded = (int) Math.floor( Math.random()*15+23);
                    SoulsNeeded = 4;
                    armorStand.setCustomName(ChatColor.LIGHT_PURPLE + "Souls Needed: " + SoulsNeeded);

                    TheRoadPlugin.getInstance().beaconEventLoop.SoulsNeeded = SoulsNeeded;
                    TheRoadPlugin.getInstance().beaconEventLoop.centerPointArmorStand = armorStand;
                    TheRoadPlugin.getInstance().beaconEventLoop.runTaskTimer(TheRoadPlugin.getInstance(), 0 , 5);
                }
                else {
                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "The respawn beacon has already been used!");
                }
            }
            else {
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "The respawn beacon is not needed!");
            }
            event.setCancelled(true);
        }

    }
}
