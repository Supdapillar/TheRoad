package me.supdapillar.theroad.Tasks.TheEnlightener;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class RookUpdater extends BukkitRunnable {
    public Zombie rookToUpdate;
    public int attackTimer = 0;


    public RookUpdater(Zombie zombie){
        rookToUpdate = zombie;


        Random random = new Random();
        attackTimer = 60 + random.nextInt(120);

    }


    @Override
    public void run() {
        attackTimer--;
        rookToUpdate.setCustomName(ChatColor.WHITE + "Rook " +"[" + Math.ceil(rookToUpdate.getHealth()) + "❤/" + rookToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
        if (rookToUpdate.isDead()){
            this.cancel();
        }

        Location whereToTeleport = rookToUpdate.getLocation();

        if (attackTimer < 50){
            rookToUpdate.getWorld().spawnParticle(Particle.REDSTONE, rookToUpdate.getLocation().add(0,0.5,0), 2,0.35,0.75,0.35, new Particle.DustOptions(Color.PURPLE, (float) (0.8+Math.random())));

            //Finds the teleport location
            if (rookToUpdate.getTarget() != null){
                LivingEntity target = rookToUpdate.getTarget();
                Vector vector = new Vector(target.getLocation().subtract(rookToUpdate.getLocation()).getX(), target.getLocation().subtract(rookToUpdate.getLocation()).getY(), target.getLocation().subtract(rookToUpdate.getLocation()).getZ());

                double angle = Math.atan2(vector.getX(),vector.getZ());

                double yaw = (Math.toDegrees(angle));

                if (yaw >= -45 && yaw <= 45){
                    //Z
                    whereToTeleport = new Location(
                            rookToUpdate.getWorld(),
                            rookToUpdate.getLocation().getX(),
                            rookToUpdate.getTarget().getLocation().getY(),
                            rookToUpdate.getTarget().getLocation().getZ());
                }
                else if (yaw >= 45 && yaw <= 135){
                    //X
                    whereToTeleport = new Location(
                            rookToUpdate.getWorld(),
                            rookToUpdate.getTarget().getLocation().getX(),
                            rookToUpdate.getTarget().getLocation().getY(),
                            rookToUpdate.getLocation().getZ()
                            );

                }
                else if (yaw >= 135 || yaw <= -135){
                    //Z
                    whereToTeleport = new Location(
                            rookToUpdate.getWorld(),
                            rookToUpdate.getLocation().getX(),
                            rookToUpdate.getTarget().getLocation().getY(),
                            rookToUpdate.getTarget().getLocation().getZ()
                    );

                }
                else if (yaw >= -135){
                    //X
                    whereToTeleport = new Location(
                            rookToUpdate.getWorld(),
                            rookToUpdate.getTarget().getLocation().getX(),
                            rookToUpdate.getTarget().getLocation().getY(),
                            rookToUpdate.getLocation().getZ()

                    );

                }
            }
            rookToUpdate.getWorld().spawnParticle(Particle.REDSTONE, whereToTeleport.add(0,0.5,0), 4,0.35,0.75,0.35, new Particle.DustOptions(Color.PURPLE, (float) (0.8+Math.random())));
        }



        //Teleport Attack
        if (attackTimer <= 0){
            if (rookToUpdate.getTarget() != null){
                if (rookToUpdate.getTarget().getWorld() == rookToUpdate.getWorld()){
                    rookToUpdate.getWorld().playSound(rookToUpdate, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    rookToUpdate.teleport(whereToTeleport);
                    Random random = new Random();
                    attackTimer = 100 + random.nextInt(80);
                }
            }
        }





    }
}
