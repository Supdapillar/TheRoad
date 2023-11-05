package me.supdapillar.theroad.Tasks.SkyGuardian;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.Random;

public class SkyBeamAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;
    private double Radius;

    private Location beamCenter;

    public SkyBeamAttack(Zombie zombie, double radius){
        bossToUpdate = zombie;

        double randomAngle = (Math.PI *2) * Math.random();
        double randomDistance = Math.random() *7 +2;
        beamCenter = zombie.getLocation().add(Math.cos(randomAngle) * randomDistance,0,Math.sin(randomAngle) * randomDistance);

        Random random = new Random();
        setOffTime = 50 + random.nextInt(20);
        Radius = radius;
    }


    @Override
    public void run() {
        counter++;
        Boolean bossEnraged = bossToUpdate.getHealth() < (bossToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 3);

        //Warn the players
        if (counter < setOffTime){

            double Angle = 0;
            for(int i = 0; i < 30; i++){
                Angle -= Math.PI/15f + new Date(System.currentTimeMillis()).getTime()*80;;

                Location particleLocation = new Location(beamCenter.getWorld(), beamCenter.getX() + (Math.cos(Angle) * Radius), beamCenter.getY(), beamCenter.getZ()+ (Math.sin(Angle) * Radius));
                beamCenter.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, 0.2 ,0.2 ,0.2, new Particle.DustOptions(Color.PURPLE, 2));
                if (bossEnraged){
                    beamCenter.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, 1, 0.2 ,0.2 ,0.2, 0);
                }
            }
            //Taller particles
            for(int i = 0; i < 5; i++){
                Angle -= Math.PI/2.5f + new Date(System.currentTimeMillis()).getTime()*80;;

                Location particleLocation = new Location(beamCenter.getWorld(), beamCenter.getX() + (Math.cos(Angle) * Radius), beamCenter.getY(), beamCenter.getZ()+ (Math.sin(Angle) * Radius));
                beamCenter.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, 0.2 ,4 ,0.2, new Particle.DustOptions(Color.PURPLE, 2));
                if (bossEnraged){
                    beamCenter.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, 1, 0.2 ,4 ,0.2, 0);
                }
            }
        }
        //Attacks and ends task
        else if (counter > setOffTime+5) {
            beamCenter.getWorld().playSound(beamCenter, Sound.ENTITY_BLAZE_SHOOT, 16, 0.5f);

            //Particles
            double Angle = 0;
            for (int y = 0; y < 15; y++){
                for(int i = 0; i < 30; i++){
                    Angle -= Math.PI/15f;

                    Location particleLocation = new Location(beamCenter.getWorld(), beamCenter.getX() + (Math.cos(Angle) * Radius), beamCenter.getY() + y, beamCenter.getZ()+ (Math.sin(Angle) * Radius));
                    beamCenter.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, 0.2 ,0.2 ,0.2, new Particle.DustOptions(Color.WHITE, 2));
                    beamCenter.getWorld().spawnParticle(Particle.END_ROD, particleLocation, 1, 0.2 ,0.4 ,0.2, 0);
                    if (bossEnraged){
                        beamCenter.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, 1, 0.2 ,0.4 ,0.2, 0);

                    }

                }
            }

            //The attack
            for(Player player : Bukkit.getOnlinePlayers()){
                if (player.getGameMode() == GameMode.ADVENTURE){
                    if (player.getWorld() == beamCenter.getWorld()){
                        if (player.getLocation().distance(beamCenter) < Radius){
                            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 5 * 20, 0, true));
                            player.damage(5, bossToUpdate);
                            player.setVelocity(player.getVelocity().add(new Vector(0,1,0)));
                            if (bossEnraged){
                                player.setFireTicks(40);
                            }
                        }
                    }
                }
            }


            this.cancel();
        }
    }
}
