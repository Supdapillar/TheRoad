package me.supdapillar.theroad.Talisman.Challenges;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HealingTouchChallengeTalisman extends Talisman {

    private Player challengeVictim;
    private boolean challengeEnded = false;

    public HealingTouchChallengeTalisman(Player player){
        name = "Healing Touch Challenge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "no no see");
        isChallenge = true;
        countsAsActive = false;

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

        challengeVictim = player;
        challengeVictim.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD  + "You hands are infused with the power of healing!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- All damage you deal heals the enemy!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Get an enemy to over 80hp!");
    }



    public void onMobDamage(EntityDamageByEntityEvent event) {
        if (!challengeEnded){
            if (event.getEntity() instanceof Mob){
                Mob mob = (Mob) event.getEntity();

                mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + Math.ceil(event.getDamage()));
                mob.setHealth(mob.getHealth()+Math.ceil(event.getDamage()));

                //Challenge end
                if (mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() >= 80){
                    challengeEnded = true;
                    challengeVictim.sendMessage(ChatColor.GRAY + "- You survived the healing touch challenge, good luck with that enemy! ;)");
                    TheRoadPlugin.getInstance().PlayerScores.put(challengeVictim,TheRoadPlugin.getInstance().PlayerScores.get(challengeVictim) + 35);
                    challengeVictim.sendMessage(ChatColor.GREEN + "Challenge Complete +35$");
                    ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                }
                event.setDamage(0);
                //Corrects the display name
                if (mob.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(), "BossName"), PersistentDataType.STRING)){
                    mob.setCustomName(ChatColor.WHITE + mob.getPersistentDataContainer().get(new NamespacedKey(TheRoadPlugin.getInstance(),"BossName"),PersistentDataType.STRING) + " [" + Math.ceil(mob.getHealth() - event.getDamage()) + "❤/" + mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
                    mob.setCustomNameVisible(true);
                }
                else { // everything that isn't a boss

                    mob.setCustomName("[" + Math.ceil(mob.getHealth() - event.getDamage()) + "❤/" + mob.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
                    mob.setCustomNameVisible(true);
                }


            }
        }
    }
}
