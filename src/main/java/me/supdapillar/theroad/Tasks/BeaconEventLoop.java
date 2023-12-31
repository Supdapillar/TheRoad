package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class BeaconEventLoop extends BukkitRunnable {

    public ArmorStand centerPointArmorStand = null;
    public int SoulsNeeded = 0;
    public static BeaconEventLoop beaconEventLoop = null;

    private float counter = 0;

    public BeaconEventLoop(ArmorStand armorStand){

        centerPointArmorStand = armorStand;
        beaconEventLoop = this;
    }
    @Override
    public void run() {
        counter += 0.25f;

        if (SoulsNeeded > 0) {
            //Spawn Particles
            Location centerLocation = centerPointArmorStand.getLocation();
            double Angle = 0;
            for (int i = 0; i < 50; i++) {
                Angle -= Math.PI / 25f;

                Location particleLocation = new Location(centerLocation.getWorld(), centerLocation.getX() + (Math.cos(Angle) * 12f), centerLocation.getY(), centerLocation.getZ() + (Math.sin(Angle) * 12f));
                centerLocation.getWorld().spawnParticle(Particle.END_ROD, particleLocation, 3, 0, 3, 0, 0);
            }

            //Push players back in
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() != GameMode.SPECTATOR) {
                    if ((player.getLocation().getY() < centerPointArmorStand.getLocation().getY() + 10)) {
                        if (player.getWorld() == centerPointArmorStand.getWorld()){
                            if (player.getLocation().distance(centerPointArmorStand.getLocation()) > 12) {
                                Location PlayerLocation = player.getLocation();
                                Location ArmorstandLocation = centerPointArmorStand.getLocation();
                                Vector vector = new Vector(PlayerLocation.getX() - ArmorstandLocation.getX(), -2, PlayerLocation.getZ() - ArmorstandLocation.getZ()).normalize();

                                player.setVelocity(vector.multiply(-0.75f));
                            }
                        }
                    }
                }
            }

            //Spawn Enemies
            if (Math.random() < 0.085f) {
                Angle = (Math.PI * 2) * Math.random();
                Location ArmorstandLocation = centerPointArmorStand.getLocation();

                Location zombieSpawnLocation = new Location(ArmorstandLocation.getWorld(), ArmorstandLocation.getX() + Math.cos(Angle) * 11, ArmorstandLocation.getY(), ArmorstandLocation.getZ() + Math.sin(Angle) * 11);

                while(zombieSpawnLocation.getBlock().getType() != Material.AIR){
                    Angle = (Math.PI * 2) * Math.random();
                    zombieSpawnLocation =new Location(ArmorstandLocation.getWorld(), ArmorstandLocation.getX() + Math.cos(Angle) * 11, ArmorstandLocation.getY(), ArmorstandLocation.getZ() + Math.sin(Angle) * 11);
                }

                Zombie zombie = (Zombie) centerPointArmorStand.getWorld().spawnEntity(zombieSpawnLocation, EntityType.ZOMBIE);
                zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20 + Math.ceil(counter / 20));
                zombie.setHealth(20 + Math.ceil(counter / 20));
                zombie.damage(0);
            }
        }
        else { // Ends the event
            //Every update revive a new player
            if (Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.SPECTATOR).toArray().length > 0){
                //Plays a sound for the players
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.playSound(player,Sound.ENTITY_GENERIC_EXPLODE, 9999, 0.75f);
                }
                //Spawn Particles
                Location centerLocation = centerPointArmorStand.getLocation();
                double Angle = 0;
                for (int i = 0; i < 100; i++) {
                    Angle -= Math.PI / 50f;

                    Location particleLocation = new Location(centerLocation.getWorld(), centerLocation.getX() + (Math.cos(Angle) * 12f), centerLocation.getY(), centerLocation.getZ() + (Math.sin(Angle) * 12f));
                    centerLocation.getWorld().spawnParticle(Particle.LAVA, particleLocation, 8, 0, 3, 0, 0);
                }
                //Respawning the player
                List<Player> deadPlayers = new ArrayList<Player>();
                for(Player player : Bukkit.getOnlinePlayers()){
                    if (player.getGameMode() == GameMode.SPECTATOR){
                        deadPlayers.add(player);
                    }
                }
                Random random = new Random();
                TheRoadPlugin.getInstance().gameManager.respawnPlayer(deadPlayers.get(random.nextInt(deadPlayers.size())),centerPointArmorStand.getLocation());
            }
            //Ends the event
            if (Bukkit.getOnlinePlayers().stream().filter(o -> o.getGameMode() == GameMode.SPECTATOR).toArray().length == 0){
                SoulsNeeded = 0;
                centerPointArmorStand.setCustomName(ChatColor.GRAY + "RESPAWN BEACON ALREADY USED");
                centerPointArmorStand.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "IsAbleToRespawn"), PersistentDataType.BOOLEAN, false);


                BeaconEventLoop.beaconEventLoop.cancel();
                BeaconEventLoop.beaconEventLoop = null;
            }
        }
    }
}
