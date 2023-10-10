package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.sql.DataTruncation;
import java.util.Date;

public class InteractListener implements Listener {
    public InteractListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event){
        if (event.getItem() == null) return;
        if (event.getAction() == Action.PHYSICAL) return;

        Player player = event.getPlayer();



        switch(event.getItem().getType()){
            case RECOVERY_COMPASS:
                StarterItems.refreshClassInventory(player);
                break;
            case MAP:
                StarterItems.refreshMapInventory(player);
                event.setCancelled(true);
                break;
            case RED_CONCRETE:
                player.getInventory().remove(Material.RED_CONCRETE);
                StarterItems.GiveReadyConcrete(player);
                player.sendMessage(ChatColor.GREEN + "You are now ready!");
                player.playSound(player, Sound.ENCHANT_THORNS_HIT, 9999, 1);
                break;
            case GREEN_CONCRETE:
                player.getInventory().remove(Material.GREEN_CONCRETE);
                StarterItems.GiveUnreadyConcrete(player);
                player.sendMessage(ChatColor.RED + "You are now unready!");
                player.playSound(player, Sound.ENCHANT_THORNS_HIT, 9999, 1);
                break;
            case TOTEM_OF_UNDYING:
                StarterItems.refreshTalismanMenu(player);
                break;
            case ALLIUM:
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK ){ // Shoots a beam and heals anything it touches
                    for(Player player1: Bukkit.getOnlinePlayers()){
                        //Makes sure the player can heal
                        if (player.getLevel() > 1){
                            player.playSound(player,Sound.ENTITY_SHULKER_SHOOT,  9999, 1.2f);
                            //Checks if the player is within line of sight
                            if (player1.getWorld() == player.getWorld()){
                                Location eye = player.getEyeLocation();
                                Vector toEntity = player1.getEyeLocation().toVector().subtract(eye.toVector());

                                double dot = toEntity.normalize().dot(eye.getDirection());




                                if (dot > 0.99D){
                                    if (player1.getHealth() + 1 < player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()){
                                        player1.setHealth(player1.getHealth()+1);
                                        player1.sendMessage(ChatColor.GREEN + "You've been healed by " +player.getName());

                                        player.setLevel(player.getLevel()-1);//TODO change
                                        player.sendMessage(ChatColor.GREEN + "Healed "+ player1.getName());


                                        Vector vector = new Vector(player1.getLocation().subtract(player.getLocation()).getX(), player1.getLocation().subtract(player.getLocation()).getY(), player1.getLocation().subtract(player.getLocation()).getZ());
                                        //Spawn particles
                                        for(int i = 0; i < (int)player.getLocation().distance(player1.getLocation()) * 4; i++){
                                            Location particleLocation = new Location(player.getWorld(), player.getEyeLocation().getX() + vector.normalize().getX() * i/4, player.getEyeLocation().getY() +  vector.normalize().getY() * i/4,player.getEyeLocation().getZ() +  vector.normalize().getZ() * i/4);
                                            player.getWorld().spawnParticle(Particle.REDSTONE,particleLocation ,1, 0.2,0.2,0.2, new Particle.DustOptions(Color.GREEN, 2));
                                            player.getWorld().spawnParticle(Particle.COMPOSTER,particleLocation ,1, 0.3,0.3,0.3);
                                        }
                                    }
                                    else {
                                        player1.setHealth(player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                                        player.sendMessage(ChatColor.GREEN + player1.getName() + " is already healed!");
                                    }
                                    break;
                                }
                            }
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "You lack the experience to heal!");
                        }

                    }
                }
                //Circle spell heal
                else {
                    //Makes sure the player can heal
                    if (player.getLevel() > 3) {
                        player.getWorld().playSound(player, Sound.ENTITY_EVOKER_CAST_SPELL, 90, 0.8f);
                        player.setLevel(player.getLevel() -3);
                        //Particles
                        Location pLocation = player.getLocation();
                        double Angle = 0;
                        for(int i = 0; i < 36; i++){
                            Angle -= Math.PI/18f + new Date(System.currentTimeMillis()).getTime()*80;;

                            Location particleLocation = new Location(player.getWorld(), pLocation.getX() + (Math.cos(Angle) * 3.75f), pLocation.getY(), pLocation.getZ()+ (Math.sin(Angle) * 4f));
                            player.spawnParticle(Particle.REDSTONE, particleLocation, 3, 0.2 ,0.4 ,0.2 ,new Particle.DustOptions(Color.GREEN, 2));
                            player.spawnParticle(Particle.COMPOSTER, particleLocation, 2, 0.2 ,0.3 ,0.2);
                        }
                        //Heals all players and tameables in the radius
                        for (Entity entity: player.getNearbyEntities(5,8,5)){
                            if (entity.getLocation().distance(player.getLocation()) < 4){
                                if (entity instanceof Tameable){
                                    Tameable tameable = (Tameable) entity;
                                    //Is less than full
                                    if ((tameable.getHealth() + 2) < tameable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()){
                                        tameable.setHealth(tameable.getHealth()+2);
                                    }
                                    else {
                                        tameable.setHealth(tameable.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                                    }
                                }
                                else if (entity instanceof Player) {
                                    Player player1 = (Player) entity;
                                    //Is less than full
                                    if ((player1.getHealth() + 2) < player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()){
                                        player1.setHealth(player1.getHealth()+2);
                                        player1.sendMessage(ChatColor.GREEN + "You've been healed by " +player.getName());
                                        player.sendMessage("You healed "+ player1.getName());
                                    }
                                    else {
                                        player1.setHealth(player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                                    }
                                }
                            }

                        }

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You lack the experience to heal!");
                    }
                }
                break;
            case WOLF_SPAWN_EGG:
                event.setCancelled(true);
                ItemStack wolfStack = event.getItem();
                if (wolfStack.getAmount() > 1) {

                    wolfStack.setAmount(wolfStack.getAmount() - 1);
                    summonWolf(player);

                }
                else {

                    wolfStack.setAmount(0);
                    summonWolf(player);

                }
                break;
        }
    }

    private void summonWolf(Player player){

        Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);


        wolf.setTamed(true);
        wolf.setOwner(player);


        wolf.setCustomName(ChatColor.BLUE + "[" + wolf.getHealth() + "❤/" + wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
        wolf.setCustomNameVisible(true);
    }

}

