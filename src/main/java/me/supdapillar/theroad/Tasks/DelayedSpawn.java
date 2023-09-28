package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

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
            TheRoadPlugin.getInstance().gameManager.gameArenas[TheRoadPlugin.getInstance().gameManager.currentArena].spawnLocation.getWorld().spawnParticle(Particle.COMPOSTER, whereToSummon.getLocation().add(0,0.5,0),3,
                     (double) tickCounter /30,1,(double) tickCounter /30);



        //Summons the entity and ends the task
        if (tickCounter == 20){
            PersistentDataContainer dataContainer = whereToSummon.getPersistentDataContainer();
            whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.valueOf(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING)), true);
            TheRoadPlugin.getInstance().gameManager.currentActiveSpawners.remove(this);
            this.cancel();
        }

    }
}
