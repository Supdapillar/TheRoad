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

public class LazerCurseAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;


    public LazerCurseAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 120 + random.nextInt(60);

    }


    @Override
    public void run() {
        counter++;
        Boolean bossEnraged = bossToUpdate.getHealth() < (bossToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 3);

        //Warn the players
        if (counter < setOffTime){
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0,0.5,0), 2,0.5,1,0.5, new Particle.DustOptions(Color.PURPLE, (float) (1+Math.random())));
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.getWorld() == bossToUpdate.getWorld()){
                    if (player.getGameMode() == GameMode.ADVENTURE){
                        if (bossToUpdate.hasLineOfSight(player)){
                            //Make a particle connection
                            Vector vector = new Vector(player.getLocation().subtract(bossToUpdate.getLocation()).getX(), player.getLocation().subtract(bossToUpdate.getLocation()).getY(), player.getLocation().subtract(bossToUpdate.getLocation()).getZ());
                            //Spawn particles
                            for(int i = 0; i < (int)bossToUpdate.getLocation().distance(player.getLocation()); i++){
                                Location particleLocation = new Location(bossToUpdate.getWorld(), bossToUpdate.getEyeLocation().getX() + vector.normalize().getX() * i, bossToUpdate.getEyeLocation().getY() +  vector.normalize().getY() * i,bossToUpdate.getEyeLocation().getZ() +  vector.normalize().getZ() * i);
                                bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,1,0.2,0.2,0.2, new Particle.DustOptions(Color.PURPLE, 1));
                                bossToUpdate.getWorld().spawnParticle(Particle.SPELL_WITCH,particleLocation ,1,0.2,0.2,0.2, 0);
                                if (bossEnraged){
                                    bossToUpdate.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, 1, 0.2 ,0.2 ,0.2, 0);
                                }
                            }

                        }
                    }
                }
            }
        }
        else{
            bossToUpdate.getWorld().playSound(bossToUpdate.getLocation(),Sound.ENTITY_ELDER_GUARDIAN_CURSE, 3, 1.4f);
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0,0.5,0), 2,0.5,1,0.5, new Particle.DustOptions(Color.WHITE, (float) (1+Math.random())));
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player.getWorld() == bossToUpdate.getWorld()){
                    if (player.getGameMode() == GameMode.ADVENTURE){
                        if (bossToUpdate.hasLineOfSight(player)){
                            //Make a particle connection
                            Vector vector = new Vector(player.getLocation().subtract(bossToUpdate.getLocation()).getX(), player.getLocation().subtract(bossToUpdate.getLocation()).getY(), player.getLocation().subtract(bossToUpdate.getLocation()).getZ());
                            //Spawn particles
                            for(int i = 0; i < (int)bossToUpdate.getLocation().distance(player.getLocation()); i++){
                                Location particleLocation = new Location(bossToUpdate.getWorld(), bossToUpdate.getEyeLocation().getX() + vector.normalize().getX() * i, bossToUpdate.getEyeLocation().getY() +  vector.normalize().getY() * i,bossToUpdate.getEyeLocation().getZ() +  vector.normalize().getZ() * i);
                                bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,10,0.1,0.1,0.1, new Particle.DustOptions(Color.WHITE, 1));
                                bossToUpdate.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,particleLocation ,3,0.1,0.1,0.1, 0);
                                if (bossEnraged){
                                    bossToUpdate.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, 2, 0.2 ,0.2 ,0.2, 0);
                                }
                            }
                            //The Attack
                            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 80, 0, true));
                            player.damage(5, bossToUpdate);
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
