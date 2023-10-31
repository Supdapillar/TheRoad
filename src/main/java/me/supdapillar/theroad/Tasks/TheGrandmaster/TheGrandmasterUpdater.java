package me.supdapillar.theroad.Tasks.TheGrandmaster;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class TheGrandmasterUpdater extends BukkitRunnable {
    public Zombie bossToUpdate;
    private int AttackCooldown = 0;
    public TheGrandmasterUpdater(Zombie zombie){
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
            switch (random.nextInt(2,3)){
                case 0:

                    new TeleportBehindAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);

                    AttackCooldown = 50+ random.nextInt(80);
                    break;
                case 1:

                    new SummonRookAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);
                    AttackCooldown = 80+ random.nextInt(80);
                    break;
                case 2:

                    new WallPushAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);
                    AttackCooldown = 30+ random.nextInt(60);
                    break;

            }
        }
    }
}
