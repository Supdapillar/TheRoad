package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Talisman.BarrageTalisman;
import me.supdapillar.theroad.gameClasses.Defender;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class DefenderShieldTimer extends BukkitRunnable {



    private String axis;
    public Player summonedBy;
    private Location summonedAt;
    private int hitsRemaining = 10;

    private float animationCounter = 0;


    public static ArrayList<DefenderShieldTimer> currentShieldActivations = new ArrayList<DefenderShieldTimer>();
    public DefenderShieldTimer(Player player){
        summonedBy = player;

        for (DefenderShieldTimer defenderShieldTimer : currentShieldActivations){
            if (defenderShieldTimer.summonedBy == player){
                defenderShieldTimer.cancel();
            }
        }
        currentShieldActivations.removeIf(o -> o.summonedBy == player);

        player.sendMessage(ChatColor.AQUA + "New shield made");
        summonedAt = player.getTargetBlockExact(10).getLocation().add(0.5,0,0.5);

        //Gets the axis the shield should face
        float yaw = player.getLocation().getYaw();

        if (yaw >= -45 && yaw <= 45){
            axis = "X";
        }
        else if (yaw >= 45 && yaw <= 135){
            axis = "Y";
        }
        else if (yaw >= 135 || yaw <= -135){
            axis = "X";
        }
        else if (yaw >= -135){
            axis = "Y";
        }
        currentShieldActivations.add(this);
    }


    @Override
    public void run() {
        if (animationCounter < 1){
            animationCounter+= 0.025F;
        }

        if (hitsRemaining <= 0 || summonedBy.getWorld() != summonedAt.getWorld()){
            cancel();
        }



        if (Objects.equals(axis, "X")){

            //Change Color based on damage
            Particle.DustOptions dustOptions;

            if ((Math.random()*10) > hitsRemaining){
                 dustOptions = new Particle.DustOptions(Color.RED, (float) (1 + Math.random()));
            }
            else {
                 dustOptions = new Particle.DustOptions(Color.AQUA, (float) (1 + Math.random()));
            }


            Location particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+2*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,20, 1.25f*animationCounter, 1*animationCounter, 0, dustOptions);
            //Makes the border
            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX()+2.5f*animationCounter, summonedAt.getY()+1.5f*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,5, 0, 1*animationCounter, 0, dustOptions);

            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX()-2.5f*animationCounter, summonedAt.getY()+1.5f*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,5,0, animationCounter, 0, dustOptions);

            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+4*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,5, animationCounter, 0, 0, dustOptions);

            //Collisions
            for (Entity entity: summonedBy.getNearbyEntities(25,10,25)){
                if (entity instanceof Mob){
                    Mob livingEntity = (Mob) entity;
                    if (livingEntity.getLocation().distance(summonedAt) < 4 ){
                        boolean withinX = livingEntity.getLocation().getX() > summonedAt.getX()-2 && livingEntity.getLocation().getX() < summonedAt.getX()+2;
                        boolean withinZ = livingEntity.getLocation().getY() > summonedAt.getY()-1f && livingEntity.getLocation().getY() < summonedAt.getY()+2f;
                        boolean withinY = livingEntity.getLocation().getZ() > summonedAt.getZ() - 0.5f && livingEntity.getLocation().getZ() < summonedAt.getZ()+ 0.5f;

                        if (withinX && withinZ && withinY){
                            if (livingEntity.getLocation().getZ() > summonedAt.getZ() ){
                                livingEntity.setVelocity(new Vector(0f, 0.1f, 0.75f  ));
                                hitsRemaining--;
                            }
                            else {
                                livingEntity.setVelocity(new Vector(0f, 0.1f, -0.75f  ));
                                hitsRemaining--;
                            }
                        }
                    }
                }
            }
        }
        else {

            //Change Color based on damage
            Particle.DustOptions dustOptions;
            if ((Math.random()*10) > hitsRemaining){
                 dustOptions = new Particle.DustOptions(Color.RED, (float) (1 + Math.random()));
            }
            else {
                 dustOptions = new Particle.DustOptions(Color.AQUA, (float) (1 + Math.random()));
            }

            Location particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+2*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,20, 0, 1*animationCounter, 1.25f*animationCounter, dustOptions);
            //Makes the border
            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+1.5f*animationCounter, summonedAt.getZ()+2.5f*animationCounter);
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,5, 0, 1*animationCounter, 0, dustOptions);

            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+1.5f*animationCounter, summonedAt.getZ()-2.5f*animationCounter);
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,5,0, animationCounter, 0, dustOptions);

            particleLocation = new Location(summonedAt.getWorld(), summonedAt.getX(), summonedAt.getY()+4*animationCounter, summonedAt.getZ());
            summonedBy.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,5, 0, 0, animationCounter, dustOptions);

            //Collisions
            for (Entity entity: summonedBy.getNearbyEntities(25,10,25)){
                if (entity instanceof Mob){
                    Mob livingEntity = (Mob) entity;
                    if (livingEntity.getLocation().distance(summonedAt) < 4 ){
                        boolean withinX = livingEntity.getLocation().getX() > summonedAt.getX()-0.5f && livingEntity.getLocation().getX() < summonedAt.getX()+0.5f;
                        boolean withinZ = livingEntity.getLocation().getY() > summonedAt.getY()-1f && livingEntity.getLocation().getY() < summonedAt.getY()+2f;
                        boolean withinY = livingEntity.getLocation().getZ() > summonedAt.getZ() - 2f && livingEntity.getLocation().getZ() < summonedAt.getZ()+ 2f;

                        if (withinX && withinZ && withinY){
                            if (livingEntity.getLocation().getX() > summonedAt.getX() ){
                                livingEntity.setVelocity(new Vector(0.75f, 0.1f, 0  ));
                                hitsRemaining--;
                            }
                            else {
                                livingEntity.setVelocity(new Vector(-0.75f, 0.1f, 0  ));
                                hitsRemaining--;
                            }
                        }
                    }
                }
            }
        }
    }
}
