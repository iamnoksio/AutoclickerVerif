package fr.ctlserv.william.autoclick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckRunnable extends BukkitRunnable {
	private int timeBetweenAlerts;
	private int maxCps;
	private String alertMessage;
	private String permission;
	
	public CheckRunnable(int secondes, int maxCps, String AlertMessage, String Perm){
		this.timeBetweenAlerts = secondes;
		this.maxCps = maxCps;
		this.alertMessage = AlertMessage;
		this.permission = Perm;
	}
	
	public void run() {
		for (PlayerWrapper wp : PlayerWrapper.players.values()){
			int ping = wp.getPlayer().getPing();
            double tps = Math.round(Bukkit.spigot().getTPS()[0] * 1e2)/1e2;
            int antiLag = (int)((20.0D - tps) * 2.0D);
            antiLag += ping / 50;
            if ((wp.clicks[0] >= maxCps + antiLag) && (wp.lastAlert + timeBetweenAlerts * 1000L < System.currentTimeMillis())) {
            	wp.lastAlert = System.currentTimeMillis();
	            for (Player toAlert : Bukkit.getOnlinePlayers()){
	            	if (toAlert.hasPermission(permission)){
	            		toAlert.sendMessage(alertMessage.replace("%username", wp.pseudo).replace("%cps", String.valueOf(wp.clicks)).replace("%ms", String.valueOf(ping))
	            				.replace("%tps", String.valueOf(tps))
	            				.replace("&", "§"));
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


