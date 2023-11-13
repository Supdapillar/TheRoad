package me.supdapillar.theroad.enums;

import me.supdapillar.theroad.Helpers.CustomHead;
import me.supdapillar.theroad.gameClasses.Mage;
import org.bukkit.inventory.ItemStack;

public enum Heads {
    Wolf("NjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0="),
    Cloud("MWE5NTcwNDE1Zjk0YjM5NGZmNTFhOTI1OWYxZmNmOWRiMzA2Njc3NDM4YmRjOGJhYzM1ZGNkNTkxYWEwMmVkZSJ9fX0="),
    Mage("ZWRkOGZlY2RiYTE5ODViYWFjNGRmOWMzZjEzZTQzMjE2NTNmNmNhMWU2M2I3YTBmZjc4ZGNkNzNlM2YwZDVjOCJ9fX0="),
    Lantern("MjdkNTQ0MTdiOGY1MDQxZTQ4OGVhNTZmZTViY2NhNmMzYmU3ZGUyZGQ4NDQ4NzgwZDllMDVhZmNiYTc0OWM0NSJ9fX0="),
    SeaLantern("M2IxNTU3YjcwMGRmNTI0ZTA3ODYwYzc0NjNlYWNhOTQ1MTViYWI3ZTRiMWQzM2UzOWJkMjg5NmFkY2IwZWQ5MCJ9fX0="),
    ChessHead("NWNjNzZjNzI3OWE3ZGU4N2ZiMzQwMDYzMGU0OWZhNTdiOTdkODk5MzBkM2IzYzcxNGY3MGU1YjM4NTA5MDNjZCJ9fX0="),
    ChessPawn("NGI3YWE0OTY5YWUzZGY4MzM0OTU4YzIxMjA1MGJlMzQzODU0MzAzNzY3YzI5ZDFhMWQxMWM0YWM3YjdiNTNkNCJ9fX0="),
    ChessRook("MjgxNzc1NWUwYTEzZDU0NjBkMTgzMTMwYTVmY2RkMmU1YzM4ZTcyOTJiY2Q2ZWY1ZmRjOTkwNTAxODEyYWMzOSJ9fX0="),
    Lava("MDQ5OGY4MjA5NjhiNWRmMjcyYmEzMWE2ZDhlMmI0MjA2Yjk1OTY3ZWI4MjhjOGM1OWY0YmYxMmUyMzZlZiJ9fX0="),

    //Rainbow Text
    RainbowZero("M2I0NjhmNTU5OGFmN2M2NmYxYzFkNzM0NjVlYzMxZGNkNjdhODhkOTAwNTFiODk3ZGFkODRiYjM2MGIzMzc5OSJ9fX0=");

    private ItemStack item;
    private String prefix ="eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    Heads(String tex){
        item = CustomHead.createCustomHead(prefix + tex);
    }

    public ItemStack getItemStack(){
        return item;
    }
}
