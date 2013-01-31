package com.jackwilsdon.KillStreak;
 
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * KillStreakPlugin
 * Main class - Manages enabling and disabling
 */
public class KillStreakPlugin extends JavaPlugin {
	
	/*
	 * onEnable()
	 * Called on plugin enable
	 */
	public void onEnable()
	{
		/*
		 * Output enable message
		 */
		getLogger().info(getDescription().getFullName()+" enabled!");
		
		/*
		 * Save default config
		 */
		saveDefaultConfig();
		
		/*
		 * Start metrics
		 */
		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			getServer().getLogger().log(Level.WARNING, "Unable to send statistics :(");
		}
		
		/*
		 * Configure the KillStreakManager
		 */
		KillStreakManager.create(this);
		
		/*
		 * Register command listener
		 */
		getCommand("killstreak").setExecutor(new KillStreakCommandExecutor());
		
		/*
		 * Register event listener
		 */
		getServer().getPluginManager().registerEvents(new KillStreakEventListener(this), this);
	}
	
	/*
	 * onDisable()
	 * Called on plugin disable
	 */
	public void onDisable()
	{
		/*
		 * Output disable message
		 */
		getLogger().info(getDescription().getFullName()+" disabled!");
	}
}