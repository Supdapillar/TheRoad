package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class CounterLoop extends BukkitRunnable {
    public int counter = 10;
    public int mobStallCounter = 80;

    @Override
    public void run() {
        switch (TheRoadPlugin.getInstance().gameManager.gamestates){
            case lobby:
                if (!Bukkit.getOnlinePlayers().isEmpty()){
                    int readyPlayers = 0;

                    for(Player player : Bukkit.getServer().getOnlinePlayers()){
                        if (player.getInventory().contains(Material.GREEN_CONCRETE)){
                            readyPlayers++;
                        }
                    }

                    if (readyPlayers >= Math.ceil((double) Bukkit.getOnlinePlayers().size() /2)){
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "Game will begin in 30");
                        counter = 30;
                        counter--;
                        TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.starting;
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 9999, 1f);
                        }
                    }
                }
                break;
            case starting:
                //Lowers the counter
                if (counter > 0){
                    counter--;
                    if (counter <= 5 || counter == 30 || counter == 20 || counter == 10){
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "Game will begin in " + counter);
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BELL, 9999, 1f);
                        }
                    }
                }
                else {
                    // Beginning the game
                    TheRoadPlugin.getInstance().gameManager.startGame();
                    counter=5;
                }
                //Need players for a game to be started
                if (Bukkit.getOnlinePlayers().isEmpty()){
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Not all players ready, stopping countdown");
                    TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.lobby;
                }
                //Checks if players are unready
                int unreadyPlayers = 0;
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    if (player.getInventory().contains(Material.RED_CONCRETE)){
                        unreadyPlayers++;
                    }
                }
                if (unreadyPlayers > Math.ceil((double) Bukkit.getOnlinePlayers().size() /2) || unreadyPlayers == Bukkit.getOnlinePlayers().size()){
                    Bukkit.broadcastMessage(ChatColor.YELLOW + "Not all players ready, stopping countdown");
                    TheRoadPlugin.getInstance().gameManager.gamestates = Gamestates.lobby;
                }
                else if (unreadyPlayers == 0){
                    if (counter > 5){
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "All players ready, game will start in 5");
                        counter=5;
                    }
                }
                break;
                case inGame:
                    //Prevents players from stalling
                    mobStallCounter--;
                    if (mobStallCounter == 40){
                        Bukkit.broadcastMessage(ChatColor.RED + "Damage enemies or game will restart in: 40");
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player,Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 9999, 1);
                        }
                    }
                    else if (mobStallCounter < 11){
                        Bukkit.broadcastMessage(ChatColor.RED + "Damage enemies or game will restart in: " + mobStallCounter);
                        for(Player player : Bukkit.getOnlinePlayers()){
                            player.playSound(player,Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 9999, 1);
                        }
                        if (mobStallCounter == 0){
                            Bukkit.broadcastMessage(ChatColor.RED + "Damage was not dealt to enemies game restarting...");
                            TheRoadPlugin.getInstance().gameManager.resetGame(false);

                        }
                    }

                    //Makes sure there are no zombies
                    Arena currentArena = TheRoadPlugin.getInstance().gameManager.gameArenas[(TheRoadPlugin.getInstance().gameManager.currentArena)];
                    //Make sure all the loot containers dont despawn
                    for(Entity entity : currentArena.spawnLocation.getWorld().getEntities()){
                        if (entity instanceof FallingBlock){
                            entity.setTicksLived(1);
                        }
                    }

                    //Check for remaining zombies
                    boolean areZombiesLeft = false;
                    for (Entity entity : currentArena.spawnLocation.getWorld().getLivingEntities()){
                        if (entity instanceof Zombie){
                            areZombiesLeft = true;
                            break;
                        }
                        if (entity instanceof Skeleton){
                            areZombiesLeft = true;
                            break;
                        }
                        if (entity instanceof WitherSkeleton){
                            areZombiesLeft = true;
                            break;
                        }
                        if (entity instanceof Spider){
                            areZombiesLeft = true;
                            break;
                        }
                        if (entity instanceof Stray){
                            areZombiesLeft = true;
                            break;
                        }
                    }

                    if (!areZombiesLeft && TheRoadPlugin.getInstance().gameManager.currentActiveSpawners.isEmpty()){
                        if (counter > 0){
                            if (counter == 5){
                                TheRoadPlugin.getInstance().gameManager.currentRound++;
                            }
                            //Normal Round text
                            if (TheRoadPlugin.getInstance().gameManager.currentRound < currentArena.finalRound-1){
                                Bukkit.broadcastMessage(ChatColor.YELLOW + "Next round in: " + counter);
                            }
                            //Boss round text
                            else if (TheRoadPlugin.getInstance().gameManager.currentRound == (currentArena.finalRound-1)){
                                Bukkit.broadcastMessage(ChatColor.YELLOW + "Boss round in: " + counter);
                            }
                            //Last round text
                            else {
                                Bukkit.broadcastMessage(ChatColor.YELLOW + "Game finish in: " + counter);
                            }
                            counter--;
                        }
                        else
                        {
                            //Normal Round
                            if (TheRoadPlugin.getInstance().gameManager.currentRound != currentArena.finalRound){
                                Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "ROUND " + TheRoadPlugin.getInstance().gameManager.currentRound);
                                counter = 5;
                            }
                            //// ENDING THE GAME
                            else {
                                Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "YOU'VE ESCAPED THE ROAD");
                                TheRoadPlugin.getInstance().gameManager.resetGame(true);
                            }
                            TheRoadPlugin.getInstance().gameManager.summonWave();


                        }

                    }
                    break;
        }
    }
}
