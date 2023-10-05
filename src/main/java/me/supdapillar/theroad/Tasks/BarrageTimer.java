package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Talisman.BarrageTalisman;
import me.supdapillar.theroad.Talisman.ShieldTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class BarrageTimer extends BukkitRunnable {
    public Mob Victim;
    public Player Damager;
    private int hitRemaining;

    public BarrageTimer(Player player,Mob thingToHit){
        Victim = thingToHit;
        Damager = player;
        Random random = new Random();
        hitRemaining = random.nextInt(2,6);
    }


    @Override
    public void run() {
        hitRemaining--;
        Victim.damage(1,Damager);

        Victim.getWorld().spawnParticle(Particle.REDSTONE, Victim.getLocation().add(0,0.5,0), 14,0.4,0.8,0.4, new Particle.DustOptions(Color.BLACK, (float) (1+Math.random())));
        if (hitRemaining <= 0){
            BarrageTalisman.currentActiveTimers.remove(this);
            this.cancel();
        }
    }
}
