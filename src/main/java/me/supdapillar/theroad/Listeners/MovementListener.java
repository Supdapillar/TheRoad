package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Managers.GameManager;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.Objects;

public class MovementListener implements Listener {
    public MovementListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }




    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        GameManager gameManager = TheRoadPlugin.getInstance().gameManager;

        //Talisman
        if (TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player) != null){
            for(Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player)){
                talisman.onPlayerMove(event);
            }
        }

        if (player.getGameMode() == GameMode.CREATIVE){
            for (Entity entity : player.getWorld().getEntities()){
                if ((entity instanceof ArmorStand)) {

                    ArmorStand armorstand = (ArmorStand) entity;
                    NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(),"BlockageRound");
                    NamespacedKey namespacedKey2 = new NamespacedKey(TheRoadPlugin.getInstance(),"Round");
                    NamespacedKey namespacedKey3 = new NamespacedKey(TheRoadPlugin.getInstance(),"SpawnChance");

                    if (armorstand.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)){
                        player.spawnParticle(Particle.REDSTONE,entity.getLocation(),5, new Particle.DustOptions(Color.FUCHSIA, 2));

                    }
                    else if (armorstand.getPersistentDataContainer().has(namespacedKey2,PersistentDataType.INTEGER))
                    {
                        if (armorstand.getPersistentDataContainer().get(namespacedKey2, PersistentDataType.INTEGER) % 2 ==0){
                            player.spawnParticle(Particle.REDSTONE,entity.getLocation(),5, new Particle.DustOptions(Color.BLUE, 2));

                        }
                        else {
                            player.spawnParticle(Particle.REDSTONE,entity.getLocation(),5, new Particle.DustOptions(Color.YELLOW, 2));
                        }
                    }else if (armorstand.getPersistentDataContainer().has(namespacedKey3, PersistentDataType.INTEGER))
                    {
                        player.spawnParticle(Particle.REDSTONE,entity.getLocation(),5, new Particle.DustOptions(Color.GREEN, 2));

                    }
                }
            }
        }
        else {
            for (Entity entity : player.getWorld().getEntities()){
                if ((entity instanceof ArmorStand)) {

                    ArmorStand armorstand = (ArmorStand) entity;
                    armorstand.setGlowing(false);
                }
            }
        }

        //Loop all the armor stands
        for(Entity entity : player.getWorld().getEntities()){
            if (entity instanceof ArmorStand){
                ArmorStand armorStand = (ArmorStand) entity;
                NamespacedKey namespacedKey = new NamespacedKey(TheRoadPlugin.getInstance(),"BlockageRound");
                if (armorStand.getPersistentDataContainer().has(namespacedKey, PersistentDataType.INTEGER)){
                    if (armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) >= TheRoadPlugin.getInstance().gameManager.currentRound){
                        NamespacedKey namespacedKeyAxis = new NamespacedKey(TheRoadPlugin.getInstance(),"Axis");
                        float DistanceTilParticles = 9;
                        int length = armorStand.getPersistentDataContainer().get(new NamespacedKey(TheRoadPlugin.getInstance(), "Length"), PersistentDataType.INTEGER);
                        //X axis check
                        if (Objects.equals(armorStand.getPersistentDataContainer().get(namespacedKeyAxis, PersistentDataType.STRING), "X")){ // X Axis Check
                            if (event.getTo().getZ() > armorStand.getLocation().getZ()-DistanceTilParticles && event.getTo().getZ() < armorStand.getLocation().getZ()+DistanceTilParticles){
                                boolean withinX = event.getTo().getX() > armorStand.getLocation().getX()-length && event.getTo().getX() < armorStand.getLocation().getX()+length;
                                boolean withinZ = event.getTo().getZ() > armorStand.getLocation().getZ()-0.75f && event.getTo().getZ() < armorStand.getLocation().getZ()+0.75f;
                                boolean withinY = event.getTo().getY() < armorStand.getLocation().getY() + 4 && event.getTo().getY() > armorStand.getLocation().getY()-1;

                                if (withinX && withinZ && withinY){
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.YELLOW + "This gate opens after round " + armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) + "!");
                                    player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT, 9999, 0.77f);
                                    player.setVelocity(new Vector(0f, 0.05f, event.getFrom().getZ() - event.getTo().getZ()).normalize());
                                }
                                else {//show particles
                                    Location asLocation = armorStand.getLocation();
                                    for (int i = 1; i < length+1; i++){
                                        Particle.DustOptions dustOptions;
                                        Location randomLocation = new Location(asLocation.getWorld(),asLocation.getX()+(-length+(i*2)+(Math.random()*2)) - 2f,asLocation.getY()+Math.random()*4, asLocation.getZ());

                                        if (Math.abs(randomLocation.getX() - randomLocation.getY()) % 4 < 1){
                                            dustOptions = new Particle.DustOptions(Color.YELLOW, 3);
                                        }
                                        else {
                                            dustOptions = new Particle.DustOptions(Color.BLACK, 3);
                                        }

                                        player.spawnParticle(Particle.REDSTONE,randomLocation ,1, dustOptions);
                                    }
                                }
                            }
                        }
                        else { // Z Axis Check
                            if (event.getTo().getX() > armorStand.getLocation().getX()-DistanceTilParticles && event.getTo().getX() < armorStand.getLocation().getX()+DistanceTilParticles){
                                boolean withinX = event.getTo().getX() > armorStand.getLocation().getX()-0.75f && event.getTo().getX() < armorStand.getLocation().getX()+0.75f;
                                boolean withinZ = event.getTo().getZ() > armorStand.getLocation().getZ()-length && event.getTo().getZ() < armorStand.getLocation().getZ()+length;
                                boolean withinY = event.getTo().getY() < armorStand.getLocation().getY() + 4 && event.getTo().getY() > armorStand.getLocation().getY()-1;

                                if (withinX && withinZ && withinY){
                                    event.setCancelled(true);
                                    player.sendMessage(ChatColor.YELLOW + "This gate opens after round " + armorStand.getPersistentDataContainer().get(namespacedKey, PersistentDataType.INTEGER) + "!");
                                    player.playSound(player,Sound.BLOCK_NOTE_BLOCK_BIT, 9999, 0.77f);
                                    player.setVelocity(new Vector(event.getFrom().getX() - event.getTo().getX(), 0.05f, 0f).normalize());
                                }
                                else {//show particles
                                    Location asLocation = armorStand.getLocation();
                                    for (int i = 1; i < length+1; i++){
                                        Particle.DustOptions dustOptions;
                                        Location randomLocation = new Location(asLocation.getWorld(),asLocation.getX(),asLocation.getY()+Math.random()*4, asLocation.getZ()+(-length+(i*2)+(Math.random()*2)) - 2f);
                                        if (Math.abs(randomLocation.getZ() - randomLocation.getY()) % 4 < 1){
                                            dustOptions = new Particle.DustOptions(Color.YELLOW, 3);
                                        }
                                        else {
                                            dustOptions = new Particle.DustOptions(Color.BLACK, 3);
                                        }
                                        player.spawnParticle(Particle.REDSTONE,randomLocation ,1, dustOptions);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
