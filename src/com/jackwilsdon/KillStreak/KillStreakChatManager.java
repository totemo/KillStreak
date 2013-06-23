package com.jackwilsdon.killstreak;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.Potion;

/**
 * Manages all chat formatting
 * @author Jack Wilsdon
 */
public class KillStreakChatManager {
	private ConfigurationSection config = null;
	private KillStreakManager manager = null;
	
	/**
	 * Pass the ConfigurationSection to use
	 * @param config The ConfigurationSection to use
	 */
	public KillStreakChatManager(ConfigurationSection config, KillStreakManager manager)
	{
		this.config = config;
		this.manager = manager;
	}
	
	/**
	 * Returns whether to broadcast a powerup to chat
	 * @return Whether to broadcast a powerup to chat
	 */
	public boolean broadcastOnPowerup()
	{
		return this.config.getBoolean("broadcast-on-powerup");
	}

	/**
	 * Gets the prefix from the configuration
	 * @return The chat prefix from the configuration
	 */
	public String getPrefix()
	{
		return this.config.getString("message-tag");
	}
	
	/**
	 * Gets the chat color for killstreaks from the configuration
	 * @return The chat color defined in the configuration
	 */
	private ChatColor getKillStreakColor()
	{
		String colorString = this.config.getString("killstreak-color");
		String colorCode = colorString.substring(1);
		return ChatColor.getByChar(colorCode);
	}
	
	/**
	 * Gets the username color from the configuration
	 * @return The username color defined in the configuration
	 */
	private ChatColor getUsernameColor()
	{
		String colorString = this.config.getString("username-color");
		String colorCode = colorString.substring(1);
		return ChatColor.getByChar(colorCode);
	}
	
	/**
	 * Parses &? color codes into ChatColors
	 * @param message The message to parse
	 * @return The parsed message with colors
	 */
	public String parseColors(String message)
	{
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	/**
	 * Get the broadcast message for the user
	 * @param username The user to get the message for
	 * @return The message to broadcast (null if none available)
	 */
	public String getBroadcastMessage(String username)
	{
		int streak = this.manager.getKills(username);
		Potion potion = this.manager.getPotion(streak);
		
		if (potion == null)
		{
			return null;
		}
		
		String powerup = potion.getType().name();
		
		StringBuilder base = new StringBuilder();
		base.append(this.getPrefix());
		base.append(this.getUsernameColor()+username+"&f");
		base.append(" has a killstreak of ");
		base.append(this.getKillStreakColor()+Integer.toString(streak)+"&f");
		base.append(" and has been rewarded the powerup ");
		base.append("&e"+powerup+"&f!");
		
		return this.parseColors(base.toString());
	}
	
	/**
	 * Gets the streak message for the user
	 * @param username The user to get the message for
	 * @return The message to send to the user
	 */
	public String getMessage(String username)
	{
		int streak = this.manager.getKills(username);
		Potion potion = this.manager.getPotion(streak);
		
		StringBuilder base = new StringBuilder();
		base.append(this.getPrefix());
		base.append("You now have a killstreak of ");
		base.append(this.getKillStreakColor()+Integer.toString(streak)+"&f");
		
		if (potion != null)
		{
			base.append(" and have been rewarded the powerup ");
			base.append("&e"+potion.getType().name()+"&f!");
		} else {
			base.append("!");
		}
		
		return this.parseColors(base.toString());
	}
	
	/**
	 * Get the message to be sent on death
	 * @param username The user that died
	 * @return The message to be sent on death
	 */
	public String getDeathMessage(String username)
	{
		int streak = this.manager.getKills(username);
		
		StringBuilder base = new StringBuilder();
		base.append(this.getPrefix());
		base.append("Your killstreak was ");
		base.append(this.getKillStreakColor()+Integer.toString(streak));
		
		return this.parseColors(base.toString());
	}
	
	/**
	 * Get the message to be sent on streak query
	 * @param username The username to query
	 * @param self Whether the user is asking about someone else
	 * @return The message to be sent on query
	 */
	public String getStreak(String username, boolean self)
	{
		int streak = this.manager.getKills(username);
		
		StringBuilder base = new StringBuilder();
		base.append(this.getPrefix());
		if (self)
		{
			base.append("Your killstreak is ");
		} else {
			base.append(this.getUsernameColor()+username+" has a killstreak of ");
		}
		base.append(this.getKillStreakColor()+Integer.toString(streak));
		
		return this.parseColors(base.toString());
	}
	
	/**
	 * Get the message to be sent on streak query
	 * @param username The username to query
	 * @return The message to be sent on query
	 */
	public String getStreak(String username)
	{
		return this.getStreak(username, true);
	}
}
