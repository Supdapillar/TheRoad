package me.supdapillar.theroad.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (args.length == 1){
                player.teleport(new Location(Bukkit.getWorld(args[0]),0,-60,0));
            }
            else {
                player.sendMessage(ChatColor.RED + "/ChangeWorld <WorldName>");
                player.sendMessage(ChatColor.RED + "Current Loaded Worlds:");
                for(World world : Bukkit.getWorlds()){
                    player.sendMessage(ChatColor.RED + world.getName());
                }
            }
        }
    return true;
    }
}
