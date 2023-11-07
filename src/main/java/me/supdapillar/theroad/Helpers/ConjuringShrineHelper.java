package me.supdapillar.theroad.Helpers;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ConjuringShrineHelper {

    private static Random random = new Random();
    public static HashMap<String, Integer> itemCosts = new HashMap<String, Integer>() {{
       put("Golden Apple",15);

    }};




    public static void generateShrines(World world){
        for (TextDisplay textDisplay : world.getEntitiesByClass(TextDisplay.class)){
            if (textDisplay.getPersistentDataContainer().has(new NamespacedKey(TheRoadPlugin.getInstance(),"ShrineItem"), PersistentDataType.STRING )){
                ArrayList<String> allNames = new ArrayList<>();
                for(String s : itemCosts.keySet()){
                    allNames.add(s);
                }
                int RandomIndex = random.nextInt(allNames.size());
                textDisplay.setText(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "CONJURING SHRINE [CLICK] FOR A" + allNames.get(RandomIndex) + ChatColor.BLUE + itemCosts.get(allNames.get(RandomIndex))+"$");
                textDisplay.getPersistentDataContainer().set(new NamespacedKey(TheRoadPlugin.getInstance(),"ShrineItem"),PersistentDataType.STRING, allNames.get(RandomIndex));

                makeShrineDisplay(textDisplay.getLocation());


            }
        }
    }

    public static void makeShrineDisplay(Location location){

        BlockData barrelData = Material.AIR.createBlockData();
        FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location, barrelData);
        fallingBlock.setVelocity(new Vector(0,0,0));
        fallingBlock.setGravity(false);
        fallingBlock.setInvulnerable(true);
        fallingBlock.setRotation(90,33);
        fallingBlock.setPersistent(true);
        fallingBlock.setCancelDrop(true);
        fallingBlock.setDropItem(true);

    }
}
