package com.jackwilsdon.KillStreak;

import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/*
 * KillStreakManager
 * Manages killstreaks of players
 */
public class KillStreakManager {

	/*
	 * Variables for plugin
	 */
	private static KillStreakPlugin plugin;
	private static FileConfiguration config;
	
	/*
	 * create()
	 * Set the plugin reference and configuration
	 */
	public static void create(KillStreakPlugin pl)
	{
		plugin = pl;
		config = plugin.getConfig();
	}
	
	/*
	 * resetStreak()
	 * Resets a player's killstreak (usually called on death)
	 */
	public static void resetStreak(String player)
	{
		config.set("PlayerStreaks."+player, 0);
	}
	
	/*
	 * add()
	 * Add a kill to a player
	 */
	public static void add(String player)
	{
		int streak = config.getInt("PlayerStreaks."+player) + 1;
		config.set("PlayerStreaks."+player, streak);
		plugin.saveConfig();
	}
	
	/*
	 * get()
	 * Get a player's killstreak
	 */
	public static int get(String player)
	{
		if (config.get("PlayerStreaks."+player) == null)
		{
			return 0;
		}
		return config.getInt("PlayerStreaks."+player);
	}

	/*
	 * getPotion()
	 * Get a potion for a player's killstreak
	 */
	public static Potion getPotion(String killer)
	{
		int streak = get(killer);
		if (config.get("KillStreak.streaks."+streak+".potion") == null) return null;
		String type = config.getString("KillStreak.streaks."+streak+".potion").toUpperCase(Locale.ENGLISH);
		int level = config.getInt("KillStreak.streaks."+streak+".level");
		PotionType pType = null;
		
		try {
			pType = PotionType.valueOf(type);
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "\""+type+"\" is not a valid potion type!");
			return null;
		}
		
		if (pType == null) return null;
		
		Potion potion = new Potion(pType, level);
		return potion;
	}

	/*
	 * getPrefix()
	 * Get the prefix from the configuration
	 */
	public static String getPrefix()
	{
		String message = config.getString("KillStreak.messages.message-tag")+" ";
		return parseText(message);
	}
	
	/*
	 * getKillColor()
	 * Get the kill color from the config
	 */
	public static ChatColor getKillColor()
	{
		String cc = config.getString("KillStreak.messages.killstreak-color").substring(1);
		ChatColor col = ChatColor.getByChar(cc);
		return col;
	}
	
	/*
	 * getUsernameColor()
	 * Get the username color from the config
	 */
	public static ChatColor getUsernameColor()
	{
		String cc = config.getString("KillStreak.messages.username-color").substring(1);
		ChatColor col = ChatColor.getByChar(cc);
		return col;
	}
	
	/*
	 * parseText()
	 * Add color to text
	 */
	public static String parseText(String text)
	{
		for (ChatColor color : ChatColor.values()) {
			String code = "&"+color.getChar();
			text = text.replaceAll(code, color.toString());
		}
		return text;
	}
	
	/*
	 * shouldBroadcastMessage()
	 * Check whether a message should be broadcast
	 */
	public static boolean shouldBroadcastMessage()
	{
		return config.getBoolean("KillStreak.messages.broadcast-on-powerup");
	}
	
	/*
	 * shouldTellPlayer()
	 * Check whether the player should be told on powerup
	 */
	public static boolean shouldTellPlayer()
	{
		return config.getBoolean("KillStreak.messages.tell-player-on-powerup");
	}

}
