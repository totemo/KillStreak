package com.jackwilsdon.KillStreak;
 
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * KillStreakPlugin
 * Main plugin file
 * @author Jack Wilsdon
 *
 */
public class KillStreakPlugin extends JavaPlugin {
	
	public void onEnable()
	{
		getLogger().info(getDescription().getFullName()+" enabled!");
		saveDefaultConfig();
		
		/*
		 * Start metrics
		 */
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			getServer().getLogger().log(Level.WARNING, "Unable to send statistics :(");
		}
		
		/*
		 * Register listeners
		 */
		KillStreakManager.create(this);
		getCommand("killstreak").setExecutor(new KillStreakCommandExecutor());
		getServer().getPluginManager().registerEvents(new KillStreakEventListener(), this);
	}
	
	public void onDisable()
	{
		getLogger().info(getDescription().getFullName()+" disabled!");
	}
}