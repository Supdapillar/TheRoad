package me.supdapillar.theroad.Listeners;

import me.supdapillar.theroad.Helpers.StarterItems;
import me.supdapillar.theroad.TheRoadPlugin;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
            case SILVERFISH_SPAWN_EGG:
                event.setCancelled(true);
                ItemStack itemStack = event.getItem();
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemStack.getAmount() > 1) {

                    itemStack.setAmount(itemStack.getAmount() - 1);
                    summonSilverfish(player);

                }
                else {

                    itemStack.setAmount(0);
                    summonSilverfish(player);

                }
                break;
        }
    }

    private void summonSilverfish(Player player){

        TheRoadPlugin.getInstance().nextMobIsSummoned = true;

        Silverfish silverfish = (Silverfish) player.getWorld().spawnEntity(player.getLocation(), EntityType.SILVERFISH);
        PersistentDataContainer silverFishData = silverfish.getPersistentDataContainer();

        //Sets the summoner as the summoner player
        NamespacedKey summonedKey = new NamespacedKey(TheRoadPlugin.getInstance(), "summonedby");
        silverFishData.set(summonedKey, PersistentDataType.STRING, player.getDisplayName());

        //Makes the silverfish stronger
        silverfish.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(2D);
        silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(10D);
        silverfish.setHealth(10);
        Bukkit.broadcastMessage(silverfish.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() + "");




        //Makes sure the health matches the updated version
        silverfish.setCustomName(ChatColor.BLUE + "[" + silverfish.getHealth() + "❤/" + silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + "❤]");
        silverfish.setCustomNameVisible(true);
    }
}

