package com.gartox.uhcswap;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gartox.uhcswap.state.State;

public class UHCSwapGame extends BukkitRunnable{
	public static int time = 0;
	private UHCSwap game;
	private Random rand = new Random();
	private int nextSwap = 0;
	
	public UHCSwapGame(UHCSwap uhcSwap){
		this.game = uhcSwap; 
	}
	
	@Override
	public void run() {
		
		if(time == game.getConfig().getInt("game.nodamage_time"))
			game.setState(State.NOPVP);
		
		if(time == game.getConfig().getInt("game.pvp_start")){
			game.setState(State.PVP);
			swap();
		}
		if(time == nextSwap){
			swap();
		}
		
		time++;
	}
	public void swap(){
		nextSwap = time+((rand.nextInt(300))+300);
		List<Player> players = game.getPlayersInGame();
		while (players.size() > 1){
			int ip1 = 0;
			int ip2 = 0;
			while(ip1 == ip2){
				ip1 = rand.nextInt(players.size());
				ip2 = rand.nextInt(players.size());
			}
			players.remove(ip1);
			players.remove(ip2);
			int x = rand.nextInt(500);
			int z = rand.nextInt(500);
			
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spreadplayers "+ x +" "+ z + " 5 10 false " + players.get(ip1).getName() + " " + players.get(ip2));
				players.get(ip1).sendMessage("spreadplayers "+ x +" "+ z+ " 5 10 false " + players.get(ip1).getName() + " " + players.get(ip2));
		}
	}

}
