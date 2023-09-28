package me.supdapillar.theroad.enums;

import me.supdapillar.theroad.Helpers.CustomHead;
import org.bukkit.inventory.ItemStack;

public enum Heads {
    Silverfish("ZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ");


    private ItemStack item;
    private String prefix ="eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    Heads(String tex){
        item = CustomHead.createCustomHead(prefix + tex);
    }

    public ItemStack getItemStack(){
        return item;
    }
}
