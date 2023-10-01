package me.supdapillar.theroad.Tasks;

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
            PersistentDataContainer dataContainer = whereToSummon.getPersistentDataContainer();
            ///// Handles all the fancy stuff for the boss
            if (Objects.equals(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING), "BOSS1")){
                //The Sky Guardian
                TheRoadPlugin.getInstance().nextMobIsBoss = true;
                whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.ZOMBIE);






            }
            else {
                whereToSummon.getLocation().getWorld().spawnEntity(whereToSummon.getLocation(), EntityType.valueOf(dataContainer.get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING)), true);
            }
            TheRoadPlugin.getInstance().gameManager.currentActiveSpawners.remove(this);
            this.cancel();
        }

    }
}
