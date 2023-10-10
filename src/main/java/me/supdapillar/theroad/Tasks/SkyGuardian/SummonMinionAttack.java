package me.supdapillar.theroad.Tasks.SkyGuardian;

import me.supdapillar.theroad.enums.Heads;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SummonMinionAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;
    private ArrayList<Location> minionLocations = new ArrayList<Location>();

    public SummonMinionAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 60 + random.nextInt(25);
        int RandomAmount = random.nextInt(1,4);

        //Addes the minion spawns
        for (int i = 0; i<RandomAmount; i++){
            double randomAngle = (Math.PI *2) * Math.random();
            double randomDistance = Math.random() *5 +2;
            minionLocations.add(bossToUpdate.getLocation().add(Math.cos(randomAngle) * randomDistance,0,Math.sin(randomAngle) * randomDistance) );
        }

    }


    @Override
    public void run() {
        counter++;
        Boolean bossEnraged = bossToUpdate.getHealth() < (bossToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 3);
        //Warn the players
        double randomAngle = (double) new Date(System.currentTimeMillis()).getTime() /2000;
        if (counter < setOffTime){
            for(Location location : minionLocations){
                location.getWorld().spawnParticle(Particle.REDSTONE, location, 2,0.5,1,0.5, new Particle.DustOptions(Color.PURPLE, (float) (1+Math.random())));
                location.getWorld().spawnParticle(Particle.SPELL_WITCH, location, 1,0.5,1,0.5, 0);
                if (bossEnraged){
                    location.getWorld().spawnParticle(Particle.SMALL_FLAME, location, 1,0.5,1,0.5, 0);
                }
            }
        }
        //Spawn the minions
        else {
            bossToUpdate.getWorld().playSound(bossToUpdate.getLocation(),Sound.ENTITY_EVOKER_PREPARE_SUMMON, 8, 1.4f);
            for(Location location : minionLocations){
                location.getWorld().spawnParticle(Particle.REDSTONE, location.add(0,0.5,0), 24,0.5,1,0.5, new Particle.DustOptions(Color.WHITE, (float) (1+Math.random())));
                location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location.add(0,0.5,0), 12,0.5,1,0.5, 0);
                if (!bossEnraged) {
                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.getEquipment().setHelmet(Heads.Cloud.getItemStack());
                    zombie.setBaby();
                }
                else {
                    location.getWorld().spawnParticle(Particle.SMALL_FLAME, location, 1,0.5,1,0.5, 0);
                    Zombie zombie = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
                    zombie.getEquipment().setHelmet(Heads.Cloud.getItemStack());
                    zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.45);
                    zombie.setBaby();
                }

            }
            this.cancel();
        }
    }
}
