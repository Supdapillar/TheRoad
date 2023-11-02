package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Tasks.BeaconEventLoop;
import me.supdapillar.theroad.Tasks.CounterLoop;
import me.supdapillar.theroad.Tasks.CursedTreasureEventLoop;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
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
        //Cursed Treasure
        if (event.getInventory().getType() == InventoryType.BLAST_FURNACE){
            Player player = (Player) event.getPlayer();
            ArmorStand armorStand = null;
            NamespacedKey cursedKey = new NamespacedKey(TheRoadPlugin.getInstance(),"CanUseTreasure");
            //Gets the armorstand
            for (Entity entity : player.getNearbyEntities(5,5,5)){
                if (entity.getPersistentDataContainer().has(cursedKey, PersistentDataType.BOOLEAN)){
                    armorStand = (ArmorStand) entity;
                }
            }
            //Check if cursed treasure can be summoned
            if (armorStand.getPersistentDataContainer().get(cursedKey, PersistentDataType.BOOLEAN)){
                if (CursedTreasureEventLoop.activeCursedTreasure == null){
                    //Start timer
                    player.getWorld().playSound(player, Sound.ENTITY_GUARDIAN_DEATH, 9999, 0.5f);
                    armorStand.getPersistentDataContainer().set(cursedKey, PersistentDataType.BOOLEAN, false);
                    new CursedTreasureEventLoop(armorStand).runTaskTimer(TheRoadPlugin.getInstance(),0,10);
                }
                else {

                    Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "A Cursed Treasure is already active!");
                }
            }
            else {
                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "The Cursed Treasure has already been activated!");
            }
            event.setCancelled(true);
        }
        //Beacon stuff
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

            //Gets the armorstand
            for (Entity entity : player.getNearbyEntities(5,5,5)){
                if (entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.BOOLEAN)){
                    armorStand = (ArmorStand) entity;
                }
            }

            //Check if the reviving process can be started
            if (Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.SPECTATOR).toArray().length > 0){ //Checks if it can start
                if (armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN)){
                    TheRoadPlugin.getInstance().respawnBeaconActive = true;

                    int SoulsNeeded = (int) Math.floor(Math.random()*3+5) + (Bukkit.getOnlinePlayers().size() );
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
