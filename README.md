KillStreak
==========
Get potion effects for kill streaks!

Configuration
-------------
	KillStreak:
	  reset-on-disconnect: false # Whether to reset a player's kill streak on disconnect
	  streaks:
		5: # The kill streak a player needs before they get the powerup
		  potion: SPEED # The potion to apply to the player (a full list can be found below)
		  level: 1 # The level of the potion (1/2)
	  messages:
        broadcast-on-powerup: true # Whether to broadcast to the server that the player has achieved a powerup
        tell-player-on-powerup: false # Whether to tell a player they have achieved a powerup
        message-tag: '&f[&aKillStreak&f]' # The prefix for any messages
        killstreak-color: '&c' # The color of the number shown in messages 
        username-color: '&e' # The color of the username shown in messages
	PlayerStreaks:

A full list of chat color codes can be found here; http://wiki.ess3.net/mc/

Commands
--------
 - `/ks [username]` **or** `/killstreak [username]` - Shows your current kill streak, or another user's kill streak

Valid potions
-------------
 - FIRE_RESISTANCE 
 - INSTANT_DAMAGE 
 - INSTANT_HEAL 
 - INVISIBILITY 
 - NIGHT_VISION 
 - POISON 
 - REGEN 
 - SLOWNESS 
 - SPEED 
 - STRENGTH 
 - WATER 
 - WEAKNESS 