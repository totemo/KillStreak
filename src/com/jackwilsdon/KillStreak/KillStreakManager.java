package com.jackwilsdon.KillStreak;

import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 * KillStreakManager
 * Manages the killstreaks of players
 * @author Jack Wilsdon
 */
public class KillStreakManager {
	private static KillStreakPlugin plugin;
	
	/**
	 * Set the plugin reference to use
	 * @param pl The plugin reference to use
	 */
	public static void create(KillStreakPlugin pl)
	{
		plugin = pl;
	}
	
	/**
	 * Reset a player's killstreak
	 * @param player The player to reset
	 */
	public static void resetStreak(String player)
	{
		plugin.getConfig().set("PlayerStreaks."+player, 0);
	}
	
	/**
	 * Add a kill to a player
	 * @param player The player to add the kill to
	 */
	public static void add(String player)
	{
		int streak = plugin.getConfig().getInt("PlayerStreaks."+player) + 1;
		plugin.getConfig().set("PlayerStreaks."+player, streak);
		plugin.saveConfig();
	}
	
	/**
	 * Get the killstreak of a player
	 * @param player The player to get the killstreak from
	 * @return The player's killstreak
	 */
	public static int get(String player)
	{
		if (plugin.getConfig().get("PlayerStreaks."+player) == null)
		{
			return 0;
		}
		return plugin.getConfig().getInt("PlayerStreaks."+player);
	}

	/**
	 * Get the potion for the killstreak of a player
	 * @param killer The player to get the potion for
	 * @return The potion for the killstreak of the player (null if no potion is available)
	 */
	public static Potion getPotion(String killer)
	{
		int streak = get(killer);
		if (plugin.getConfig().get("KillStreak.streaks."+streak+".potion") == null) return null;
		String type = plugin.getConfig().getString("KillStreak.streaks."+streak+".potion").toUpperCase(Locale.ENGLISH);
		int level = plugin.getConfig().getInt("KillStreak.streaks."+streak+".level");
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

	/**
	 * Get the plugin's chat prefix
	 * @return The plugin's chat prefix
	 */
	public static String getPrefix()
	{
		String message = plugin.getConfig().getString("KillStreak.messages.message-tag")+" ";
		return parseText(message);
	}
	
	/**
	 * Get the color to show kills in chat
	 * @return The color to show kills in chat
	 */
	public static ChatColor getKillColor()
	{
		String cc = plugin.getConfig().getString("KillStreak.messages.killstreak-color").substring(1);
		ChatColor col = ChatColor.getByChar(cc);
		return col;
	}
	
	/**
	 * Get the username color to show in chat
	 * @return The username color to show in chat
	 */
	public static ChatColor getUsernameColor()
	{
		String cc = plugin.getConfig().getString("KillStreak.messages.username-color").substring(1);
		ChatColor col = ChatColor.getByChar(cc);
		return col;
	}
	
	/**
	 * Parse text for color codes
	 * @param text The text to parse
	 * @return Text with color codes added
	 */
	public static String parseText(String text)
	{
		for (ChatColor color : ChatColor.values()) {
			String code = "&"+color.getChar();
			text = text.replaceAll(code, color.toString());
		}
		return text;
	}
	
	/**
	 * Check whether powerup messages should be broadcast
	 * @return Whether powerup messages should be broadcast
	 */
	public static boolean shouldBroadcastMessage()
	{
		return plugin.getConfig().getBoolean("KillStreak.messages.broadcast-on-powerup");
	}
	
	/**
	 * Check whether the player should be told on powerup
	 * @return Whether the player should be notified on powerup
	 */
	public static boolean shouldTellPlayer()
	{
		return plugin.getConfig().getBoolean("KillStreak.messages.tell-player-on-powerup");
	}
	
	/**
	 * Check whether the killstreak should be reset on disconnect
	 * @return Whether the killstreak should be reset on disconnect
	 */
	public static boolean shouldResetOnDisconnect()
	{
		return plugin.getConfig().getBoolean("KillStreak.reset-on-disconnect");
	}

}
