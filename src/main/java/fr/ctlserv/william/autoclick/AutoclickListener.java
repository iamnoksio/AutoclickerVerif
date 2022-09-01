package fr.ctlserv.william.autoclick;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoclickListener implements Listener{
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
	    if (e.getAction() == Action.LEFT_CLICK_AIR) {
	      Player player = e.getPlayer();
	      PlayerWrapper wp = PlayerWrapper.getByPlayer(player);
	      // TODO: FIX BUG WHERE PLAYER CAN BE DETECTED AUTOCLICKING BY SPAMMING CLICK BLOCK
	      if ((player.getTargetBlock(null, 100).getLocation().distance(player.getLocation()) < 6.0D) && (wp.lastBlockInteraction > System.currentTimeMillis()) && (wp.clicks[0] >= 10)) {
	    	  e.setCancelled(true);
	    	  e.setUseInteractedBlock(Result.DENY); // IDK if it works but it seems to be the only way
	    	  e.setUseItemInHand(Result.DENY);
	    	  return;
	      }
	      if (wp.clicks[0] > wp.maxClicks) {
	    	  wp.maxClicks = wp.clicks[0];
	      }
	      wp.clicks[0] += 1;
	    } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
	      Player player = e.getPlayer();
	      PlayerWrapper wp = PlayerWrapper.getByPlayer(player);
	      wp.lastBlockInteraction = (System.currentTimeMillis() + 5000L);
	    }
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e){
		if (e.getClickedInventory() != null && e.getClickedInventory().getTitle().startsWith("§cVerif >")){
			e.setCancelled(true);
		}
		return;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		new PlayerWrapper(e.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		PlayerWrapper.removePlayer(e.getPlayer());
	}
}
