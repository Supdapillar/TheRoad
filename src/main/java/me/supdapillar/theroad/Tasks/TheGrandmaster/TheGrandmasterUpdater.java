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
            switch (random.nextInt(0,4)){
                case 0:

                    new TeleportBehindAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);

                    AttackCooldown = 120+ random.nextInt(50);
                    break;
                case 1:

                    new SummonRookAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),0, 0);
                    AttackCooldown = 160+ random.nextInt(80);
                    break;
                case 2:

                    int randomAmount = random.nextInt(1,4);
                    for (int i = 0; i < randomAmount; i++){
                        new WallPushAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(),random.nextInt(i*20,i*20+20), 0);
                    }
                    AttackCooldown = 170+ random.nextInt(60);
                    break;
                case 3:
                    new SummonPawnAttack(bossToUpdate).runTaskTimer(TheRoadPlugin.getInstance(), 0,0);
                    AttackCooldown = 200+ random.nextInt(60);
                    break;

            }
        }
    }
}
