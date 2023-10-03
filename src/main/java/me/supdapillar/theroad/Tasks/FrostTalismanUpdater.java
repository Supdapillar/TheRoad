package me.supdapillar.theroad.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.Bisected;
import org.bukkit.entity.Mob;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.Random;

public class FrostTalismanUpdater extends BukkitRunnable {
    private int HalfSecondCounter = 20;
    private final Mob effectedMob;

    private double defaultSpeed;

    public FrostTalismanUpdater(Mob mob){
        effectedMob = mob;
        defaultSpeed = effectedMob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();

    }
    @Override
    public void run() {
        //Makes the zombies gradually faster

        HalfSecondCounter--;

        Double dividedSpeed = defaultSpeed/20;
        
        effectedMob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(dividedSpeed * (20-HalfSecondCounter) );
        Bukkit.broadcastMessage(effectedMob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getValue() + "");


        //Spawn epic particles
        Particle.DustOptions dustOptions = null;
        Random random = new Random();
        for (int i = 0; i < HalfSecondCounter; i++){
            switch (random.nextInt(4)){
                case 0:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(231, 237, 235),1);
                    break;
                case 1:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(142, 206, 206),1);
                    break;
                case 2:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(98, 161, 199),1);
                    break;
                case 3:
                    dustOptions = new Particle.DustOptions(Color.fromRGB(63, 110, 204),1);
                    break;

            }
            effectedMob.getWorld().spawnParticle(Particle.REDSTONE, effectedMob.getLocation().add(0,0.5,0), 1,0.5,1,0.5, dustOptions);

        }


        if (HalfSecondCounter == 0){
            this.cancel();
            Bukkit.broadcastMessage("CANCELD");
        }
    }
}
