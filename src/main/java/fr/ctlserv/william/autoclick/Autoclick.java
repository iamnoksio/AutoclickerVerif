package fr.ctlserv.william.autoclick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Autoclick extends JavaPlugin{
	public static Autoclick instance;
	private boolean hasPaper= false;

	public void onEnable(){
		System.out.println("[Autoclick] Starting to load.");
		instance = this;
		try {
			Bukkit.spigot().getTPS();
			hasPaper = true;
			System.out.println("[Autoclick] Paperspigot detected.");
		} catch (NoSuchMethodError e) {
			new TPS();
			System.out.println("[Autoclick] Paperspigot not detected.");
		}
		if (this.getConfig().get("SECONDS_BETWEEN_ALERTS") == null || !this.getConfig().isInt("SECONDS_BETWEEN_ALERTS")){
			this.getConfig().set("SECONDS_BETWEEN_ALERTS", 3);
		}
		if (this.getConfig().get("MAX_CPS") == null || !this.getConfig().isInt("MAX_CPS")){
			this.getConfig().set("MAX_CPS", 18);
		}
		if (this.getConfig().get("ALERT_MESSAGE") == null || !this.getConfig().isString("ALERT_MESSAGE")){
			this.getConfig().set("ALERT_MESSAGE", "&8[&4&lAntiCheat&8]&r &c %username &7 a fait %cps clics (MS: %ms [TPS: %tps])");
		}
		if (this.getConfig().get("ALERT_PERM") == null || !this.getConfig().isString("ALERT_PERM")){
			this.getConfig().set("ALERT_PERM", "autoclick.alert");
		}
		if (this.getConfig().get("COMMAND_PERM") == null || !this.getConfig().isString("COMMAND_PERM")){
			this.getConfig().set("COMMAND_PERM", "autoclick.verif");
		}
		this.getServer().getPluginManager().registerEvents(new AutoclickListener(), this);
		this.getCommand("verif").setExecutor(new VerifCommand(this.getConfig().getString("COMMAND_PERM")));
		this.saveConfig();
		new VerifRunnable().runTaskTimerAsynchronously(this, 0L, 1L);
		new CheckRunnable(this.getConfig().getInt("SECONDS_BETWEEN_ALERTS")
				, this.getConfig().getInt("MAX_CPS")
				, this.getConfig().getString("ALERT_MESSAGE")
				, this.getConfig().getString("ALERT_PERM")).runTaskTimerAsynchronously(this, 0L, 20L);
		for (Player p : Bukkit.getOnlinePlayers()){
			new PlayerWrapper(p);
		}
		System.out.println("[Autoclick] Finished loading.");
	}
	
	public void onDisable(){
		for (Player p : Bukkit.getOnlinePlayers()){
			PlayerWrapper.removePlayer(p);
		}
		System.out.println("[Autoclick] Unloaded.");
	}
	
	public boolean hasPaper(){
		return hasPaper;
	}
	
	public double getTps(){
		if (hasPaper){
			return Bukkit.spigot().getTPS()[0];
		}
		return TPS.tps + 1;
	}
}
