package me.supdapillar.theroad.Talisman.Challenges;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FistChallengeTalisman extends Talisman {

    private Player challengeVictim;
    private int killsNeeded = 2;
    private boolean challengeEnded = false;

    public FistChallengeTalisman(Player player){
        name = "Fist Challenge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "no no see");
        isChallenge = true;
        countsAsActive = false;

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

        challengeVictim = player;
        challengeVictim.sendMessage(ChatColor.WHITE + "" + ChatColor.BOLD  + "You feel yourself forgetting!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- You've forgot how to use weapons!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Get 2 Kills with out ever using a weapon!");
    }



    public void onMobDamage(EntityDamageByEntityEvent event) {
        if (challengeVictim.getInventory().getItemInMainHand().getType() != Material.AIR){
            if (!challengeEnded){
                challengeVictim.sendMessage(ChatColor.RED + "You failed the challenge!");
                challengeEnded = true;
            }
        }
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
                challengeVictim.sendMessage(ChatColor.GRAY + "- You survived the fist only challenge!");
                TheRoadPlugin.getInstance().PlayerScores.put(challengeVictim,TheRoadPlugin.getInstance().PlayerScores.get(challengeVictim) + 25);
                challengeVictim.sendMessage(ChatColor.GREEN + "Challenge Complete +25$");
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
            }
        }
    }
}
