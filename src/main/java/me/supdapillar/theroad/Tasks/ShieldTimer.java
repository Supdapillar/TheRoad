package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Talisman.ShieldTalisman;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ShieldTimer extends BukkitRunnable {
    public Player shieldOwner;
    public ShieldTalisman baseShieldTalisman;

    public ShieldTimer(Player player, ShieldTalisman shieldTalisman){
        shieldOwner = player;
        baseShieldTalisman = shieldTalisman;

    }


    @Override
    public void run() {
        shieldOwner.sendMessage(ChatColor.BLUE + "Shield recharged!");
        shieldOwner.playSound(shieldOwner, Sound.BLOCK_NOTE_BLOCK_BELL, 9999, 0.5f);
        baseShieldTalisman.currentActiveTimers.remove(this);
        this.cancel();
    }
}
