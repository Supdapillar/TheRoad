package me.supdapillar.theroad.Commands;

import com.sun.tools.javac.jvm.Items;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Vector3f;

public class MakeLootGeneratorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "/MakeLootGenerator <1-100 Chance>");
            }
            else {
                //Makes the armor stand that spawns the loot chest
                ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

                PersistentDataContainer armorStandData = armorStand.getPersistentDataContainer();
                armorStandData.set(new NamespacedKey(TheRoadPlugin.getInstance(), "SpawnChance"), PersistentDataType.INTEGER, Integer.parseInt(args[0]));
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setMarker(true);
                player.sendMessage(ChatColor.BLUE + "Loot generator created");
            }



            return true;

        }
        else {
            return false;

        }




    }
}
