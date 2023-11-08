package me.supdapillar.theroad.Commands;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;

public class ConjuringShrineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;

            if (args.length > 0){

                float rotation = 0;
                switch (args[0]){
                    case "Z+":
                        rotation = 0;
                        break;
                    case "X-":
                        rotation = 90;
                        break;
                    case "Z-":
                        rotation = 180;
                        break;
                    case "X+":
                        rotation = 270;
                        break;
                }

                TextDisplay textDisplay = (TextDisplay) player.getWorld().spawnEntity(player.getLocation(),EntityType.TEXT_DISPLAY,true);
                textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "CONJURING SHRINE [CLICK] FOR AN <ITEM>"+ ChatColor.BLUE + " (15XP) ");
                textDisplay.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "ShrineItem"), PersistentDataType.STRING, "none");
                //textDisplay.setLineWidth(Integer.parseInt(args[1]));
                Transformation transformation = textDisplay.getTransformation();
                textDisplay.setBillboard(Display.Billboard.FIXED);
                textDisplay.setPersistent(true);
                textDisplay.setRotation(rotation,0);
                textDisplay.setTransformation(transformation);
                textDisplay.setBackgroundColor(Color.fromARGB(0,0,0,0));

                player.sendMessage(ChatColor.BLUE + "Conjuring Shrine created");
            }
            else {
                player.sendMessage(ChatColor.RED + "/MakeShrine < X+ | X- | Z+ | Z- >");
            }
        }
    return true;
    }
}
