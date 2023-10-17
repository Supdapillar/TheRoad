package me.supdapillar.theroad.Tasks;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class FireShotgunAttackTimer extends BukkitRunnable {

    private Player firedBy;
    public FireShotgunAttackTimer(Player player){
        firedBy = player;
    }

    @Override
    public void run() {

        int mobsHit = 0;
        //Calculate the mobs to hit
        for (Entity entity :firedBy.getNearbyEntities(10,10,10)){
            if (mobsHit > 7) continue;
            if (entity instanceof Mob){
                if (firedBy.hasLineOfSight(entity)){
                    double distance = entity.getLocation().distance(firedBy.getLocation());
                    if (distance < 10){
                        Mob mob = (Mob) entity;
                        mob.damage(25-(distance*2.5f));
                        mob.setFireTicks(9999);
                        mobsHit++;

                        Vector vector = new Vector(mob.getLocation().subtract(firedBy.getLocation()).getX(), 0.65f, mob.getLocation().subtract(firedBy.getLocation()).getZ());
                        mob.setVelocity(vector.normalize().multiply(1.1));
                    }
                }
            }
        }

        //Makes the particles
        double startingAngle = Math.toRadians(firedBy.getLocation().getYaw()) + Math.PI/8;
        for (int i = 0; i < 15; i++){
            startingAngle +=( Math.PI - (Math.PI/4))/15;
            for (int d = 0; d< 10; d++){
                Location particleLocation = new Location(firedBy.getWorld(), firedBy.getLocation().getX() + (Math.cos(startingAngle) * d), firedBy.getLocation().getY()+0.5, firedBy.getLocation().getZ() + (Math.sin(startingAngle) * d));
                firedBy.getWorld().spawnParticle(Particle.FLAME, particleLocation, 1, 0.25, 0.1, 0.25, 0);
                if (Math.random() > 0.6) {
                    firedBy.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, particleLocation, 1, 0.25, 0.1, 0.25, 0);
                }

            }
        }

    }
}
