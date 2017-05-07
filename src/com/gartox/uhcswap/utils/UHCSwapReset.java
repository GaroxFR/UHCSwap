package com.gartox.uhcswap.utils;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UHCSwapReset {
	public static void resetWorld(JavaPlugin plugin)
    {
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			p.kickPlayer("Server restarting");
		}
        if(Bukkit.getWorld("world") != null)
            Bukkit.unloadWorld("world", false);

        plugin.getLogger().info("Deleting World...");
        deleteWorld(new File(plugin.getDataFolder().getParentFile().getParentFile(), "world"), plugin);
        plugin.getLogger().info("Done");

        Bukkit.createWorld(new WorldCreator("world"));
    }

    private static void deleteWorld(File path, JavaPlugin plugin)
    {
        if(path.exists())
        {
            File files[] = path.listFiles();
            for(int i = 0; i < files.length; i++)
            {
                if(files[i].isDirectory())
                    deleteWorld(files[i], plugin);

                else if(!files[i].delete())
                    plugin.getLogger().warning(files[i].getPath() + " Has not been deleted !");
            }
        }

        return ;
    }
}
