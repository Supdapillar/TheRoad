package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Date;
import java.util.Random;

public class RootOvergrowthAttackTimer extends BukkitRunnable {


    private Player firedBy;
    private int growthLeft = 0;
    private Location startLocation;
    public RootOvergrowthAttackTimer(Player player)
    {
        firedBy = player;



        startLocation = firedBy.getTargetBlockExact(25).getLocation().add(0.5,1,0.5);
    }


    @Override
    public void run() {
        growthLeft++;


        if (growthLeft < 50) {
            //Particles
            double Angle = 0;
            for (int i = 0; i < 36; i++) {
                Angle -= Math.PI / 18f + new Date(System.currentTimeMillis()).getTime() * 80;
                Location particleLocation = new Location(startLocation.getWorld(), startLocation.getX() + (Math.cos(Angle) * growthLeft / 10), startLocation.getY(), startLocation.getZ() + (Math.sin(Angle) * growthLeft / 10));
                startLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.GREEN, 2));
                startLocation.getWorld().spawnParticle(Particle.TOTEM, particleLocation, 2, 0.2, 0.3, 0.2, 0);
            }
            for (LivingEntity livingEntity : startLocation.getWorld().getLivingEntities()) {
                if (livingEntity instanceof Mob) {
                    Mob mob = (Mob) livingEntity;
                    if (mob.getLocation().distance(startLocation) < growthLeft / 10){
                        mob.damage(1, firedBy);
                        new RootSpellAttackTimer(mob).runTaskTimer(TheRoadPlugin.getInstance(), 0, 10);
                    }
                }
            }
        }
        else if(growthLeft < 55){
            //Particles
            double Angle = 0;
            for (int i = 0; i < 36; i++) {
                Angle -= Math.PI / 18f + new Date(System.currentTimeMillis()).getTime() * 80;
                Location particleLocation = new Location(startLocation.getWorld(), startLocation.getX() + (Math.cos(Angle) *( 55-growthLeft)), startLocation.getY()+(growthLeft-50), startLocation.getZ() + (Math.sin(Angle) * (55-growthLeft)));
                startLocation.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 3, 0.2, 0.4, 0.2, new Particle.DustOptions(Color.GREEN, 2));
                startLocation.getWorld().spawnParticle(Particle.TOTEM, particleLocation, 2, 0.2, 0.3, 0.2, 0);
            }
            for (LivingEntity livingEntity : startLocation.getWorld().getLivingEntities()) {
                if (livingEntity instanceof Mob) {
                    Mob mob = (Mob) livingEntity;
                    if (mob.getLocation().distance(startLocation) < (double) growthLeft / 10){
                        mob.damage(10, firedBy);
                        mob.setVelocity(new Vector(0,2,0));
                        new RootSpellAttackTimer(mob).runTaskTimer(TheRoadPlugin.getInstance(), 0, 10);
                    }
                }
            }
        }
        else {
            this.cancel();
        }
    }
}
