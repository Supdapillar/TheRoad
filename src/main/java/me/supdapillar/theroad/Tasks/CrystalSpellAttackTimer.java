package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Talisman.BarrageTalisman;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Vibration;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class CrystalSpellAttackTimer extends BukkitRunnable {
    private EntityDamageByEntityEvent event;

    public CrystalSpellAttackTimer(EntityDamageByEntityEvent e){
        event = e;
    }


    @Override
    public void run() {

        Player player = (Player) event.getDamager();

        Random random = new Random();
        Object[] array = event.getEntity().getNearbyEntities(5, 5, 5).stream().filter(o -> o instanceof Mob).toArray();
        if (array.length > 0){
            Mob mob = (Mob) array[random.nextInt(0, array.length)];
            mob.damage(event.getDamage(), player);
            //Particle
            event.getEntity().getWorld().spawnParticle(Particle.SPELL_WITCH, event.getEntity().getLocation().add(0,0.5,0), 12, 0.25, 1, 0.25, 0);
            mob.getWorld().spawnParticle(Particle.SPELL_WITCH, mob.getLocation().add(0,0.5,0), 12, 0.5, 1, 0.5, 0);

            Vibration vibration = new Vibration(((Mob) event.getEntity()).getEyeLocation(), new Vibration.Destination.EntityDestination(mob), 5);
            event.getEntity().getWorld().spawnParticle(Particle.VIBRATION, event.getEntity().getLocation(), 1, vibration);
            player.playSound(player, Sound.BLOCK_AMETHYST_BLOCK_RESONATE, 9, 1);
        }
    }
}
