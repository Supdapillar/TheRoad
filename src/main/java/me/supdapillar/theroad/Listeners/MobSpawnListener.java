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
import java.util.Objects;

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
        if (!Objects.equals(TheRoadPlugin.getInstance().nextBossIs, "")){
            Zombie bossZombie = (Zombie) mobEntity;
            switch (TheRoadPlugin.getInstance().nextBossIs){
                case "SKYGUARDIAN":
                    bossZombie.getEquipment().clear();
                    bossZombie.setAdult();
                    bossZombie.getEquipment().setHelmet(Heads.Cloud.getItemStack());
                    bossZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(600 + (Bukkit.getOnlinePlayers().size()*300));
                    bossZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.26);
                    bossZombie.setPersistent(true);
                    bossZombie.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                    bossZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                    bossZombie.setHealth(600 + (Bukkit.getOnlinePlayers().size()*300));
                    bossZombie.setCustomName(ChatColor.WHITE + "Sky Guardian");
                    bossZombie.setCustomNameVisible(true);
                    bossZombie.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "BossName"),PersistentDataType.STRING, "Sky Guardian");
                    break;

                case "THEENLIGHTENER":
                    bossZombie.getEquipment().clear();
                    bossZombie.setAdult();
                    bossZombie.getEquipment().setHelmet(Heads.Lantern.getItemStack());
                    bossZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(400 + (Bukkit.getOnlinePlayers().size()*200));
                    bossZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.26);
                    bossZombie.setPersistent(true);
                    bossZombie.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                    bossZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                    bossZombie.setHealth(400 + (Bukkit.getOnlinePlayers().size()*200));
                    bossZombie.setCustomName(ChatColor.WHITE + "The Enlightener");
                    bossZombie.setCustomNameVisible(true);
                    bossZombie.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "BossName"),PersistentDataType.STRING, "The Enlightener");
                    break;
                case "THEGRANDMASTER":
                    bossZombie.getEquipment().clear();
                    bossZombie.setAdult();
                    bossZombie.getEquipment().setHelmet(Heads.ChessHead.getItemStack());
                    bossZombie.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(600 + (Bukkit.getOnlinePlayers().size()*300));
                    bossZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.26);
                    bossZombie.setPersistent(true);
                    bossZombie.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1);
                    bossZombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(10);
                    bossZombie.setHealth(600 + (Bukkit.getOnlinePlayers().size()*300));
                    bossZombie.setCustomName(ChatColor.WHITE + "The Grandmaster");
                    bossZombie.setCustomNameVisible(true);
                    bossZombie.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "BossName"),PersistentDataType.STRING, "The Grandmaster");
                    break;
            }
            TheRoadPlugin.getInstance().nextBossIs = "";



        }
        else {
            mobEntity.setCustomName("[" + Math.round(mobEntity.getHealth()) + "❤/" + mobEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            mobEntity.setCustomNameVisible(true);
        }
    }
}
