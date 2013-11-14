package me.jackwilsdon.killstreak;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class StreakHandler {
    private KillStreakPlugin plugin;

    private ConfigurationSection config;
    private ConfigurationSection players;

    public StreakHandler(KillStreakPlugin plugin, FileConfiguration configuration) {
        this.plugin = plugin;
        this.config = configuration.getConfigurationSection("KillStreak");
        this.players = configuration.getConfigurationSection("PlayerStreaks");

        for (String key: this.config.getKeys(true))
        {
            System.out.println(key);
        }
    }

    public ConfigurationSection getConfig() {
        return this.config;
    }

    public ConfigurationSection getPlayers() {
        return this.players;
    }

    public int getStreak(String name) {
        return (this.players == null) ? 0 : this.players.getInt(name);
    }

    public void setStreak(String name, int streak) {
        this.players.set(name, streak);
        this.plugin.saveConfig();
    }

    public PotionEffect getPotion(int streak) {
        ConfigurationSection powerup = this.config.getConfigurationSection("streaks." + streak);

        if (powerup == null) {
            return null;
        }

        PotionEffectType type = PotionEffectType.getByName(powerup.getString("potion"));

        int duration = (powerup.getInt("duration") == 0) ? 60 : powerup.getInt("duration");
        int amplifier = (powerup.getInt("level") == 0) ? powerup.getInt("amplifier") : powerup.getInt("level");

        if (type == null) {
            return null;
        }

        PotionEffect effect = new PotionEffect(type, duration * 20, amplifier);
        return effect;
    }
}
