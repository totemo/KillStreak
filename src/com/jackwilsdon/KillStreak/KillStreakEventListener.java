package com.jackwilsdon.KillStreak;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.Potion;

/*
 * KillStreakEventListener
 * Called on player death
 */
public class KillStreakEventListener implements Listener {
	
	/*
	 * Prefix for messages
	 */
	private String prefix = ChatColor.WHITE+"["+ChatColor.GREEN+"KillStreak"+ChatColor.WHITE+"] ";
	
	/*
	 * Variables for plugin
	 */
	private KillStreakPlugin plugin;
	private FileConfiguration config;
	
	/*
	 * KillStreakEventListener()
	 * Constructor
	 */
	KillStreakEventListener(KillStreakPlugin pl)
	{
		plugin = pl;
		config = plugin.getConfig();
	}
	
	/*
	 * onQuit()
	 * Called when a player leaves/quits
	 */
	public void onQuit(PlayerQuitEvent pQuit)
	{
		if (config.getBoolean("KillStreak.reset-on-disconnect"))
		{
			String username = pQuit.getPlayer().getName();
			KillStreakManager.resetStreak(username);
		}
	}
	
	/*
	 * onDeath()
	 * Called when a player dies
	 */
	@EventHandler
	public void onDeath(PlayerDeathEvent pDeath)
	{
		String victim = pDeath.getEntity().getName();
		
		pDeath.getEntity().sendMessage(prefix+"Your kill streak was "+KillStreakManager.get(victim));
		
		KillStreakManager.resetStreak(victim);
		
		if (pDeath.getEntity().getKiller() != null)
		{
			
			String killer = pDeath.getEntity().getKiller().getName();
			
			KillStreakManager.add(killer);
			
			pDeath.getEntity().getKiller().sendMessage(prefix+"Your kill streak is now "+KillStreakManager.get(killer));
			
			if (KillStreakManager.getPotion(killer) != null)
			{
				Potion potion = KillStreakManager.getPotion(killer);
				pDeath.getEntity().getKiller().removePotionEffect(potion.getType().getEffectType());
				potion.apply(pDeath.getEntity().getKiller());
			}
		}
	}
}
