package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.Tasks.CrystalLightningAttackTimer;
import me.supdapillar.theroad.Tasks.DefenderShieldTimer;
import me.supdapillar.theroad.Tasks.FireShotgunAttackTimer;
import me.supdapillar.theroad.Tasks.RootOvergrowthAttackTimer;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.gameClasses.Merchant;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Date;

public class InteractListener implements Listener {
    public InteractListener(TheRoadPlugin plugin){
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent event){
        //Block trapdoor opens and fence opens
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType().name().contains("TRAP") || event.getClickedBlock().getType().name().contains("FENCE")){
                if (event.getPlayer().getGameMode() != GameMode.CREATIVE){
                    event.setCancelled(true);
                }
            }
            else if(event.getClickedBlock().getType() == Material.MANGROVE_BUTTON){
                event.getPlayer().sendMessage(  ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "https://discord.gg/bjn5YEtFv");

            }
        }
        if (event.getItem() == null) return;
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
                if (event.getHand() == EquipmentSlot.HAND){
                    player.getInventory().remove(Material.RED_CONCRETE);
                    StarterItems.GiveReadyConcrete(player);
                    player.sendMessage(ChatColor.GREEN + "You are now ready!");
                    player.playSound(player, Sound.ENCHANT_THORNS_HIT, 9999, 1);
                    int totalReadiedPlayers = 0;
                    for(Player player1 : Bukkit.getOnlinePlayers()){
                        if (player1.getInventory().contains(Material.GREEN_CONCRETE)){
                            totalReadiedPlayers++;
                        }
                    }
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getDisplayName() + " is ready! (" + totalReadiedPlayers + "/" + Bukkit.getOnlinePlayers().size() + ")");
                }
                break;
            case GREEN_CONCRETE:
                if (event.getHand() == EquipmentSlot.HAND) {
                    player.getInventory().remove(Material.GREEN_CONCRETE);
                    StarterItems.GiveUnreadyConcrete(player);
                    player.sendMessage(ChatColor.RED + "You are now unready!");
                    player.playSound(player, Sound.ENCHANT_THORNS_HIT, 9999, 1);
                }
                break;
            case TOTEM_OF_UNDYING:
                StarterItems.refreshTalismanMenu(player);
                break;
            case CHEST:
                Merchant.openMerchantShop(player);
                break;
            case ALLIUM:
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK ){ // Shoots a beam and heals anything it touches
                    for(Player player1: Bukkit.getOnlinePlayers()){
                        //Makes sure the player can heal
                        if (player.getLevel() >= 1){
                            if (player1.getGameMode() == GameMode.ADVENTURE){
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
                                            break;
                                        }
                                        else {
                                            player1.setHealth(player1.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());
                                            player.sendMessage(ChatColor.GREEN + player1.getName() + " is already healed!");
                                        }
                                        break;
                                    }
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
                    if (player.getLevel() >= 3) {
                        player.getWorld().playSound(player, Sound.ENTITY_EVOKER_CAST_SPELL, 90, 0.8f);
                        player.setLevel(player.getLevel() -3);
                        //Particles
                        Location pLocation = player.getLocation();
                        double Angle = 0;
                        for(int i = 0; i < 48; i++){
                            Angle -= Math.PI/24f;

                            Location particleLocation = new Location(player.getWorld(), pLocation.getX() + (Math.cos(Angle) * 6f), pLocation.getY(), pLocation.getZ()+ (Math.sin(Angle) * 6f));
                            player.spawnParticle(Particle.REDSTONE, particleLocation, 3, 0.2 ,0.4 ,0.2 ,new Particle.DustOptions(Color.GREEN, 2));
                            player.spawnParticle(Particle.COMPOSTER, particleLocation, 2, 0.2 ,0.3 ,0.2);
                        }
                        //Heals all players and tameables in the radius
                        for (Entity entity: player.getNearbyEntities(7,8,7)){
                            if (entity.getLocation().distance(player.getLocation()) < 6){
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
                                    if (player.getGameMode() == GameMode.ADVENTURE){
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

                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You lack the experience to heal!");
                    }
                }
                break;
            case BLAZE_ROD:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if (player.getLevel() >= 9){
                        player.setLevel(player.getLevel()-9);
                        new FireShotgunAttackTimer(player).runTask(TheRoadPlugin.getInstance());
                        player.playSound(player, Sound.ITEM_FIRECHARGE_USE, 9, 1);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough xp for this spell!");
                    }
                }
                break;
            case AMETHYST_SHARD:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if (player.getLevel() >= 10){
                        player.setLevel(player.getLevel()-10);
                        new CrystalLightningAttackTimer(player).runTaskTimer(TheRoadPlugin.getInstance(), 0 ,2);
                        player.playSound(player, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1,2);
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough xp for this spell!");
                    }
                }
                break;
            case MANGROVE_PROPAGULE:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if (player.getLevel() >= 10){
                        if (player.getTargetBlockExact(25) != null){
                            player.setLevel(player.getLevel()-10);
                            new RootOvergrowthAttackTimer(player).runTaskTimer(TheRoadPlugin.getInstance(), 0 ,2);
                            player.playSound(player, Sound.ITEM_TRIDENT_RETURN, 1,2);
                        }
                        else {
                            player.sendMessage(ChatColor.RED + "Out of range!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough xp for this spell!");
                    }
                }
                break;
            case PRISMARINE_SHARD:
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                    if (player.getLevel() >= 1){
                        player.setLevel(player.getLevel()-1);
                        if (player.getTargetBlockExact(10) != null){
                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, 1f, 2);
                            new DefenderShieldTimer(player).runTaskTimer(TheRoadPlugin.getInstance(),0,0);
                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "You don't have enough xp to manifest a shield!");
                    }
                }
                break;
            case WOLF_SPAWN_EGG:
                event.setCancelled(true);
                ItemStack wolfStack = event.getItem();
                if (wolfStack.getAmount() > 1) {
                    if (summonWolf(player)){
                        wolfStack.setAmount(wolfStack.getAmount() - 1);
                    }
                }
                else {
                    if (summonWolf(player)){
                        wolfStack.setAmount(0);
                    }
                }
                break;
        }
    }

    private boolean summonWolf(Player player){

        //Gets the number of wolves owned by this player
        Object[] allPlayerOwnedWolves = player.getWorld().getEntities().stream().filter(o -> o instanceof Wolf && ((Wolf) o).getOwner() == player).toArray();

        if (allPlayerOwnedWolves.length < 5){
            Wolf wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);


            wolf.setTamed(true);
            wolf.setOwner(player);
            wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10);
            wolf.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.36);

            wolf.setCustomName(ChatColor.BLUE + "[" + wolf.getHealth() + "❤/" + wolf.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
            wolf.setCustomNameVisible(true);
            return  true;
        }
        else {
            player.sendMessage(ChatColor.RED + "You have too many wolves!");
            return false;
        }

    }

}

