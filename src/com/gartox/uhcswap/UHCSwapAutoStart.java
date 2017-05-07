package com.gartox.uhcswap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gartox.uhcswap.state.State;
import com.gartox.uhcswap.title.Title;

import net.md_5.bungee.api.ChatColor;


public class UHCSwapAutoStart extends BukkitRunnable{

	private int timer = 15;
	static UHCSwap game;

	

	public UHCSwapAutoStart(UHCSwap uhcSwap) {
		game = uhcSwap;
	}

	private static void teleportPlayers() {
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			p.teleport(new Location(Bukkit.getWorld("world"), 0, 128, 0));
		}
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spreadplayers 0 0 250 1000 false @a");
		for(Player p : Bukkit.getServer().getOnlinePlayers()){
			p.sendMessage(p.getWorld().getName());
			Bukkit.getLogger().info(p.getWorld().getName());
		}
	}

	@Override
	public void run() {
		Title title = new Title();
		for(Player p : game.getPlayersInGame()){
			p.setLevel(timer);
			
		}
		if(timer == 15 || timer == 10 || timer <= 5){
			for(Player p : game.getPlayersInGame()){
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
				title.sendTile(p, "§cLancement du jeux", ChatColor.YELLOW + Integer.toString(timer), 200);
			}
			
		}
		if(timer == 0){
			
			
			teleportPlayers();
			
			for(Player p : game.getPlayersInGame()){
				p.getInventory().clear();
				p.setFoodLevel(20);
				p.setHealth(20);
				p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
				title.sendTile(p, " ", " ", 1);
				
				
				game.swap.runTaskTimer(game, 20L, 20L);
			}
			game.setState(State.NODAMAGE);
			cancel();
		}
		timer--;
		
	}
}