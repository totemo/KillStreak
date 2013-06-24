package com.jackwilsdon.killstreak;
 
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin file
 * @author Jack Wilsdon
 */
public class KillStreakPlugin extends JavaPlugin {
	
	public void onEnable()
	{
		this.getLogger().info(this.getDescription().getFullName()+" enabled!");
		this.saveDefaultConfig();
		
		if (this.getConfig().getBoolean("KillStreak.updater-enabled"))
		{
			KillStreakUpdateChecker update = new KillStreakUpdateChecker(this);
			this.getServer().getScheduler().runTaskAsynchronously(this, update);
		}
		
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			this.getServer().getLogger().log(Level.WARNING, "Unable to send statistics :(");
		}
		
		KillStreakManager manager = new KillStreakManager(this);
		KillStreakEventListener ev = new KillStreakEventListener(manager);
		this.getServer().getPluginManager().registerEvents(ev, this);
		
		KillStreakCommandExecutor executor = new KillStreakCommandExecutor(manager);
		this.getCommand("killstreak").setExecutor(executor);
		
		
	}
	
	public void onDisable()
	{
		this.getLogger().info(this.getDescription().getFullName()+" disabled!");
	}
}