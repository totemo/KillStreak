KillStreak
==========
Get poiton effects for kill streaks!

Configuration
-------------
	KillStreak:
	  reset-on-disconnect: false
	  streaks:
		5:
		  potion: SPEED
		  level: 1
		10:
		  potion: SPEED
		  level: 2
	PlayerStreaks:
- - - -
**Breakdown of the configuration**

 - `KillStreak` - The base node for the configuration. **Do not rename this!**
   - `reset-on-disconnect` - Whether to reset a player's kill streak on disconnect
   - `streaks` - A list of potions rewarded on streaks
     - `5` - The streak a player must achieve to get a powerup (can be any number)
	   - `potion` - The potion type. Available types are listed below
	   - `level` - The level of the potion. Available levels are listed below
