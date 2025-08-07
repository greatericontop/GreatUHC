# GreatUHC

A simple UHC plugin.

---

## Game Structure

When the game is started, everyone is teleported to a bedrock platform. You'll get some time to choose a kit.

Then, you'll be teleported randomly in the map. This is the grace period, and you have 15 minutes to peacefully gather materials.

After the grace period, everyone is allowed to fight.

Later after that, Deathmatch will begin. Everyone will be teleported to a randomly generated arena and forced to fight there until the end of the game.

## Mechanics

- Absorption fix
- Baby animals grow faster
- Players drop heads on death, which can be eaten
  - They can also be made into golden heads for more healing
- Diamonds have a small chance to spread to other blocks
- Hunger is modified - generally it decreases slower, but exhaustion is faster if you're low on health or are close to the border
- Item drops are tweaked
  - Iron and gold drop ingots and have a small chance to double
  - (Configurable) Andesite, diorite, and granite drop cobblestone
  - Flint has a higher drop rate
  - Mobs drop more loot and experience (skeletons have a 15% chance to drop a new bow, endermen always drop a pearl, etc.)
  - All leaves drop apples (including when mined with shears), with oak and dark oak leaves having a higher chance
  - Nether gold ore drops significantly less gold
- Blazes can spawn in the open nether outside a fortress
- Older version PVP rules apply
  - Shields are disabled and axes deal less damage
  - (Configurable) Swords have unlimited attack speed
- Players drop gold on death and give their killers the strength effect for a few seconds
- Endermen won't teleport when they are hit
- The world border does true damage, but it is constant
- Enchantments like `Sharpness` and `Protection` are buffed to be similar to 1.8 levels
- You take less damage from natural sources
- **There are numerous custom crafts to explore!**

## Commands

`/uhcstart` - starts the game

`/uhc` - allows you to manage things (like modifiers)
- It can set modifiers
- `/uhc crafts` clears craft counters
- `/uhc extend-clock <seconds>` extends the game clock by the specified amount
- `/uhc debug-mode` toggles debug mode

