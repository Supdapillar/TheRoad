package me.supdapillar.theroad.enums;

import me.supdapillar.theroad.Helpers.CustomHead;
import me.supdapillar.theroad.gameClasses.Mage;
import org.bukkit.inventory.ItemStack;

public enum Heads {
    Wolf("NjlkMWQzMTEzZWM0M2FjMjk2MWRkNTlmMjgxNzVmYjQ3MTg4NzNjNmM0NDhkZmNhODcyMjMxN2Q2NyJ9fX0="),
    Cloud("MWE5NTcwNDE1Zjk0YjM5NGZmNTFhOTI1OWYxZmNmOWRiMzA2Njc3NDM4YmRjOGJhYzM1ZGNkNTkxYWEwMmVkZSJ9fX0="),
    Mage("ZWRkOGZlY2RiYTE5ODViYWFjNGRmOWMzZjEzZTQzMjE2NTNmNmNhMWU2M2I3YTBmZjc4ZGNkNzNlM2YwZDVjOCJ9fX0=");
    private ItemStack item;
    private String prefix ="eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    Heads(String tex){
        item = CustomHead.createCustomHead(prefix + tex);
    }

    public ItemStack getItemStack(){
        return item;
    }
}
