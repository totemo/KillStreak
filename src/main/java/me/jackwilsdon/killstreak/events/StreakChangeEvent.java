package me.jackwilsdon.killstreak.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class StreakChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private int streak;

    private boolean cancelled;

    public StreakChangeEvent(Player player, int streak) {
        this.player = player;
        this.streak = streak;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getStreak() {
        return this.streak;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public HandlerList getHandlers() {
        return StreakChangeEvent.handlers;
    }

    public static HandlerList getHandlerList() {
        return StreakChangeEvent.handlers;
    }
}
