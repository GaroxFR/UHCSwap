package com.gartox.uhcswap;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gartox.uhcswap.events.UHCSwapListener;
import com.gartox.uhcswap.state.State;
import com.gartox.uhcswap.title.Title;
import com.gartox.uhcswap.utils.UHCSwapReset;

public class UHCSwap extends JavaPlugin {
	
	private List<Player>playersInGame;
	public UHCSwapGame swap = new UHCSwapGame(this);
	private State state;
	public Title title = new Title();
	
	public void onEnable() {
		
		playersInGame = new ArrayList<Player>();
		saveDefaultConfig();
		for(Player p : getServer().getOnlinePlayers())addPlayer(p);
		getServer().getPluginManager().registerEvents(new UHCSwapListener(this), this);
		setState(State.WAITING);
	}

	public UHCSwap getInstance() {
		return this;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("start")){
			UHCSwapAutoStart start = new UHCSwapAutoStart(this);
			 
			start.runTaskTimer(getInstance(), 20, 20);
			
			return true;
		}
		if(command.getName().equalsIgnoreCase("swap")){
			getServer().broadcastMessage("Swap ! ");
			swap.swap();
			return true;
		}
		if(command.getName().equalsIgnoreCase("reset")){
			
			UHCSwapReset.resetWorld(getInstance());
			return true;
		}
		if(command.getName().equalsIgnoreCase("lobby")){
			if(sender instanceof Player){
				((Player)sender).teleport(new Location(Bukkit.getWorld("Lobby"), 0, 128, 0));
			}
		}
		return false;
	}
	public List<Player> getPlayersInGame() {
		return playersInGame;
	}
	public void addPlayer(Player player){
		playersInGame.add(player);
	}
	public void removePlayer(Player player){
		playersInGame.remove(player);
	}

	public boolean isState(State state) {
		return this.state == state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
