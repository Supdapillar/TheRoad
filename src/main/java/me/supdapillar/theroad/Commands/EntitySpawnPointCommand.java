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

public class EntitySpawnPointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (args.length < 2){
                player.sendMessage(ChatColor.RED + "/MakeSpawn <EnemyType> <Round>");
            }
            else {
                ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

                PersistentDataContainer armorStandData = armorStand.getPersistentDataContainer();
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING, args[0]);
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "Round"), PersistentDataType.INTEGER, Integer.parseInt(args[1]));
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                player.sendMessage(args[0]);
                armorStand.setMarker(true);
                player.sendMessage(ChatColor.BLUE + "Spawnpoint created");
            }
            return true;
        }
        else {
         return false;
        }
    }
}
