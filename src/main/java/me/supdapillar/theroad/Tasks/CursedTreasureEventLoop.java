package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CursedTreasureEventLoop extends BukkitRunnable {

    public ArmorStand centerPointArmorStand = null;
    public static CursedTreasureEventLoop activeCursedTreasure = null;
    private int enemiesLeftToSpawn = 0;
    private String[] enemyTypes = {"ZOMBIE","WITHER_SKELETON","SKELETON","STRAY","SPIDER"};
    private Random random = new Random();

    public CursedTreasureEventLoop(ArmorStand armorStand){
        centerPointArmorStand = armorStand;
        enemiesLeftToSpawn = random.nextInt(5,9+Bukkit.getOnlinePlayers().size());

        activeCursedTreasure = this;
    }
    @Override
    public void run() {


        //Name Updater
        centerPointArmorStand.setCustomName(ChatColor.RED + "☠ KILL ALL ENEMIES ☠");

        //Spawn mob in circle around armorstand
        if (enemiesLeftToSpawn > 0){
            Location enemySpawn = centerPointArmorStand.getLocation();
            double randomAngle = (Math.PI *2) * Math.random();
            enemySpawn.add(Math.cos(randomAngle)*3,-1,Math.sin(randomAngle)*3);
            Mob mob = (Mob) centerPointArmorStand.getWorld().spawnEntity(enemySpawn, EntityType.valueOf(enemyTypes[random.nextInt(enemyTypes.length)]));
            mob.setCustomName("Treasure Guard [" + mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*2 + "/"+ mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*2+"❤]");
            mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()*2);
            mob.setHealth(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
            enemiesLeftToSpawn--;
            enemySpawn.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME,enemySpawn, 12, 0.25,1,0.25, 0.1);
            enemySpawn.getWorld().playSound(enemySpawn,Sound.PARTICLE_SOUL_ESCAPE,4,1);
        }





        //When all the enemies are killed spawn loot
        if (!AreZombiesLeft()){
            if (enemiesLeftToSpawn < 1) {
                //Spawns the chests
                Location location = centerPointArmorStand.getLocation();
                switch (enemiesLeftToSpawn){
                    case 0:
                        location.add(2,-1,0);
                        break;
                    case -1:
                        location.add(-2,-1,0);
                        break;
                    case -2:
                        location.add(0,-1,2);
                        break;
                    case -3:
                        location.add(0,-1,-2);
                        cancel();
                        activeCursedTreasure = null;
                        centerPointArmorStand.setCustomName(ChatColor.GRAY + "CURSED TREASURE ALREADY USED");
                        break;
                }
                location.getWorld().spawnParticle(Particle.LAVA,location, 12, 0.5,1,0.5, 0);
                location.getWorld().playSound(location,Sound.ENTITY_GENERIC_EXPLODE,3,1);
                TheRoadPlugin.getInstance().gameManager.makeLootChest(location);
                enemiesLeftToSpawn--;
            }
        }
    }

    private boolean AreZombiesLeft() {
        //Check for remaining zombies
        boolean areZombiesLeft = false;
        for (Entity entity : centerPointArmorStand.getWorld().getEntities()){
            if (entity instanceof Zombie){
                areZombiesLeft = true;
                break;
            }
            if (entity instanceof Skeleton){
                areZombiesLeft = true;
                break;
            }
            if (entity instanceof WitherSkeleton){
                areZombiesLeft = true;
                break;
            }
            if (entity instanceof Spider){
                areZombiesLeft = true;
                break;
            }
            if (entity instanceof Stray){
                areZombiesLeft = true;
                break;
            }
        }
        return areZombiesLeft;
    }
}
