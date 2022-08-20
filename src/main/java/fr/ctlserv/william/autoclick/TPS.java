package fr.ctlserv.william.autoclick;

import org.bukkit.Bukkit;

public class TPS implements Runnable
{
	  long sec;
	  long currentSec;
	  public int ticks;
	  int delay;
	  public static double tps = 20.0D;
	  int index = -1;
	  
	  public TPS()
	  {
	    Bukkit.getScheduler().runTaskTimer(Autoclick.instance, this, 0L, 1L);
	  }
	  
	  public void run()
	  {
	    this.sec = (System.currentTimeMillis() / 1000L);
	    if (this.currentSec == this.sec)
	    {
	      this.ticks += 1;
	    }
	    else
	    {
	      this.currentSec = this.sec;
	      tps = tps == 0.0D ? this.ticks : (tps + this.ticks) / 2.0D;
	      this.ticks = 0;
	    }
	  }
	}
