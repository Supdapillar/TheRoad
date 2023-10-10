package me.supdapillar.theroad.Tasks.SkyGuardian;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SkyGuardianUpdater extends BukkitRunnable {
    public Zombie bossToUpdate;
    private int AttackCooldown = 0;
    public SkyGuardianUpdater(Zombie zombie){
        bossToUpdate = zombie;
    }


    @Override
    public void run() {
        AttackCooldown--;
        Boolean bossEnraged = bossToUpdate.getHealth() < (bossToUpdate.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / 3);

        //Cool hip flame particles
        if (bossEnraged){
            bossToUpdate.getWorld().spawnParticle(Particle.SMALL_FLAME, bossToUpdate.getLocation().add(0,0.5,0), 1,0.5,1,0.5, 0);
        }

        if (AttackCooldown <= 0){

            Random random = new Random();
            switch (random.nextInt(3)){
                case 0:
                    //Beam attack
                    if (!bossEnraged){
                        int RandomAmount = random.nextInt(2,4);

                        for (int i = 0; i < RandomAmount; i++){
                            new SkyBeamAttack(bossToUpdate, 3).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);
                        }
                        AttackCooldown = 200+ random.nextInt(80);

                    }
                    else {
                        int RandomAmount = random.nextInt(4,7);
                        for (int i = 0; i < RandomAmount; i++){
                            new SkyBeamAttack(bossToUpdate, 2.25).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);
                        }
                        AttackCooldown = 180+ random.nextInt(50);

                    }


                    break;
                case 1:
                    //Curse attack
                    new LazerCurseAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
                    if (!bossEnraged){
                        AttackCooldown = 240+ random.nextInt(60);

                    }
                    else {
                        AttackCooldown = 220+ random.nextInt(50);
                    }

                    break;
                case 2:
                    //Curse attack
                    //Minion Attack
                    new SummonMinionAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(), 0,0);
                    AttackCooldown = 200+ random.nextInt(100);

                    break;            }
        }
    }
}
