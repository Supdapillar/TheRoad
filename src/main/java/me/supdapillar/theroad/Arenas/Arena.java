package me.supdapillar.theroad.Arenas;

import org.bukkit.Location;

public class Arena {

    public String arenaName;
    public Location spawnLocation;
    public int finalyRound;
    public Arena(String arena_name,Location spawn_Location, int fRound){
        arenaName = arena_name;
        spawnLocation = spawn_Location;
        finalyRound = fRound;//
    }
}
