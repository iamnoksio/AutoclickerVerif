package fr.ctlserv.william.autoclick.customevents;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class AutoclickAlertEvent extends PlayerEvent implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	private int CPS;
	private int MS;
	private double TPS;
	private boolean cancelled = false;

	public AutoclickAlertEvent(String name, int CPS, int MS, double TPS){
		super(Bukkit.getPlayerExact(name));
		this.CPS = CPS;
		this.MS = MS;
		this.TPS = TPS;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.cancelled = arg0;
	}
	
	public int getCPS(){
		return CPS;
	}
	
	public int getMS(){
		return MS;
	}
	
	public double getTPS(){
		return TPS;
	}
}
