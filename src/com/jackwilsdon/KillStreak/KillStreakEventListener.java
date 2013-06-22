package com.jackwilsdon.KillStreak;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.Potion;

/**
 * KillStreakEventListener
 * Handles events for KillStreak
 * @author Jack Wilsdon
 *
 */
public class KillStreakEventListener implements Listener {
	
	private String prefix = KillStreakManager.getPrefix();

	@EventHandler
	public void onQuit(PlayerQuitEvent pQuit)
	{
		if (KillStreakManager.shouldResetOnDisconnect())
		{
			String username = pQuit.getPlayer().getName();
			KillStreakManager.resetStreak(username);
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent pDeath)
	{
		String victim = pDeath.getEntity().getName();
		
		ChatColor kC = KillStreakManager.getKillColor();
		
		pDeath.getEntity().sendMessage(prefix+"Your kill streak was "+kC+KillStreakManager.get(victim));
		
		KillStreakManager.resetStreak(victim);
		
		if (pDeath.getEntity().getKiller() != null)
		{
			
			String killer = pDeath.getEntity().getKiller().getName();
			
			KillStreakManager.add(killer);
			
			pDeath.getEntity().getKiller().sendMessage(prefix+"Your kill streak is now "+kC+KillStreakManager.get(killer));
			
			if (KillStreakManager.getPotion(killer) != null)
			{
				Potion potion = KillStreakManager.getPotion(killer);
				pDeath.getEntity().getKiller().removePotionEffect(potion.getType().getEffectType());
				potion.apply(pDeath.getEntity().getKiller());
				
				boolean broadcast = KillStreakManager.shouldBroadcastMessage();
				boolean message = KillStreakManager.shouldTellPlayer();
				int kills = KillStreakManager.get(killer);
				ChatColor uC = KillStreakManager.getUsernameColor();
				
				
				if (broadcast)
				{
					Bukkit.getServer().broadcastMessage(prefix+uC+killer+ChatColor.WHITE+" has a kill streak of "+kC+kills+ChatColor.WHITE+", and was rewarded the powerup "+ChatColor.YELLOW+potion.getType().name()+"!");
				} else if (message) {
					pDeath.getEntity().getKiller().sendMessage(prefix+"You now have a kill streak of "+kC+kills+ChatColor.WHITE+"!");
				}
			}
		}
	}
}
