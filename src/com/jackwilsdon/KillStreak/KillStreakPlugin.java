package com.jackwilsdon.KillStreak;
 
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