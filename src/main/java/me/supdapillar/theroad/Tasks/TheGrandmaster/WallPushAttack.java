package me.supdapillar.theroad.Tasks.TheGrandmaster;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.Random;

public class WallPushAttack extends BukkitRunnable {
    public Zombie bossToUpdate;

    public int counter = 0;
    private int setOffTime;
    private Location wallCenter;
    private String direction;
    private double length = 5;

    public WallPushAttack(Zombie zombie){
        bossToUpdate = zombie;
        Random random = new Random();

        setOffTime = 50 + random.nextInt(0,30);

        switch (random.nextInt(4)){
            case 0:
                direction = "X+";
                break;
            case 1:
                direction = "X-";

                break;
            case 2:
                direction = "Z+";

                break;
            case 3:
                direction = "Z-";
                break;

        }
    }


    @Override
    public void run() {
        counter++;

        //Stops the attack
        if (counter > setOffTime+40){
            cancel();
        }



        //Warn the player
        if (counter < setOffTime){
            //Sets the center point to be at the boss
            wallCenter = bossToUpdate.getLocation();

        }
        //Attack
        else {
            if (counter == setOffTime){
                bossToUpdate.getWorld().playSound(bossToUpdate,Sound.ENTITY_SHULKER_SHOOT, 99, 0.5f);
            }
            //Moves the wall
            if (direction.charAt(0) == 'X'){
                if (direction.charAt(1) == '+'){
                    wallCenter.add(0,0,0.4);
                }
                else {
                    wallCenter.add(0,0,-0.4);
                }
                //X Collisions
                for (Player player: Bukkit.getOnlinePlayers()){
                    if (player.getWorld() == wallCenter.getWorld()){
                        if (player.getLocation().distance(wallCenter) < length ){
                            boolean withinX = player.getLocation().getX() > wallCenter.getX()-length && player.getLocation().getX() < wallCenter.getX()+length;
                            boolean withinZ = player.getLocation().getY() > wallCenter.getY()-1f && player.getLocation().getY() < wallCenter.getY()+2f;
                            boolean withinY = player.getLocation().getZ() > wallCenter.getZ() - 0.5f && player.getLocation().getZ() < wallCenter.getZ()+ 0.5f;

                            //Knocks the player back with damage
                            if (withinX && withinZ && withinY){
                                player.damage(7, bossToUpdate);
                                if (direction.charAt(1) == '+'){
                                    player.setVelocity(player.getVelocity().add(new Vector(0,0.1f,0.5)));
                                }
                                else {
                                    player.setVelocity(player.getVelocity().add(new Vector(0,0.1f,-0.5)));
                                }
                            }
                        }
                    }
                }
            }
            else {
                if (direction.charAt(1) == '+'){
                    wallCenter.add(0.4,0,0);
                }
                else {
                    wallCenter.add(-0.4,0,0);
                }
                //Z Collisions
                for (Player player: Bukkit.getOnlinePlayers()){
                    if (player.getWorld() == wallCenter.getWorld()){
                        if (player.getLocation().distance(wallCenter) < length ){
                            boolean withinX = player.getLocation().getX() > wallCenter.getX()-0.5f && player.getLocation().getX() < wallCenter.getX()+ 0.5f;
                            boolean withinZ = player.getLocation().getY() > wallCenter.getY()-1f && player.getLocation().getY() < wallCenter.getY()+2f;
                            boolean withinY = player.getLocation().getZ() > wallCenter.getZ()-length && player.getLocation().getZ() < wallCenter.getZ()+length;
                            //Knocks the player back with damage
                            if (withinX && withinZ && withinY){
                                player.damage(7, bossToUpdate);
                                if (direction.charAt(1) == '+'){
                                    player.setVelocity(player.getVelocity().add(new Vector(0.5,0.1f,0)));
                                }
                                else {
                                    player.setVelocity(player.getVelocity().add(new Vector(-0.5,0.1f,0)));
                                }
                            }
                        }
                    }
                }
            }
        }
        //Always render the wall
        Particle.DustOptions dustOptions;
        if (counter > setOffTime){
            dustOptions = new Particle.DustOptions(Color.WHITE, (float) (1+Math.random()));
        }
        else {
            dustOptions = new Particle.DustOptions(Color.PURPLE, (float) (1+Math.random()));
        }
        Location particleLocation = wallCenter;
        if (direction.charAt(0) == 'X'){
            //Render the wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+1,wallCenter.getZ());

            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,(int)length*6, length/2, 1, 0.25f, dustOptions);

            //Makes the border
            //Ceiling
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+3,wallCenter.getZ());
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,(int) (length*2), length/2, 0, 0, dustOptions);
            //Left wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX()- length,wallCenter.getY()+1,wallCenter.getZ() );
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,10,0, 1, 0, dustOptions);
            //Right wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX()+ length,wallCenter.getY()+1,wallCenter.getZ());
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,10, 0, 1, 0, dustOptions);
        }
        else {
            //Render the wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+1,wallCenter.getZ());
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, (int)length*6, 0.25f, 1, length/2, dustOptions);
            //Makes the border
            //Ceiling
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+3,wallCenter.getZ());
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,(int) (length*2), 0, 0, length/2, dustOptions);
            //Left wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+1,wallCenter.getZ()- length );
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,10,0, 1, 0, dustOptions);
            //Right wall
            particleLocation = new Location(wallCenter.getWorld(),wallCenter.getX(),wallCenter.getY()+1,wallCenter.getZ()+ length);
            bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, particleLocation,10, 0, 1, 0, dustOptions);
        }


    }
}
