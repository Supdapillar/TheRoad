package me.supdapillar.theroad.Tasks.TheGrandmaster;

import me.supdapillar.theroad.Tasks.TheEnlightener.RookUpdater;
import me.supdapillar.theroad.Tasks.TheEnlightener.SentryUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SummonRookAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;
    private ArrayList<Location> minionLocations = new ArrayList<Location>();

    public SummonRookAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 60 + random.nextInt(25);

        //Addes the minion spawns
        for (int i = 0; i<1; i++){
            double randomAngle = (Math.PI *2) * Math.random();
            double randomDistance = Math.random() *5 +2;
            minionLocations.add(bossToUpdate.getLocation().add(Math.cos(randomAngle) * randomDistance,0,Math.sin(randomAngle) * randomDistance) );
        }

    }


    @Override

    public void run() {
        counter++;
        //Warn the players
        double randomAngle = (double) new Date(System.currentTimeMillis()).getTime() /2000;
        if (counter < setOffTime){
            for(Location location : minionLocations){
                location.getWorld().spawnParticle(Particle.REDSTONE, location, 2,0.5,1,0.5, new Particle.DustOptions(Color.PURPLE, (float) (1+Math.random())));
                location.getWorld().spawnParticle(Particle.SPELL_WITCH, location, 1,0.5,1,0.5, 0);
            }
        }
        //Spawn the minions
        else {
            bossToUpdate.getWorld().playSound(bossToUpdate.getLocation(),Sound.ENTITY_EVOKER_PREPARE_SUMMON, 8, 1.4f);
            for(Location location : minionLocations){
                location.getWorld().spawnParticle(Particle.REDSTONE, location.add(0,0.5,0), 24,0.5,1,0.5, new Particle.DustOptions(Color.WHITE, (float) (1+Math.random())));
                location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.add(0,0.5,0), 12,0.5,1,0.5, 0);

                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.getEquipment().setHelmet(Heads.ChessRook.getItemStack());
                zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
                    zombie.setHealth(30);
                    zombie.setAdult();
                    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.26);
                    new RookUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);

            }
            this.cancel();
        }
    }
}
