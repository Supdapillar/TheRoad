package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MobSpawnListener implements Listener {

    public MobSpawnListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event){
        if (!(event.getEntity() instanceof Mob)) return;
        if (event.getEntity() instanceof Player) return;
        if (event.getEntity() instanceof ArmorStand) return;
        Mob mobEntity = (Mob) event.getEntity();

        //For Bosses
        if (TheRoadPlugin.getInstance().nextMobIsBoss){
            TheRoadPlugin.getInstance().nextMobIsBoss = false;

            Zombie bossZombie = (Zombie) mobEntity;

            bossZombie.getEquipment().clear();
            bossZombie.setAdult();
            bossZombie.getEquipment().setHelmet(Heads.Cloud.getItemStack());
            bossZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(900);
            bossZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
            Bukkit.broadcastMessage("Attack:" + bossZombie.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue() );
            bossZombie.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(15);
            bossZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(15);
            bossZombie.setHealth(900);
            bossZombie.setCustomName(ChatColor.WHITE + "Sky Guardian");
            bossZombie.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "IsBoss"),PersistentDataType.BOOLEAN, true);
        }
        else {
            mobEntity.setCustomName("[" + Math.round(mobEntity.getHealth()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }
    }
}
