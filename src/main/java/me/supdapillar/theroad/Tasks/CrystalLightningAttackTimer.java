package me.supdapillar.theroad.Tasks;

import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Random;

public class CrystalLightningAttackTimer extends BukkitRunnable {

    private ArrayList<Mob> alreadyHitMobs = new ArrayList<Mob>();
    private Player shotBy;
    public CrystalLightningAttackTimer(Player player){
        shotBy = player;
    }

    @Override
    public void run() {


        Entity lightningStart = shotBy;

        if (!alreadyHitMobs.isEmpty()){
            lightningStart = alreadyHitMobs.get(alreadyHitMobs.size()-1);
            Bukkit.broadcastMessage("the start is a mob");
        }

        ArrayList<Mob> nearbyMobs = new ArrayList<>();
        for (Entity entity : lightningStart.getNearbyEntities(10, 10, 10)){
            if (entity instanceof Mob){
                Mob mob = (Mob) entity;
                if (!alreadyHitMobs.contains(mob)){
                    nearbyMobs.add(mob);
                }
            }
        }





        //Finds the nearest enemy
        Mob nearestMob = null;
        //Checks that it hasnt hit its cap
        if (alreadyHitMobs.size() < 10 && !nearbyMobs.isEmpty()){
            //gets all entities
            for(Mob mob  : nearbyMobs){
                //Cant hit the same mob twice
                if (!alreadyHitMobs.contains(mob)){
                    //Makes sure that it has the nearest mob
                    if (nearestMob == null){
                        nearestMob = mob;
                    }
                    //Gets the closer mob
                    else if (nearestMob.getLocation().distance(lightningStart.getLocation()) > mob.getLocation().distance(lightningStart.getLocation()) ) {
                        nearestMob = mob;
                    }
                }

            }

        }
        else {
            this.cancel();
        }


        if (nearestMob != null){
            //The Attack
            alreadyHitMobs.add(nearestMob);
            nearestMob.damage(5, shotBy);

            //Particle line
            Vector vector = new Vector(nearestMob.getLocation().subtract(lightningStart.getLocation()).getX(), nearestMob.getLocation().subtract(lightningStart.getLocation()).getY(), nearestMob.getLocation().subtract(lightningStart.getLocation()).getZ());
            Location startLocation = lightningStart.getLocation().add(0, 0.5, 0);
            //Spawn particles
            for(int i = 0; i < (int)startLocation.distance(nearestMob.getLocation()) * 4; i++){
                Location particleLocation = new Location(startLocation.getWorld(), startLocation.getX() + vector.normalize().getX() * i/4, startLocation.getY() +  vector.normalize().getY() * i/4,startLocation.getZ() +  vector.normalize().getZ() * i/4);
                particleLocation.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,2,0.3,0.3,0.3, new Particle.DustOptions(Color.PURPLE, 1));
                particleLocation.getWorld().spawnParticle(Particle.SPELL_WITCH,particleLocation ,1, 0.3,0.3,0.3,0);
            }

        }
    }
}
