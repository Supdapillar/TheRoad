package me.supdapillar.theroad.Talisman.Challenges;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WarpChallengeTalisman extends Talisman {

    private Player challengeVictim;
    private int killsNeeded = 10;
    private boolean challengeEnded = false;

    public WarpChallengeTalisman(Player player){
        name = "Warp- Challenge";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "nononono see");
        isChallenge = true;
        countsAsActive = false;

        inventoryIcon = new ItemStack(Material.BLADE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

        challengeVictim = player;
        challengeVictim.sendMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD  + "The fabric of reality funnels towards you!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Everytime you take damage all the enemies teleport to you!");
        challengeVictim.sendMessage(ChatColor.GRAY +"- Get 10 Kills!");
    }





    @Override
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (!challengeEnded){
            Player player = (Player) event.getEntity();
            player.getWorld().playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT,9,1);
            for (Mob mob : player.getWorld().getEntitiesByClass(Mob.class)){
                mob.teleport(player);
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
                challengeVictim.sendMessage("You survived the warp challenge!");


                TheRoadPlugin.getInstance().PlayerScores.put(challengeVictim,TheRoadPlugin.getInstance().PlayerScores.get(challengeVictim) + 35);
                challengeVictim.sendMessage(ChatColor.GREEN + "Challenge Complete +35$");
                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
            }
        }
    }
}
