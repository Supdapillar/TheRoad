package me.supdapillar.theroad.Tasks.TheEnlightener;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class SentryUpdater extends BukkitRunnable {
    public Zombie sentryToUpdate;
    public int attackTimer = 0;


    public SentryUpdater(Zombie zombie){
        sentryToUpdate = zombie;


        Random random = new Random();
        attackTimer = 60 + random.nextInt(120);

    }


    @Override
    public void run() {
        attackTimer--;
        sentryToUpdate.setCustomName(ChatColor.WHITE + "Sentry " +"[" + Math.ceil(sentryToUpdate.getHealth()) + "❤/" + sentryToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");


        if (sentryToUpdate.isDead()){
            this.cancel();
        }


        if (attackTimer < 50){
            sentryToUpdate.getWorld().spawnParticle(Particle.REDSTONE, sentryToUpdate.getLocation().add(0,0.5,0), 2,0.35,0.75,0.35, new Particle.DustOptions(Color.PURPLE, (float) (0.8+Math.random())));
        }



        //Attack
        if (attackTimer <= 0) {
            if (sentryToUpdate.getTarget() != null) {
                if (sentryToUpdate.getTarget().getWorld() == sentryToUpdate.getWorld()) {
                    sentryToUpdate.getWorld().playSound(sentryToUpdate, Sound.ENTITY_SHULKER_SHOOT, 1, 1);
                    LivingEntity livingEntity = sentryToUpdate.getTarget();
                    Vector vector = new Vector(livingEntity.getLocation().subtract(sentryToUpdate.getLocation()).getX(), livingEntity.getLocation().subtract(sentryToUpdate.getLocation()).getY(), livingEntity.getLocation().subtract(sentryToUpdate.getLocation()).getZ());
                    sentryToUpdate.launchProjectile(ShulkerBullet.class, vector.normalize());
                    Random random = new Random();
                    attackTimer = 60 + random.nextInt(120);
                }
            }
        }
    }
}
