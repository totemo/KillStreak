KillStreak
==========
Get potion effects for kill streaks!

A kill streak is a continuous set of kills without dying. If you achieve X kills without death (as defined in the configuration), you get a predefined potion effect.

Get it on [BukkitDev](http://dev.bukkit.org/bukkit-plugins/killstreak/).
Configuration
-------------
`KillStreak.updater-enabled: true` - Enable the updater

`KillStreak.update-available` - Used for the updater (do not modify!)

`KillStreak.reset-on-disconnect: false` - Whether to reset a player's kill streak on disconnect

`KillStreak.streaks.5` The kill streak a player needs before they get the powerup

`KillStreak.streaks.5.potion: SPEED` - The potion to apply to the player (a full list can be found below)

`KillStreak.streaks.5.level: 1` - The level of the potion (these can be found in the list below)

`KillStreak.messages.broadcast-on-powerup: true` - Whether to broadcast to the server that the player has achieved a powerup

`KillStreak.messages.message-tag: '&f[&aKillStreak&f]'` - The prefix for any messages

`KillStreak.messages.killstreak-color: '&c'` - The color of the number shown in messages 

`KillStreak.messages.username-color: '&e'` - The color of the username shown in messages

`PlayerStreaks` - Where player killstreaks are stored (do not modify!)

A full list of chat color codes can be found [here](http://wiki.ess3.net/mc/).

Commands
--------
 - `/ks [username]` **or** `/killstreak [username]` - Shows your current kill streak, or another user's kill streak
 - `/ks reload` - Reloads the KillStreak configuration (permission: killstreak.reload)

Valid potions
-------------
 - WATER (level 1)
 - REGEN (level 1-2)
 - SPEED (level 1-2)
 - FIRE_RESISTANCE (level 1)
 - POISON (level 1-2)
 - INSTANT_HEAL (level 1-2)
 - NIGHT_VISION (level 1-2)
 - WEAKNESS (level 1)
 - STRENGTH (level 1-2)
 - SLOWNESS (level 1)
 - INSTANT_DAMAGE (level 1-2)
 - INVISIBILITY (level 1)
 
Statistics
----------
![MCStats Statistics](http://mcstats.org/signature/KillStreak.png)