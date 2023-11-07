package me.supdapillar.theroad.Tasks.TheGrandmaster;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class TeleportBehindAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;


    public TeleportBehindAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 60 + random.nextInt(20);

    }


    @Override
    public void run() {
        counter++;

        //Warn
        if (counter < setOffTime){
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0, 0.5, 0), 2, 0.5, 1, 0.5, new Particle.DustOptions(Color.PURPLE, (float) (1 + Math.random())));
        }
        else
        {
            if (bossToUpdate.getTarget() != null){
                if (bossToUpdate.getTarget().getWorld() == bossToUpdate.getWorld()){
                    double radians = Math.toRadians(bossToUpdate.getTarget().getLocation().getYaw()+90);

                    Location newLocation = new Location(
                            bossToUpdate.getTarget().getWorld(),
                            bossToUpdate.getTarget().getLocation().getX()-(Math.cos(radians)*6),
                            bossToUpdate.getTarget().getLocation().getY(),
                            bossToUpdate.getTarget().getLocation().getZ()-(Math.sin(radians)*6));

                    bossToUpdate.teleport(newLocation);
                }
            }

            bossToUpdate.getWorld().playSound(bossToUpdate.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 3, 1);
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0, 0.5, 0), 30, 0.5, 1, 0.5, new Particle.DustOptions(Color.WHITE, (float) (1 + Math.random())));
            this.cancel();
        }
    }
}
