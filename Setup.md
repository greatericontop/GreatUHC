# GreatUHC Setup and Config

Most parts of the plugin work out of the box, but there are a few additional features that require some additional setup.

Right now, the only thing you need to set up (if you want to use it) is the scoreboard!

## Scoreboard

You need a separate scoreboard plugin.
I use [AnimatedScoreboard](https://www.spigotmc.org/resources/animatedscoreboard.20848/).

### Quick AnimatedScoreboard Setup

1. Download and install [AnimatedScoreboard](https://www.spigotmc.org/resources/animatedscoreboard.20848/).
2. Run the server once to generate the config files.
3. Replace the `globalscoreboard.yml` file in `plugins/AnimatedScoreboard/scoreboards` with [this prewritten one](https://github.com/greatericontop/GreatUHC/blob/main/setup_files/globalscoreboard.yml).

### Customizing and/or using a different scoreboard plugin

GreatUHC has the following placeholders (you do need [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)) that you can use in making your own scoreboards:

- `%greatuhc_border_0%` - shows the world border size in the player's current world
- `%greatuhc_border_my_world%` - shows the world border size in the world "my_world" (you can change the name)
- `%greatuhc_playersalive%` - shows how many players are alive
- `%greatuhc_currentlyfighting%` - shows the health of the player you're currently fighting (if you've hit them in the last several seconds)
- `%greatuhc_timeleft1%` - shows the name of the next phase (will look like "PvP Enables in" or "Deathmatch in")
- `%greatuhc_timeleft2%` - shows the actual time until the next phase (will look like "6 minutes" or "28 seconds")
