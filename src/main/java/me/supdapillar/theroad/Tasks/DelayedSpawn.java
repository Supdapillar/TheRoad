package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Tasks.SkyGuardian.SkyGuardianUpdater;
import me.supdapillar.theroad.Tasks.TheEnlightener.TheEnlightenerUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class DelayedSpawn extends BukkitRunnable {
    private int tickCounter = 0;
    private final ArmorStand whereToSummon;

    public DelayedSpawn(ArmorStand armorStand){
        whereToSummon = armorStand;
    }
    @Override
    public void run() {
        tickCounter++;

        //Warns the player of incoming entity
        TheRoadPlugin.getInstance().gameManager.gameArenas[TheRoadPlugin.getInstance().gameManager.currentArena].spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, whereToSummon.getLocation().add(0,0.5,0),4,
                (double) tickCounter /30,1,(double) tickCounter /30);



        //Summons the entity and ends the task
        if (tickCounter == 30){
            ///// Boss Spawner
            PersistentDataContainer dataContainer = whereToSummon.getPersistentDataContainer();

            if (Objects.equals(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING), "SKYGUARDIAN")){
                //////The Sky Guardian
                TheRoadPlugin.getInstance().nextBossIs = "SKYGUARDIAN";
                Zombie zombie = (Zombie) whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.ZOMBIE);
                new SkyGuardianUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);





            }
            else if (Objects.equals(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING), "THEENLIGHTENER")){
                //////THE ENLIGHTENER
                TheRoadPlugin.getInstance().nextBossIs = "THEENLIGHTENER";
                Zombie zombie = (Zombie) whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.ZOMBIE);
                new TheEnlightenerUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);

            }
            /////Normal mob spawn
            else {
                whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.valueOf(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING)), true);
            }
            TheRoadPlugin.getInstance().gameManager.currentActiveSpawners.remove(this);
            this.cancel();
        }

    }
}
