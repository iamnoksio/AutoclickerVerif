package fr.ctlserv.william.autoclick;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class VerifCommand implements CommandExecutor {
	
    public static Map<Player, PlayerWrapper> verifiers = new HashMap<Player, PlayerWrapper>();
    private String perm;
    
    public VerifCommand(String perm){
    	this.perm = perm;
    }
    
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (!(sender instanceof Player)){
			sender.sendMessage("§cOnly usable by Players.");
			return false;
		}
		if (!sender.hasPermission(perm)){
			sender.sendMessage("§cI'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
			return false;
		}
		if (args.length != 1){
			sender.sendMessage("§cInvalide command usage, use '/verif player'");
			return false;
    	}

	    String pseudo = args[0];
	    if (Bukkit.getPlayer(pseudo) == null){
	    	sender.sendMessage("§cJoueur non connecté.");
	    	return false;
	    }
	    Inventory i = Bukkit.createInventory(null, 54, "§cVerif > " + pseudo);
	    verifiers.put((Player) sender, PlayerWrapper.getByPlayer(Bukkit.getPlayer(pseudo)));
	    ((Player)sender).openInventory(i);
		return true;
	}
}
