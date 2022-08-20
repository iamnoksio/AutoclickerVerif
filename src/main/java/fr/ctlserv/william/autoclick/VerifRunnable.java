package fr.ctlserv.william.autoclick;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.Lists;

public class VerifRunnable extends BukkitRunnable {

	@Override
	public void run() {
		for(Player verifier : VerifCommand.verifiers.keySet()){
			if (verifier.getOpenInventory().getTopInventory() != null && verifier.getOpenInventory().getTopInventory().getTitle().startsWith("§cVerif >")){
				String o = verifier.getOpenInventory().getTopInventory().getName().split("> ")[1];
	            if (Bukkit.getPlayer(o) != null)
	            {
	              Player player = Bukkit.getPlayer(o);
	              verifier.getOpenInventory().getTopInventory().setItem(0, player.getInventory().getHelmet());
	              verifier.getOpenInventory().getTopInventory().setItem(1, player.getInventory().getChestplate());
	              verifier.getOpenInventory().getTopInventory().setItem(2, player.getInventory().getLeggings());
	              verifier.getOpenInventory().getTopInventory().setItem(3, player.getInventory().getBoots());
	              PlayerWrapper wp = VerifCommand.verifiers.get(verifier);
	              
	              ItemStack it = new ItemStack(Material.DIAMOND_BLOCK, Math.min(wp.maxClicks, 64));
	              ItemMeta im = it.getItemMeta();
	              im.setDisplayName(" ");
	              im.setLore(Arrays.asList(new String[] { "§eMaximum de", "§eclicks: §f" + wp.maxClicks }));
	              it.setItemMeta(im);
	              
	              ItemStack it1 = new ItemStack(Material.GOLD_BLOCK, Math.min(wp.clicks[0], 64));
	              ItemMeta im1 = it1.getItemMeta();
	              im1.setDisplayName(" ");
	              im1.setDisplayName("§9Clics actuellement: " + wp.clicks);
	              im.setLore(Arrays.asList(new String[] { "§9Clics", "§9actuellement: §f" + wp.maxClicks }));
	              it1.setItemMeta(im1);
	              
	              int ping = wp.getPlayer().getPing();
	              ItemStack it2 = new ItemStack(Material.IRON_BLOCK, Math.min(ping, 64));
	              ItemMeta im2 = it2.getItemMeta();
	              im2.setDisplayName("§6Ping: §f" + ping);
	              
		    	  Long l = wp.Connexion;
		    	  Long l2 = System.currentTimeMillis();
		    	  long diffMs = l2 - l;       
		    	  long diffHours = diffMs / (60 * 60 * 1000);
		    	  if (diffHours != 0){
			    	  long diffMins = (diffMs / (60 * 60 * 1000)) % 60;
		    		  im2.setLore(Arrays.asList(new String[] {"§6Connecté depuis "+ diffHours + " heures et " + diffMins + " minutes." }));
		    	  }else{
			    	  long diffSec = diffMs / 1000;
			    	  long min = diffSec / 60;
		    		  im2.setLore(Arrays.asList(new String[] {"§6Connecté depuis " + min + " minutes."}));
		    	  }
		    	  it2.setItemMeta(im2);
	              int nombrealert = 1;
	              if (wp.nombreAlertesAutoClick > 0){
	            	  nombrealert = wp.nombreAlertesAutoClick;
	              }
	              
	              ItemStack it3 = new ItemStack(Material.REDSTONE_BLOCK, Math.min(nombrealert, 64));
	              ItemMeta im3 = it3.getItemMeta();
	              im3.setDisplayName("§cNombre d'alertes: " + wp.nombreAlertesAutoClick);
	              it3.setItemMeta(im3);
	              
	              ItemStack it4 = new ItemStack(Material.GOLD_BLOCK, Math.min(wp.clicks[1], 64));
		          ItemMeta im4 = it4.getItemMeta();
		          im4.setDisplayName("§cClicks les 5 dernieres secondes:");
		          final List<String> lore = Lists.newArrayList();
		          for (int i = 1; i < wp.clicks.length; i++) { // TODO: 1 or 0
		        	  lore.add("- " + wp.clicks[i]);
		          }
		          im4.setLore(lore);
		          it4.setItemMeta(im4);
		            
		          verifier.getOpenInventory().getTopInventory().setItem(5, it3);
		          verifier.getOpenInventory().getTopInventory().setItem(6, it2);
		          verifier.getOpenInventory().getTopInventory().setItem(7, it1);
		          verifier.getOpenInventory().getTopInventory().setItem(8, it);
		          verifier.getOpenInventory().getTopInventory().setItem(4, it4);

	              int slot = 8;
	              for (ItemStack itemStack : player.getInventory().getContents()) {
	            	  slot++;
	            	  verifier.getOpenInventory().getTopInventory().setItem(slot, itemStack);
	              }
	              
	              ItemStack merite = new ItemStack(Material.DIAMOND, 1);
	              ItemMeta im5 = merite.getItemMeta();
	              im5.setDisplayName("§ePlugin crée par Williambraecky, Fondateur du CTLServ."); // KEEP THIS HERE
	              merite.setItemMeta(im5);
		            //verifier.getOpenInventory().getTopInventory().setItem(45, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(46, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(47, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(48, merite);
	              verifier.getOpenInventory().getTopInventory().setItem(49, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(50, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(51, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(52, merite);
		            //verifier.getOpenInventory().getTopInventory().setItem(53, merite);
	              
	            }
			}else{
				VerifCommand.verifiers.remove(verifier);
			}
		}
		
	}

}
