package com.gartox.uhcswap.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gartox.uhcswap.UHCSwap;
import com.gartox.uhcswap.state.State;

@SuppressWarnings("deprecation")
public class UHCSwapListener implements Listener {

	private UHCSwap game;

	public UHCSwapListener(UHCSwap uhcSwap) {this.game = uhcSwap;}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		p.teleport(new Location(Bukkit.getWorld("Lobby"), 0, 128, 0));
		game.title.sendActionBar(p, "Bienvenue !");
		game.title.sendTile(p, "§cBienvenue", "§aDans l'UHC", 100);
		if(game.isState(State.WAITING ) || game.isState(State.STARTING)){
			game.addPlayer(p);
		}
	}
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		
		game.removePlayer(p);
	}
	@EventHandler
	public void onPlayerTryConnect(PlayerPreLoginEvent e){
		if(game.isState(State.RESTARTING)){
			e.setResult(PlayerPreLoginEvent.Result.KICK_FULL);
			e.disallow(PlayerPreLoginEvent.Result.KICK_FULL, "Server is restarting...");
		}
	}
	@EventHandler
	public void onDamageTake(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			if(game.isState(State.NODAMAGE)){
				 e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerMakeDamageToPlayer(EntityDamageByEntityEvent e){
		if(!(game.isState(State.PVP))){
			if((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player)){
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if(game.isState(State.WAITING) || game.isState(State.STARTING)){
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e){
		if(game.isState(State.WAITING) || game.isState(State.STARTING)){
			e.setCancelled(true);
		}
	}
	
}
