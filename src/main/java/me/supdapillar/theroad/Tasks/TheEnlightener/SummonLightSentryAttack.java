package me.supdapillar.theroad.Tasks.TheEnlightener;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SummonLightSentryAttack extends BukkitRunnable {
    public Zombie bossToUpdate;
    public int counter = 0;

    private int setOffTime;


    public SummonLightSentryAttack(Zombie zombie){
        bossToUpdate = zombie;


        Random random = new Random();
        setOffTime = 120 + random.nextInt(60);

    }


    @Override
    public void run() {
        counter++;

        bossToUpdate.getWorld().playSound(bossToUpdate, Sound.BLOCK_BEACON_POWER_SELECT, 4, 2);

        Zombie zombie = (Zombie) bossToUpdate.getWorld().spawnEntity(bossToUpdate.getLocation(), EntityType.ZOMBIE);
        zombie.getEquipment().setHelmet(Heads.SeaLantern.getItemStack());
        zombie.setBaby();
        zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(0);
        zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
        new SentryUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
    }
}
