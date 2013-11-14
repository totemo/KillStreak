package me.jackwilsdon.killstreak;

import org.bukkit.plugin.java.JavaPlugin;

public final class KillStreakPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        EventListener listener = new EventListener(this, this.getConfig());
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @Override
    public void onDisable() {
    }
}
