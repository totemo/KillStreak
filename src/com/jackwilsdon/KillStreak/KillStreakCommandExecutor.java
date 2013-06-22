package com.jackwilsdon.KillStreak;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * KillStreakCommandExecutor
 * Handles commands for KillStreak
 * @author Jack Wilsdon
 */
public class KillStreakCommandExecutor implements CommandExecutor {

	private String prefix = KillStreakManager.getPrefix();
	
	@Override
	public boolean onCommand(CommandSender cmdSender, Command cmd, String label, String[] arguments)
	{	
		if (cmd.getName().equalsIgnoreCase("killstreak") || cmd.getName().equalsIgnoreCase("ks"))
		{
			ChatColor kC = KillStreakManager.getKillColor();
			if (!(cmdSender instanceof Player))
			{
				if (arguments.length != 1)
				{
					cmdSender.sendMessage("Console usage: /"+cmd.getName()+" <username>");
				} else {
					int streak = KillStreakManager.get(arguments[0]);
					cmdSender.sendMessage(prefix+arguments[0]+" has a current kill streak of "+kC+streak);
				}
				return true;
			}
			int streak = KillStreakManager.get(cmdSender.getName());
			cmdSender.sendMessage(prefix+"Your current kill streak is "+kC+streak);
		}
		return true;
	}

	
	
}
