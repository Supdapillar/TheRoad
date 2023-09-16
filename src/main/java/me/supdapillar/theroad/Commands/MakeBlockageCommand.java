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

public class MakeBlockageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player ){
            Player player = (Player) commandSender;
            if (args.length < 2){
                player.sendMessage(ChatColor.RED + "/MakeBlockage < X | Y > <Round> <Length>");
            }
            else {
                ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

                PersistentDataContainer armorStandData = armorStand.getPersistentDataContainer();
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "Axis"), PersistentDataType.STRING, args[0]);
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "BlockageRound"), PersistentDataType.INTEGER, Integer.parseInt(args[1]));
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "Length"), PersistentDataType.INTEGER, Integer.parseInt(args[2]));
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setMarker(true);
                player.sendMessage(args[0]);
            }
        }
        return true;
    }
}
