package me.supdapillar.theroad.Tasks;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Vibration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class RootSpellAttackTimer extends BukkitRunnable {


    private Mob rootedEnemy;
    private int timeBeforeUnroot = 10;
    AttributeModifier modifier;
    public RootSpellAttackTimer(Mob victim)
    {
        rootedEnemy = victim;
        modifier = new AttributeModifier("Root",-rootedEnemy.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue()/3,AttributeModifier.Operation.ADD_NUMBER);
        rootedEnemy.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).addModifier(modifier);
    }


    @Override
    public void run() {
        timeBeforeUnroot--;



        //ends the timer
        if (timeBeforeUnroot < 0){
            rootedEnemy.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).removeModifier(modifier);
            this.cancel();
        }
        else {
            rootedEnemy.getWorld().spawnParticle(Particle.TOTEM,rootedEnemy.getLocation().add(0, 0.5, 0),2,0.3, 0.8, 0.3, 0);
            rootedEnemy.getWorld().spawnParticle(Particle.COMPOSTER,rootedEnemy.getLocation().add(0, 0.5, 0),2,0.3, 0.8, 0.3, 0);
            rootedEnemy.getWorld().spawnParticle(Particle.REDSTONE,rootedEnemy.getLocation().add(0, 0.5, 0),5,0.3, 0.8, 0.3, new Particle.DustOptions(Color.GREEN, 1.5f));
            rootedEnemy.getWorld().playSound(rootedEnemy, Sound.BLOCK_STEM_STEP, 0.25f, 1);
        }

    }
}
