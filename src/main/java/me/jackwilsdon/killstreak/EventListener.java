package me.jackwilsdon.killstreak;

import java.util.List;


import me.jackwilsdon.killstreak.events.StreakChangeEvent;
import me.jackwilsdon.killstreak.KillStreakPlugin;
import me.jackwilsdon.killstreak.StreakHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;

public final class EventListener implements Listener {
    private StreakHandler handler;

    public EventListener(KillStreakPlugin plugin, FileConfiguration configuration)
    {
        this.handler = new StreakHandler(plugin, configuration);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent pDeath) {
        Player victim = pDeath.getEntity();
        Player killer = victim.getKiller();

        int victimStreak = this.handler.getStreak(victim.getName());

        if (victimStreak != 0) {
            StreakChangeEvent event = new StreakChangeEvent(victim, 0);

            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                this.handler.setStreak(victim.getName(), 0);
            }
        }

        if (killer instanceof Player)
        {
            int killerStreak = this.handler.getStreak(killer.getName()) + 1;

            StreakChangeEvent event = new StreakChangeEvent(killer, killerStreak);

            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                this.handler.setStreak(killer.getName(), killerStreak);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent eDeath) {
        if (!this.handler.getConfig().getBoolean("count-mobs.enabled")) {
            return;
        }

        List<?> mobs = this.handler.getConfig().getList("count-mobs.mobs");
        Player killer = eDeath.getEntity().getKiller();
        
        if (killer == null) {
            return;
        }

        if (mobs.contains(eDeath.getEntityType().name())) {
            int killerStreak = this.handler.getStreak(killer.getName()) + 1;
            
            StreakChangeEvent event = new StreakChangeEvent(killer, killerStreak);

            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                this.handler.setStreak(killer.getName(), killerStreak);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent pQuit) {
        if (!this.handler.getConfig().getBoolean("reset-on-disconnect")) {
            return;
        }

        Player player = pQuit.getPlayer();

        StreakChangeEvent event = new StreakChangeEvent(player, 0);

        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            this.handler.setStreak(player.getName(), 0);
        }
    }
   

    @EventHandler(priority = EventPriority.NORMAL)
    public void onStreakChange(StreakChangeEvent sChange) {
        Player player = sChange.getPlayer();
        int streak = sChange.getStreak();

        if (!player.isOnline()) {
            return;
        }

        String prefix = this.handler.getConfig().getString("messages.message-tag");
        String pColor = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', prefix));

        String kColor = this.handler.getConfig().getString("messages.killstreak-color");
        String uColor = this.handler.getConfig().getString("messages.username-color");

        String message = String.format("%sYour kill streak is now %s%d%s!", prefix, kColor, streak, pColor);
        String formatted = ChatColor.translateAlternateColorCodes('&', message);

        player.sendMessage(formatted);

        /* ------------------------ */

        PotionEffect powerup = this.handler.getPotion(streak);

        if (powerup == null) {
            return;
        }

        String playerText = uColor + player.getName() + pColor;
        String streakText = kColor + streak + pColor;
        String powerupText = uColor + powerup.getType().getName() + pColor;
        String broadcast = String.format("%s%s has a kill streak of %s and has been rewarded the powerup %s!", prefix, playerText, streakText, powerupText);
        String formattedBroadcast = ChatColor.translateAlternateColorCodes('&', broadcast);

        if (this.handler.getConfig().getBoolean("broadcast-on-powerup")) {
            Bukkit.getServer().broadcastMessage(formattedBroadcast);
        } else {
            player.sendMessage(formattedBroadcast);
        }

        powerup.apply(player);
    }
}
