package me.supdapillar.theroad.Helpers;

import me.supdapillar.theroad.Arenas.Arena;
import me.supdapillar.theroad.Talisman.Talisman;
import me.supdapillar.theroad.TheRoadPlugin;
import me.supdapillar.theroad.gameClasses.GameClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class StarterItems {
    public static void GiveClassCompass(Player player){
        ItemStack compass = new ItemStack(Material.RECOVERY_COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();

        compassMeta.setDisplayName("Classes");
        String[] compassLore = {ChatColor.GREEN + "[Click To Open Class Menu]"};
        compassMeta.setLore(Arrays.asList(compassLore));

        compass.setItemMeta(compassMeta);
        player.getInventory().addItem(compass);
    }
    public static void GiveUnreadyConcrete(Player player){
        ItemStack unreadyConcrete = new ItemStack(Material.RED_CONCRETE,1);
        ItemMeta unreadyMeta = unreadyConcrete.getItemMeta();
        unreadyMeta.setDisplayName( ChatColor.RED + "Currently Not Ready");

        unreadyConcrete.setItemMeta(unreadyMeta);
        player.getInventory().addItem(unreadyConcrete);
    }

    public static void GiveReadyConcrete(Player player){
        ItemStack readyConcrete = new ItemStack(Material.GREEN_CONCRETE,1);
        ItemMeta readyMeta = readyConcrete.getItemMeta();
        readyMeta.setDisplayName(ChatColor.GREEN + "Currently Ready");
        readyConcrete.setItemMeta(readyMeta);
        player.getInventory().addItem(readyConcrete);
    }

    public static void GiveTalismanTotem(Player player){
        ItemStack TalsimanTotem = new ItemStack(Material.TOTEM_OF_UNDYING,1);
        ItemMeta TotemMeta = TalsimanTotem.getItemMeta();
        TotemMeta.setDisplayName(ChatColor.WHITE + "Talismen Selector");
        String[] TotemLore = {ChatColor.GREEN + "[Click To Open Talisman Menu]"};

        TotemMeta.setLore(Arrays.asList(TotemLore));
        TalsimanTotem.setItemMeta(TotemMeta);
        player.getInventory().addItem(TalsimanTotem);
    }
    public static void GiveMapSelection(Player player){
        ItemStack MapSelector = new ItemStack(Material.MAP,1);
        ItemMeta MapMeta = MapSelector.getItemMeta();
        MapMeta.setDisplayName(ChatColor.WHITE + "Map Selector");
        String[] MapLore = {ChatColor.GREEN + "[Click To Vote for a map]"};

        MapMeta.setLore(Arrays.asList(MapLore));
        MapSelector.setItemMeta(MapMeta);
        player.getInventory().addItem(MapSelector);
    }
    //UI Refreshes
    public static void refreshTalismanMenu(Player player){
        Inventory talismanInventory = Bukkit.createInventory(player, InventoryType.BARREL, ChatColor.BOLD + "Talisman Chooser");
        for(Talisman talisman : TheRoadPlugin.getInstance().talismans)
        {
            talismanInventory.addItem(talisman.makeIcon(player));
        }
        player.openInventory(talismanInventory);
    }
    public static void refreshMapInventory(Player player){
        Inventory mapInventory = Bukkit.createInventory(player, InventoryType.HOPPER, ChatColor.BOLD + "Map Selector");
        for(Arena arena : TheRoadPlugin.getInstance().gameManager.gameArenas)
        {
            mapInventory.addItem( arena.makeIcon(player));
        }
        player.openInventory(mapInventory);
    }

    public static void refreshClassInventory(Player player){
        Inventory classInventory = Bukkit.createInventory(player, 9, ChatColor.BOLD + "Class Chooser");
        for(GameClass gameClass : TheRoadPlugin.getInstance().gameClasses)
        {
            classInventory.addItem(gameClass.makeIcon(player));
        }
        player.openInventory(classInventory);
    }
}
