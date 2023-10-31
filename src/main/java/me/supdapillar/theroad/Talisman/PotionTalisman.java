package me.supdapillar.theroad.Talisman;

import org.bukkit.*;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.Random;

public class PotionTalisman extends Talisman{

    public PotionTalisman(){
        name = "Potion Talisman";
        price = 200;
        lores.add(ChatColor.LIGHT_PURPLE + "Killing an enemy has a chance ");
        lores.add(ChatColor.LIGHT_PURPLE + "of spawning a splash potion! ");


        inventoryIcon = new ItemStack(Material.BREWER_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }
    @Override
    public void onMobDeath(EntityDeathEvent event){
        if (Math.random() > 0.5f){
            ItemStack potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();

            Random random = new Random();
            //Makes a random potion effect
            switch(random.nextInt(10)){
                case 0:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 20*10+random.nextInt(30), random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(253, 255, 132));
                    break;
                case 1:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20*(10+random.nextInt(30)), random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(255, 153, 0));
                    break;
                case 2:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*(10+random.nextInt(30)), random.nextInt(2) ),false);
                    potionMeta.setColor(Color.fromRGB(78,78,78));
                    break;
                case 3:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20*(10+random.nextInt(30)), random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(51, 235, 255));
                    break;
                case 4:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEAL, 20, random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(248, 36, 35));
                    break;
                case 5:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.LEVITATION, 20*(5+random.nextInt(15)), 0 ),false);
                    potionMeta.setColor(Color.fromRGB(246,246,246));
                    break;
                case 6:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*(10+random.nextInt(30)), random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(205, 92, 171));
                    break;
                case 7:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 20*(10+random.nextInt(30)), random.nextInt(3) ),false);
                    potionMeta.setColor(Color.fromRGB(255,255,255));
                    break;
                case 8:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*(5+random.nextInt(2)), random.nextInt(1) ),false);
                    potionMeta.setColor(Color.fromRGB(255, 199, 0));

                    break;
                case 9:
                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*(10+random.nextInt(10)), random.nextInt(1) ),false);
                    potionMeta.setColor(Color.fromRGB(246,246,246));

                    break;
            }
            potion.setItemMeta(potionMeta);

            ThrownPotion thrownPotion = (ThrownPotion) event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.SPLASH_POTION);
            thrownPotion.setItem(potion);
            thrownPotion.setShooter(event.getEntity().getKiller());
        }
    }
}
