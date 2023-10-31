package me.supdapillar.theroad.Tasks.TheEnlightener;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class LightLazerAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;


    public LightLazerAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 120 + random.nextInt(60);

    }


    @Override
    public void run() {
        counter++;

        //Gets the closest player
        Player nearestPlayer = null;
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getWorld() == bossToUpdate.getWorld()){
                //If nearest player is null
                if (nearestPlayer == null){
                    nearestPlayer = player;
                }
                //Replace nearest player if there is one to replace
                else if (player.getLocation().distance(bossToUpdate.getLocation()) < nearestPlayer.getLocation().distance(bossToUpdate.getLocation()))
                {
                    if (player.getGameMode() == GameMode.ADVENTURE){
                        nearestPlayer = player;
                    }
                }
            }
        }

        //Warn the players
        if (counter < setOffTime) {
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0, 0.5, 0), 2, 0.5, 1, 0.5, new Particle.DustOptions(Color.PURPLE, (float) (1 + Math.random())));
            if (nearestPlayer != null) {
                if (bossToUpdate.hasLineOfSight(nearestPlayer)) {
                    //Make a particle connection
                    Vector vector = new Vector(nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getX(), nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getY(), nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getZ());
                    //Spawn particles
                    for (int i = 0; i < (int) bossToUpdate.getLocation().distance(nearestPlayer.getLocation()); i++) {
                        Location particleLocation = new Location(bossToUpdate.getWorld(), bossToUpdate.getEyeLocation().getX() + vector.normalize().getX() * i, bossToUpdate.getEyeLocation().getY() + vector.normalize().getY() * i, bossToUpdate.getEyeLocation().getZ() + vector.normalize().getZ() * i);
                        bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, 0.1, 0.1, 0.1, new Particle.DustOptions(Color.PURPLE, 1));
                        bossToUpdate.getWorld().spawnParticle(Particle.SPELL_WITCH, particleLocation, 1, 0.1, 0.1, 0.1, 0);
                    }

                }

            }
        }
        else if (counter < setOffTime + 60) {
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, bossToUpdate.getLocation().add(0, 0.5, 0), 2, 0.5, 1, 0.5, new Particle.DustOptions(Color.YELLOW, (float) (1 + Math.random())));
            if (nearestPlayer != null){
                if (bossToUpdate.hasLineOfSight(nearestPlayer)) {
                    //Make a particle connection
                    Vector vector = new Vector(nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getX(), nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getY(), nearestPlayer.getLocation().subtract(bossToUpdate.getLocation()).getZ());
                    //Spawn particles
                    for (int i = 0; i < (int) bossToUpdate.getLocation().distance(nearestPlayer.getLocation()); i++) {
                        Location particleLocation = new Location(bossToUpdate.getWorld(), bossToUpdate.getEyeLocation().getX() + vector.normalize().getX() * i, bossToUpdate.getEyeLocation().getY() + vector.normalize().getY() * i, bossToUpdate.getEyeLocation().getZ() + vector.normalize().getZ() * i);
                        bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 10, 0.1, 0.1, 0.1, new Particle.DustOptions(Color.YELLOW, 1));
                    }
                    //The Attack
                    nearestPlayer.damage(4, bossToUpdate);

                }
            }
        } else {
            this.cancel();
        }
    }
}
