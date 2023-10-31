package me.supdapillar.theroad.Talisman;

import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulChecker extends Talisman{

    public Player Host;
    public SoulChecker(Player player){

        Host = player;

        name = "You shouldnt see this";
        price = 0;
        lores.add(ChatColor.LIGHT_PURPLE + "Still shouldn't see this ");



        inventoryIcon = new ItemStack(Material.ARMS_UP_POTTERY_SHERD);
        ItemMeta itemMeta = inventoryIcon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BOLD + "" + ChatColor.AQUA + name);
        inventoryIcon.setItemMeta(itemMeta);

    }



    @Override
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        Player player = (Player) event.getEntity();

        //Checks if the player would've died
        if (player.getHealth() - event.getDamage() <= 0) {
            //Checks if the player can be revived
            if (Host != null){
                //Checkc if the host is able to revive
                if (Host.getHealth() > 10 && Host.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > 10){
                    //Revives the player
                    if (Host.getLocation().distance(player.getLocation()) < 6 ){
                        //Particles
                        Location pLocation = Host.getLocation();
                        double Angle = 0;
                        for(int i = 0; i < 30; i++){
                            Angle -= Math.PI/15f;;

                            Location particleLocation = new Location(Host.getWorld(), pLocation.getX() + (Math.cos(Angle) * 6f), pLocation.getY(), pLocation.getZ()+ (Math.sin(Angle) * 6f));
                            player.getWorld().spawnParticle(Particle.HEART, particleLocation, 2, 0 ,0 ,0.5 ,0);
                        }
                        //The revive
                        event.setCancelled(true);

                        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() >= 4){
                            player.setHealth(4);
                        }
                        else {
                            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                        }

                        player.sendMessage("The soul of " + Host.getDisplayName() + " has saved you!");

                        Host.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Host.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2);
                        Host.sendMessage("You revived someone");

                    }
                }
                else {
                    Host.sendMessage(ChatColor.RED + "You were too weak to save " + player.getDisplayName());
                }

            }

        }
    }

    @Override
    public void onTalismanDeselect(Player player){
        //Makes sure all the temperary talismans are deleted
        for(Player player1 : Bukkit.getOnlinePlayers() ){
            for (int i = 0; i < TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).size(); i++){

                if (TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).get(i) instanceof SoulChecker){
                    SoulChecker soulTalisman = (SoulChecker) TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).get(i);
                    if (soulTalisman.Host == player){
                        TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).remove(i);
                    }
                }
            }
        }
    }

    @Override
    public void onTalismanSelect(Player player){
        //Gives all the players a talisman to try and revive them at death
        for (Player player1 : Bukkit.getOnlinePlayers() ){
            if (player1 != player){
                TheRoadPlugin.getInstance().PlayerActiveTalismans.get(player1).add(new SoulChecker(player));
            }
        }
    }


    public void ReviveFriend(Player revivee){

    }
}
