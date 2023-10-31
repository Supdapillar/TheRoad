package me.supdapillar.theroad.Tasks.TheEnlightener;

import org.bukkit.*;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Random;

public class LanternRainAttack extends BukkitRunnable {
    public Zombie bossToUpdate;


    private int LanternsCount;


    public int counter = 0;

    private int setOffTime;
    private Random random;
    private boolean canPlaySound = true;


    public LanternRainAttack(Zombie zombie){
        bossToUpdate = zombie;


        random = new Random();
        setOffTime = 20 + random.nextInt(10);
        LanternsCount = 16 + random.nextInt(24);
    }


    @Override
    public void run() {
        counter++;

        //Gets the closest player
        Player nearestPlayer = null;
        for (Player player : Bukkit.getOnlinePlayers()){
            if (player.getWorld() == bossToUpdate.getWorld()){
                //If nearest player is null
                if (nearestPlayer == null){
                    nearestPlayer = player;
                }
                //Replace nearest player if there is one to replace
                else if (player.getLocation().distance(bossToUpdate.getLocation()) < nearestPlayer.getLocation().distance(bossToUpdate.getLocation()))
                {
                    nearestPlayer = player;
                }
            }
        }


        if (LanternsCount <= 0){
            this.cancel();
        }






        //Warn the players
        if (counter > setOffTime){
            if (canPlaySound){
                canPlaySound = false;
                bossToUpdate.getWorld().playSound(bossToUpdate,Sound.BLOCK_BEACON_ACTIVATE,9, 1.1f);
            }



            LanternsCount--;
            int randomAmount = random.nextInt(1,4);

            for (int i = 0; i<randomAmount;i++){
                Location location = bossToUpdate.getLocation().add(random.nextInt(-12,13),12,random.nextInt(-12,13));
                FallingBlock fallingBlock = bossToUpdate.getWorld().spawnFallingBlock(location, Material.SOUL_LANTERN.createBlockData());
                fallingBlock.setGravity(true);
                fallingBlock.setInvulnerable(true);
                //fallingBlock.setRotation(90,33);
                fallingBlock.setPersistent(true);
                fallingBlock.setCancelDrop(true);
                fallingBlock.setDropItem(true);
                fallingBlock.setHurtEntities(true);
                fallingBlock.setMaxDamage(15);
                fallingBlock.setDamagePerBlock(125);

                //Warning Particles
                bossToUpdate.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, location.add(0,-6,0),15,0.1,6,0.1,0);
                bossToUpdate.getWorld().spawnParticle(Particle.REDSTONE, location.add(0,-6,0),15,0.1,6,0.1,new Particle.DustOptions(Color.BLUE, 1));

            }

        }
    }
}
