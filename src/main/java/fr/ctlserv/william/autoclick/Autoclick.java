package fr.ctlserv.william.autoclick;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Autoclick extends JavaPlugin{
	public static Autoclick instance;

	public void onEnable(){
		System.out.println("[Autoclick] Starting to load.");
		instance = this;
		this.checkConfig(this.getConfig());
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
	
	private void checkConfig(FileConfiguration config) {
		if (config.get("SECONDS_BETWEEN_ALERTS") == null || !config.isInt("SECONDS_BETWEEN_ALERTS")){
			config.set("SECONDS_BETWEEN_ALERTS", 3);
		}
		if (config.get("MAX_CPS") == null || !config.isInt("MAX_CPS")){
			config.set("MAX_CPS", 18);
		}
		if (config.get("ALERT_MESSAGE") == null || !config.isString("ALERT_MESSAGE")){
			config.set("ALERT_MESSAGE", "&8[&4&lAntiCheat&8]&r &c %username &7 a fait %cps clics (MS: %ms [TPS: %tps])");
		}
		if (config.get("ALERT_PERM") == null || !config.isString("ALERT_PERM")){
			config.set("ALERT_PERM", "autoclick.alert");
		}
		if (config.get("COMMAND_PERM") == null || !config.isString("COMMAND_PERM")){
			config.set("COMMAND_PERM", "autoclick.verif");
		}
	}
	
	public void onDisable(){
		for (Player p : Bukkit.getOnlinePlayers()){
			PlayerWrapper.removePlayer(p);
		}
		System.out.println("[Autoclick] Unloaded.");
	}
}
