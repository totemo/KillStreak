KillStreak
==========
Get poiton effects for kill streaks!

Configuration
-------------
	KillStreak:				# The configuration
	  reset-on-disconnect: false		# Clear a player's killstreak on disconnect
	  streaks:				# A list of streak types
	    5:					# Triggered when a player's killstreak reaches 10
	      potion: SPEED			# Potion type
	      level: 1				# Potion Level
	    10:					# Triggered when a player's killstreak reaches 10
	      potion: SPEED			# Potion Type
	      level: 2				# Potion Level
	PlayerStreaks:				# Storage for current killstreaks