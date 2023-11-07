package me.supdapillar.theroad.Talisman.Challenges;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PoisonChallengeTalisman extends Talisman {

    private Player challengeVictim;
    private int killsNeeded = 3;
    private boolean challengeEnded = false;

    public PoisonChallengeTalisman(Player player){
        name = "Poison Challenge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "nono see");
        isChallenge = true;
        countsAsActive = false;

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

        challengeVictim = player;
        challengeVictim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,99999999,0,true,true,true));
        challengeVictim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD  + "You feel yourself rotting!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- You have fatal poison!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Get 3 Kills before you die!");
    }

    @Override
    public void onMobDeath(EntityDeathEvent event) {
        if (killsNeeded > 0){
            killsNeeded--;
            if (killsNeeded != 0)challengeVictim.sendMessage(killsNeeded + " kills remaining!");
        }

        if (killsNeeded <= 0){
            if (!challengeEnded){
                challengeEnded = true;
                challengeVictim.sendMessage("You survived the poison challenge!");
                challengeVictim.removePotionEffect(PotionEffectType.WITHER);
                TheRoadPlugin.getInstance().PlayerScores.put(challengeVictim,TheRoadPlugin.getInstance().PlayerScores.get(challengeVictim) + 30);
                challengeVictim.sendMessage(ChatColor.GREEN + "Challenge Complete +30$");
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
            }
        }
    }
}
