package me.supdapillar.theroad.Tasks.TheEnlightener;

import me.supdapillar.theroad.Tasks.SkyGuardian.LazerCurseAttack;
import me.supdapillar.theroad.Tasks.SkyGuardian.SkyBeamAttack;
import me.supdapillar.theroad.Tasks.SkyGuardian.SummonMinionAttack;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TheEnlightenerUpdater extends BukkitRunnable {
    public Zombie bossToUpdate;
    private int AttackCooldown = 0;
    public TheEnlightenerUpdater(Zombie zombie){
        bossToUpdate = zombie;
    }


    @Override
    public void run() {
        AttackCooldown--;


        if (bossToUpdate.isDead()){
            this.cancel();
        }

        if (AttackCooldown <= 0){

            Random random = new Random();
            switch (random.nextInt(3)){
                case 0:

                    new LightLazerAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);

                    AttackCooldown = 200+ random.nextInt(80);
                    break;
                case 1:

                    new SummonLightSentryAttack(bossToUpdate).runTask(TheRoadPlugin.getInstance());

                    AttackCooldown = 180+ random.nextInt(70);
                    break;
                case 2:
                    new LanternRainAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 3);

                    AttackCooldown = 200+ random.nextInt(70);
                    break;

            }
        }
    }
}
