package fr.ctlserv.william.autoclick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.ctlserv.william.autoclick.customevents.AutoclickAlertEvent;


public class CheckRunnable extends BukkitRunnable {
	private int timeBetweenAlerts;
	private int maxCps;
	private String AlertMessage;
	private String Perm;
	
	public CheckRunnable(int secondes, int maxCps, String AlertMessage, String Perm){
		this.timeBetweenAlerts = secondes;
		this.maxCps = maxCps;
		this.AlertMessage = AlertMessage;
		this.Perm = Perm;
	}
	
	public void run() {
		for (PlayerWrapper wp : PlayerWrapper.players.values()){
			int ping = wp.getPlayer().getPing();
            double tps = Math.round(Autoclick.instance.getTps()*1e2)/1e2 ;
            int AntiLag = (int)((20.0D - tps) * 2.0D);
            AntiLag += ping / 50;
            if ((wp.clicks[0] >= maxCps + AntiLag) && (wp.lastAlert + timeBetweenAlerts * 1000L < System.currentTimeMillis()))
            {
            	AutoclickAlertEvent event = new AutoclickAlertEvent(wp.pseudo, wp.clicks[0], ping , tps );
            	Bukkit.getServer().getPluginManager().callEvent(event);
            	wp.lastAlert = System.currentTimeMillis();
            	if (!event.isCancelled()){
            	for (Player toAlert : Bukkit.getOnlinePlayers()){
            		if (toAlert.hasPermission(Perm)){
            			toAlert.sendMessage(AlertMessage.replace("%username", wp.pseudo)
            		  .replace("%cps", String.valueOf(wp.clicks))
            		  .replace("%ms", String.valueOf(ping))
            		  .replace("%tps", String.valueOf(tps))
            		  .replace("&", "§"));
            		}
            	}
                wp.nombreAlertesAutoClick += 1; 
            }
            }
            wp.clicks[5] = wp.clicks[4];
            wp.clicks[4] = wp.clicks[3];
            wp.clicks[3] = wp.clicks[2];
            wp.clicks[2] = wp.clicks[1];
            wp.clicks[1] = wp.clicks[0];
            wp.clicks[0] = 0;
         }
	}
}


