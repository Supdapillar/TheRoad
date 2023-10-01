package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

