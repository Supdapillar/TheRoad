package me.supdapillar.theroad.Commands;

import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.Tasks.SkyGuardian.SkyGuardianUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

public class DebugCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (args.length > 0){
                switch (args[0]){
                    case "ListTalisman":
                        for(Player player1 : Bukkit.getOnlinePlayers()){
                            for (Talisman talisman : TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1)){
                                Bukkit.broadcastMessage(player1.getDisplayName()+ ": " + talisman.name);
                            }
                        }

                        break;
                    case "Summon":
                        switch (args[1]){
                            case "SkyGuardian":
                                TheRoadPlugin.getInstance().nextMobIsBoss = true;
                                Zombie zombie = (Zombie) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                                new SkyGuardianUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
                                break;
                        }
                        break;

                }
            }
            else {
                player.sendMessage(ChatColor.RED + "/Debug <ListTalisman | Summon>");
            }
        }
    return true;
    }
}
