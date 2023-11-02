package me.supdapillar.theroad.Commands;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CursedTreasureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

            PersistentDataContainer armorStandData = armorStand.getPersistentDataContainer();
            armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "CanUseTreasure"), PersistentDataType.BOOLEAN, true);
            armorStand.setCustomName(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "CURSED TREASURE [CLICK] TO START EVENT");
            armorStand.setCustomNameVisible(true);
            armorStand.setInvisible(true);
            armorStand.setInvulnerable(true);
            armorStand.setMarker(true);
            player.sendMessage(ChatColor.BLUE + "Cursed Treasure created");

        }
    return true;
    }
}
