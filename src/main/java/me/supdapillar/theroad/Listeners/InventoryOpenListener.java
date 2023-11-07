package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Talisman.Challenges.FistChallengeTalisman;
import me.supdapillar.theroad.Talisman.Challenges.HealingTouchChallengeTalisman;
import me.supdapillar.theroad.Talisman.Challenges.PoisonChallengeTalisman;
import me.supdapillar.theroad.Talisman.Challenges.WeaknessChallengeTalisman;
import me.supdapillar.theroad.Tasks.BeaconEventLoop;
import me.supdapillar.theroad.Tasks.CounterLoop;
import me.supdapillar.theroad.Tasks.CursedTreasureEventLoop;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.block.Beacon;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class InventoryOpenListener implements Listener {
    public  InventoryOpenListener(TheRoadPlugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this,  plugin);
    }



    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event){
        //Challenge
        if (event.getInventory().getType() == InventoryType.SHULKER_BOX){
            Player player = (Player) event.getPlayer();
            ArmorStand armorStand = null;
            NamespacedKey challengeKey = new NamespacedKey(TheRoadPlugin.getInstance(),"ChallengeType");

            //Gets the armorstand
            for (Entity entity : player.getNearbyEntities(5,5,5)){
                if (entity.getPersistentDataContainer().has(challengeKey, PersistentDataType.STRING)){
                    armorStand = (ArmorStand) entity;
                }
            }
            if (player.getGameMode() == GameMode.ADVENTURE){
                if (armorStand != null){
                    if (TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).stream().filter(o -> o.isChallenge).toArray().length > 0){
                        player.sendMessage(ChatColor.RED + "You've already activated a challenge this round!");
                    }
                    else {
                        //Activates the challenge
                        player.playSound(player, Sound.ENTITY_ELDER_GUARDIAN_CURSE, 6, 1);
                        switch (armorStand.getPersistentDataContainer().get(challengeKey, PersistentDataType.STRING)){
                            case "Poison":
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new PoisonChallengeTalisman(player)   );
                                break;
                            case "Fist":
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new FistChallengeTalisman(player)   );
                                break;
                            case "Healing":
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new HealingTouchChallengeTalisman(player)   );
                                break;
                            case "Weakness":
                                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player).add(new WeaknessChallengeTalisman(player)   );
                                break;
                        }
                    }
                }
            }
            event.setCancelled(true);
        }
        //Cursed Treasure
        else if (event.getInventory().getType() == InventoryType.BLAST_FURNACE){
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
            if (player.getGameMode() == GameMode.ADVENTURE){
                if (armorStand.getPersistentDataContainer().get(cursedKey, PersistentDataType.BOOLEAN)){
                    if (CursedTreasureEventLoop.activeCursedTreasure == null){
                        //Start timer
                        player.getWorld().playSound(player, Sound.ENTITY_GUARDIAN_DEATH, 9999, 0.5f);
                        armorStand.getPersistentDataContainer().set(cursedKey, PersistentDataType.BOOLEAN, false);
                        new CursedTreasureEventLoop(armorStand).runTaskTimer(TheRoadPlugin.getInstance(),0,10);
                        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + player.getDisplayName() + " Activated a Cursed Treasure event!");
                    }
                    else {

                        player.sendMessage(ChatColor.LIGHT_PURPLE + "A Cursed Treasure is already active!");
                    }
                }
                else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "The Cursed Treasure has already been activated!");
                }
            }
            event.setCancelled(true);
        }








        //Beacon stuff
        if (event.getInventory().getType() != InventoryType.BEACON) return;
        Player player = (Player) event.getPlayer();

        //Tells the player its active
        if (BeaconEventLoop.beaconEventLoop != null){
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "The Beacon is already active!");
            event.setCancelled(true);
        }
        else
        {

            ArmorStand armorStand = null;
            NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(),"IsAbleToRespawn");

            //Gets the armorstand
            for (Entity entity : player.getNearbyEntities(5,5,5)){
                if (entity.getPersistentDataContainer().has(namespacedKey, PersistentDataType.BOOLEAN)){
                    armorStand = (ArmorStand) entity;
                }
            }

            boolean arePlayersCloseEnough = true;
            for (Player checkedPlayer : Bukkit.getOnlinePlayers()){
                if (checkedPlayer.getWorld() == armorStand.getWorld()){
                    if (!(checkedPlayer.getLocation().distance(armorStand.getLocation()) < 12)){
                        if (checkedPlayer.getGameMode() == GameMode.ADVENTURE){
                            arePlayersCloseEnough = false;
                        }
                    }
                }
                else {
                    arePlayersCloseEnough = false;
                }

            }

            //Check if the reviving process can be started
            if (player.getGameMode() == GameMode.ADVENTURE) {
                if (Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.SPECTATOR).toArray().length > 0){ //Checks if it can start
                    if (BeaconEventLoop.beaconEventLoop == null){
                        if (armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.BOOLEAN)){
                            if (arePlayersCloseEnough){
                                Random random = new Random();



                                int SoulsNeeded = random.nextInt(4,6);
                                for (Player player1 : Bukkit.getOnlinePlayers()){
                                    if (player1.getGameMode() == GameMode.SPECTATOR){
                                        SoulsNeeded+=random.nextInt(2,4);
                                    }
                                }
                                armorStand.setCustomName(ChatColor.LIGHT_PURPLE + "Souls Needed: " + SoulsNeeded);

                                new BeaconEventLoop(armorStand);
                                BeaconEventLoop.beaconEventLoop.SoulsNeeded = SoulsNeeded;
                                BeaconEventLoop.beaconEventLoop.runTaskTimer(TheRoadPlugin.getInstance(), 0 , 5);
                            }
                            else {
                                player.sendMessage(ChatColor.LIGHT_PURPLE + "Not all players are close enough!");
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.LIGHT_PURPLE + "The respawn beacon has already been used!");
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "A respawn beacon is already active!");

                    }
                }
                else {
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "The respawn beacon is not needed!");
                }
            }
            event.setCancelled(true);
        }

    }
}
