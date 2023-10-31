package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Listeners.MobDeathListener;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PrizeTalisman extends Talisman{

    public PrizeTalisman(){
        name = "Prize Talisman";
        price = 400;
        lores.add(ChatColor.LIGHT_PURPLE + "The lower your health");
        lores.add(ChatColor.LIGHT_PURPLE + "the more money you get!");

        inventoryIcon = new ItemStack(Material.PRIZE_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }

    @Override
    public void onMobDeath(EntityDeathEvent event) {
        //Xp multiplier
        Player player = event.getEntity().getKiller();
        double XPMultiplier =Math.max(0,Math.abs( player.getHealth() - 21)/20);
        TheRoadPlugin mainPlugin = TheRoadPlugin.getInstance();
        Player killer = event.getEntity().getKiller();
        mainPlugin.PlayerScores.putIfAbsent(killer, 0);

        int extraMoney = (int)(Math.floor(MobDeathListener.moneyValues.get(event.getEntity().getType())*XPMultiplier));
        mainPlugin.PlayerScores.put(killer,mainPlugin.PlayerScores.get(killer) + extraMoney);
        ScoreboardHandler.updateScoreboard(mainPlugin);
        if (extraMoney > 0){
            player.sendMessage(ChatColor.DARK_PURPLE + "+ Bonus " + extraMoney +"$" );
            for(int i = 0; i < extraMoney; i++){
                event.getEntity().getWorld().spawnParticle(Particle.SPELL_WITCH, event.getEntity().getEyeLocation(), 1, 0.5, 1, 0.5);
            }
        }
    }
}
