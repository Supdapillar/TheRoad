package me.supdapillar.theroad.Talisman.Challenges;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WeaknessChallengeTalisman extends Talisman {

    private Player challengeVictim;
    private int killsNeeded = 5;
    private double oldHealth = 0;
    private double oldMaxHealth = 0;
    private boolean challengeEnded = false;

    public WeaknessChallengeTalisman(Player player){
        name = "Weakness Challenge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "nono see");
        isChallenge = true;
        countsAsActive = false;

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

        challengeVictim = player;
        challengeVictim.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD  + "You feel your bones becoming very delicate!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- You have half a heart!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Get 5 Kills to restore your strength!");
        //Saves old health and set new health to 1
        oldHealth = challengeVictim.getHealth();
        oldMaxHealth = challengeVictim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        challengeVictim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(1);
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
                challengeVictim.sendMessage("You survived the weakness challenge!");
                challengeVictim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(oldMaxHealth);
                challengeVictim.setHealth(oldHealth);



                TheRoadPlugin.getInstance().PlayerScores.put(challengeVictim,TheRoadPlugin.getInstance().PlayerScores.get(challengeVictim) + 40);
                challengeVictim.sendMessage(ChatColor.GREEN + "Challenge Complete +40$");
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
            }
        }
    }
}
