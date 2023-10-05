package me.supdapillar.theroad.Tasks;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Managers.GameManager;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.enums.Gamestates;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GameEndDelayer extends BukkitRunnable {
    private int timer = 0;
    GameManager gameManager;
    public GameEndDelayer(GameManager gm){
        gameManager = gm;

    }
    @Override
    public void run() {
        timer++;

        //Spawns fireworks around the players
        for(Player player : Bukkit.getOnlinePlayers()){
            player.setGameMode(GameMode.SPECTATOR);


            Location fireworkSpawnLocation = player.getLocation().add((-0.5+Math.random()*8), -3, (-0.5+Math.random())*8);

            Firework firework = (Firework) player.getWorld().spawnEntity(fireworkSpawnLocation, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.addEffect(FireworkEffect.builder().trail(true).flicker(true).withColor(Color.YELLOW).build());
            fireworkMeta.setPower(2);

            firework.setFireworkMeta(fireworkMeta);

        }


        //Resets the game and sends players back
        if (timer > 10){
            gameManager.gamestates = Gamestates.lobby;

            for (Player player : Bukkit.getOnlinePlayers()){

                player.getInventory().clear();

                player.setGameMode(GameMode.ADVENTURE);
                StarterItems.GiveClassCompass(player);

                StarterItems.GiveUnreadyConcrete(player);
                StarterItems.GiveTalismanTotem(player);
                StarterItems.GiveMapSelection(player);
                double randomAngle = (Math.PI*2) * Math.random();
                Location location = new Location(Bukkit.getWorld("minigame"),165.5 + Math.cos(randomAngle)*15,-49,31.5 + Math.sin(randomAngle)*15);
                player.teleport(location);
            }

            //Stops this task from running
            this.cancel();
        }
    }
}
