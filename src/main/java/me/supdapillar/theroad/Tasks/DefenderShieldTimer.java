package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Talisman.BarrageTalisman;
import me.supdapillar.theroad.gameClasses.Defender;
import org.bukkit.*;
import org.bukkit.entity.*;
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
                if (entity instanceof Mob || entity instanceof Projectile){
                    if (entity.getLocation().distance(summonedAt) < 4 ){
                        boolean withinX = entity.getLocation().getX() > summonedAt.getX()-2 && entity.getLocation().getX() < summonedAt.getX()+2;
                        boolean withinZ = entity.getLocation().getY() > summonedAt.getY()-1f && entity.getLocation().getY() < summonedAt.getY()+2f;
                        boolean withinY = entity.getLocation().getZ() > summonedAt.getZ() - 1.5f && entity.getLocation().getZ() < summonedAt.getZ()+ 1.5f;

                        if (entity instanceof Mob){
                            if (withinX && withinZ && withinY){
                                if (entity.getLocation().getZ() > summonedAt.getZ() ){
                                    entity.setVelocity(new Vector(0f, 0.1f, 0.75f  ));
                                    hitsRemaining--;
                                }
                                else {
                                    entity.setVelocity(new Vector(0f, 0.1f, -0.75f  ));
                                    hitsRemaining--;
                                }
                            }
                        }
                        else{
                            entity.remove();
                            hitsRemaining--;
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
                if (entity instanceof Mob || entity instanceof Projectile){
                    if (entity.getLocation().distance(summonedAt) < 4 ){
                        boolean withinX = entity.getLocation().getX() > summonedAt.getX()-1.5f && entity.getLocation().getX() < summonedAt.getX()+1.5f;
                        boolean withinZ = entity.getLocation().getY() > summonedAt.getY()-1f && entity.getLocation().getY() < summonedAt.getY()+2f;
                        boolean withinY = entity.getLocation().getZ() > summonedAt.getZ() - 2f && entity.getLocation().getZ() < summonedAt.getZ()+ 2f;

                        if (entity instanceof Mob){
                            if (withinX && withinZ && withinY){
                                if (entity.getLocation().getX() > summonedAt.getX() ){
                                    entity.setVelocity(new Vector(0.75f, 0.1f, 0  ));
                                    hitsRemaining--;
                                }
                                else {
                                    entity.setVelocity(new Vector(-0.75f, 0.1f, 0  ));
                                    hitsRemaining--;
                                }
                            }
                        }
                        else{
                            entity.remove();
                            hitsRemaining--;
                        }
                    }
                }
            }
        }
    }
}
