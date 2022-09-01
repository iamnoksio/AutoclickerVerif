package fr.ctlserv.william.autoclick;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.minecraft.util.com.google.common.collect.Maps;

public class PlayerWrapper {
	public static Map<UUID, PlayerWrapper> players = Maps.newConcurrentMap();

	public int[] clicks = {0, 0, 0, 0, 0, 0};

    public int nombreAlertesAutoClick = 0;
    public int maxClicks = 0;
    public long lastBlockInteraction = 0;
    public long lastAlert = 0;
    
    public long connexion = 0L;
    public String pseudo = "";
	
	public PlayerWrapper(Player p){
		players.put(p.getUniqueId(), this);
		this.pseudo = p.getName();
		this.connexion = System.currentTimeMillis();
	}
	
	public String getName(){
		return this.pseudo;
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(this.pseudo);
	}
	
	public static PlayerWrapper getByPlayer(Player p){
		return players.get(p.getUniqueId());
	}
	
	public static PlayerWrapper getByString(String name){
		return (Bukkit.getPlayerExact(name) == null ? null : players.get(Bukkit.getPlayerExact(name).getUniqueId()));
	}
	
	public static void removePlayer(Player p){
		players.remove(p.getUniqueId());
	}
}
