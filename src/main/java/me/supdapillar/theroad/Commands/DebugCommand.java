package me.supdapillar.theroad.Commands;

import me.supdapillar.theroad.Helpers.DatabaseHandler;
import me.supdapillar.theroad.Helpers.ScoreboardHandler;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.Tasks.SkyGuardian.SkyGuardianUpdater;
import me.supdapillar.theroad.Tasks.TheEnlightener.TheEnlightenerUpdater;
import me.supdapillar.theroad.Tasks.TheGrandmaster.TheGrandmasterUpdater;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Classes;
import me.supdapillar.theroad.enums.Heads;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Objects;

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
                                player.sendMessage(player1.getDisplayName()+ ": " + talisman.name);
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
                        //TextDisplay textDisplay = (TextDisplay) player.getWorld().spawnEntity(player.getLocation(),EntityType.TEXT_DISPLAY,true);
                        ////textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "⭐ Join our discord ⭐");
                        ////textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "❤ dankdankdot ❤" + ChatColor.RESET + " Lead map designer");
                        ////textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "🔥 Supdapillar 🔥" + ChatColor.RESET + " Lead game designer");
                        //textDisplay.setText(ChatColor.BOLD + "" + ChatColor.GREEN + "$❤---Store---❤$" + ChatColor.RESET + " If you wanna support us and our minigames, we have a " + ChatColor.GREEN + "/buy"+ ChatColor.RESET+ " with very cheap items! We appreciate you all!" +ChatColor.RED + " ❤");
                        //textDisplay.setGlowing(true);
                        //textDisplay.setGlowColorOverride(Color.BLACK);
                        //textDisplay.setLineWidth(Integer.parseInt(args[1]));
                        //Transformation transformation = textDisplay.getTransformation();
                        //textDisplay.setRotation(90,0);
                        //textDisplay.setTransformation(transformation);
                        //textDisplay.setBackgroundColor(Color.fromARGB(0,0,0,0));

                        ItemDisplay itemDisplay = (ItemDisplay) player.getWorld().spawnEntity(player.getLocation(), EntityType.ITEM_DISPLAY,true);
                        Transformation transformation = itemDisplay.getTransformation();
                        transformation.getScale().set(15,1,15);
                        itemDisplay.setItemStack(Heads.Lava.getItemStack());
                        itemDisplay.setTransformation(transformation);
                        itemDisplay.setVelocity(new Vector(0,-20,0));


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
                    case "Save":
                        if (args.length > 1){
                            DatabaseHandler.getInstance().savePlayer(Bukkit.getPlayer(args[1]));
                            ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "/Debug <Save> <Player>");
                        }
                        break;
                    case "Spider":
                            for (ArmorStand armorStand: player.getWorld().getEntitiesByClass(ArmorStand.class)){
                                if (armorStand.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(),"EnemyType"),PersistentDataType.STRING)){
                                    if (Objects.equals(armorStand.getPersistentDataContainer().get(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"), PersistentDataType.STRING), "SPIDER")){
                                       Bukkit.broadcastMessage("Spider deleted");
                                       armorStand.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(), "EnemyType"),PersistentDataType.STRING, "ZOMBIE");
                                    }
                                }
                            }
                        break;
                    case "Slots":
                            if (args.length > 2){
                                TheRoadPlugin.getInstance().TalismanSlots.put(Bukkit.getPlayer(args[1]),TheRoadPlugin.getInstance().PlayerScores.get(Bukkit.getPlayer(args[1])) + Integer.parseInt(args[2]));
                                ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                            }
                            else {
                                player.sendMessage(ChatColor.RED + "/Debug <Slots> <Player> <Amount>");
                            }
                            break;
                    case "Unsupporter":
                        Player SupporterPlayer = Bukkit.getPlayer(args[1]);
                        SupporterPlayer.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Supporter removed!");
                        SupporterPlayer.getPersistentDataContainer().remove(new NamespacedKey(TheRoadPlugin.getInstance(),"Supporter"));
                        break;
                    case "Reset":
                        if (args.length > 1){
                            Player resetPlayer = Bukkit.getPlayer(args[1]);
                            TheRoadPlugin.getInstance().PlayerScores.put(resetPlayer,0);
                            TheRoadPlugin.getInstance().TalismanSlots.put(resetPlayer,1);
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
                    case "Revive":
                        if (args.length> 1){
                            TheRoadPlugin.getInstance().gameManager.respawnPlayer(Bukkit.getPlayer(args[1]),player.getLocation());
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
        else {
            switch (args[0]) {
                case "Supporter":
                    Player SupporterPlayer = Bukkit.getPlayer(args[1]);
                    SupporterPlayer.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Thank you for buying supporter, we really appreciate it!");
                    SupporterPlayer.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(),"Supporter"), PersistentDataType.BOOLEAN, true);
                    break;
                case "Money":
                    Bukkit.getPlayer(args[1]).sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Thank you for your purchase! +500$");

                    TheRoadPlugin.getInstance().PlayerScores.put(Bukkit.getPlayer(args[1]),TheRoadPlugin.getInstance().PlayerScores.get(Bukkit.getPlayer(args[1])) + Integer.parseInt(args[2]));
                    ScoreboardHandler.updateScoreboard(TheRoadPlugin.getInstance());
                    break;
            }
        }
    return true;
    }
}
