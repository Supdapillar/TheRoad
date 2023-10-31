package me.supdapillar.theroad.Commands;

import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.Tasks.SkyGuardian.SkyGuardianUpdater;
import me.supdapillar.theroad.Tasks.TheEnlightener.TheEnlightenerUpdater;
import me.supdapillar.theroad.Tasks.TheGrandmaster.TheGrandmasterUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.*;
import org.bukkit.block.data.type.Switch;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.util.Transformation;

import javax.xml.crypto.dsig.Transform;
import java.util.ArrayList;

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
                        Zombie zombie;
                        switch (args[1]){
                            case "SkyGuardian":
                                TheRoadPlugin.getInstance().nextBossIs = "SKYGUARDIAN";
                                zombie = (Zombie) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                                new SkyGuardianUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
                                break;
                            case "TheEnlightener":
                                TheRoadPlugin.getInstance().nextBossIs = "THEENLIGHTENER";
                                zombie = (Zombie) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                                new TheEnlightenerUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
                                break;
                            case "TheGrandmaster":
                                TheRoadPlugin.getInstance().nextBossIs = "THEGRANDMASTER";
                                zombie = (Zombie) player.getLocation().getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                                new TheGrandmasterUpdater(zombie).runTaskTimer(TheRoadPlugin.getInstance(), 0, 0);
                                break;
                        }
                        break;
                    case "Display":
                        TextDisplay textDisplay = (TextDisplay) player.getWorld().spawnEntity(player.getLocation(),EntityType.TEXT_DISPLAY,true);
                        textDisplay.setText("Amount of enemies depends on your group");
                        textDisplay.setLineWidth(Integer.parseInt(args[1]));
                        Transformation transformation = textDisplay.getTransformation();
                        textDisplay.setTransformation(transformation);
                        textDisplay.setBackgroundColor(Color.fromARGB(0,0,0,0));
                        break;
                    case "Money":
                        if (args.length > 2){
                            TheRoadPlugin.getInstance().PlayerScores.put(Bukkit.getPlayer(args[1]),TheRoadPlugin.getInstance().PlayerScores.get(Bukkit.getPlayer(args[1])) + Integer.parseInt(args[2]));
                            ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());

                        }
                        else {
                            player.sendMessage(ChatColor.RED + "/Debug <Money> <Player> <Amount>");

                        }
                        break;
                    case "Reset":
                        if (args.length > 1){
                            Player resetPlayer = Bukkit.getPlayer(args[1]);
                            TheRoadPlugin.getInstance().PlayerScores.put(resetPlayer,0);
                            TheRoadPlugin.getInstance().PlayerClass.put(resetPlayer,Classes.Swordsman);
                            TheRoadPlugin.getInstance().PlayerUnlockedClasses.put(resetPlayer, new ArrayList<Classes>());
                            TheRoadPlugin.getInstance().PlayerActiveTalismans.put(player, new ArrayList<Talisman>());
                            TheRoadPlugin.getInstance().PlayerUnlockedTalisman.put(player, new ArrayList<Talisman>());
                            ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                            player.sendMessage("You have reset " + resetPlayer.getDisplayName());

                        }
                        else {
                            player.sendMessage(ChatColor.RED + "/Reset <Player>");
                        }
                        break;
                    case "ForceStart":
                        TheRoadPlugin.getInstance().gameManager.startGame();
                        TheRoadPlugin.getInstance().counterLoop.counter = 5;
                        break;
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "/Debug <ListTalisman | Summon | Money>");
            }
        }
    return true;
    }
}
