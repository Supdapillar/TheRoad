package me.supdapillar.theroad.Helpers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

public class CustomHead {

    public static ItemStack createCustomHead(String url){

        //The Fancy head
        ItemStack customHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) customHead.getItemMeta();
        //eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv ZGE5MWRhYjgzOTFhZjVmZGE1NGFjZDJjMGIxOGZiZDgxOWI4NjVlMWE4ZjFkNjIzODEzZmE3NjFlOTI0NTQwIn19fQ==
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "jimmy");
        gameProfile.getProperties().put("textures", new Property("textures", url));
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, gameProfile);

        }
        catch (NoSuchFieldException|IllegalAccessException e)
        {
            e.printStackTrace();
        }
        customHead.setItemMeta(headMeta);
        return customHead;
    }
}
