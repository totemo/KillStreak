package com.jackwilsdon.killstreak;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Checks for an update asynchronously
 * @author Jack Wilsdon
 */
public class KillStreakUpdateChecker implements Runnable {
	private final String address = "https://raw.github.com/jackwilsdon/KillStreak/master/plugin.yml";
	private KillStreakPlugin plugin = null;
	
	/**
	 * Pass an instance of KillStreakPlugin to the updater
	 * @param plugin An instance of KillStreakPlugin
	 */
	public KillStreakUpdateChecker(KillStreakPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	/**
	 * Gets the plugin.yml as specified in address
	 * @return The plugin.yml's contents
	 * @throws Exception Unable to get file
	 */
	private String getPluginYML() throws Exception
	{
		URL url = new URL(this.address);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		StringBuilder sb = new StringBuilder();
		String input = null;
		
		while ((input = in.readLine()) != null)
		{
			sb.append(input+"\n");
		}
		
		in.close();
		
		return sb.toString();
	}

	@Override
	public void run()
	{
		try {
			String yaml = this.getPluginYML();
			YamlConfiguration config = new YamlConfiguration();
			config.loadFromString(yaml);
			float remoteVersion = Float.parseFloat(config.getString("version"));
			float localVersion = Float.parseFloat(this.plugin.getDescription().getVersion());
			
			if (remoteVersion > localVersion)
			{
				this.plugin.getLogger().log(Level.INFO, "An update is available! ("+remoteVersion+")");
				this.plugin.getConfig().set("KillStreak.update-available", true);
			} else {
				this.plugin.getLogger().log(Level.INFO, "Up to date! ("+localVersion+")");
				this.plugin.getConfig().set("KillStreak.update-available", false);
			}
		} catch (Exception e) {
			this.plugin.getLogger().log(Level.WARNING, "Unable to check for updates :(");
		}
	}
}
