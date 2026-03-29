# Rating System

This plugin has an optional rating system.
I made it primarily for playing with my friends.
We all have different skill levels, so I ended up pretty much winning every game easily which isn't super entertaining.

Under this rating system, better players will have higher ratings, and a higher rated player *takes more damage*.
The goal is for the damage adjustment to make everyone have an equal chance of winning.

## How it works

Higher rated players take more damage, and lower rated players take less damage.
Then at the end of each game, the winner gains rating and the losers lose rating.

You can use `/uhcrating` to check your rating (or someone else's rating too).

There are also fun colors for the ratings because why not (I *totally* didn't steal these from Codeforces):
- Default: gray
- 800: white
- 1000: green
- 1200: aqua
- 1400: blue
- 1600: pink
- 1800: yellow
- 2000: orange
- 2200: red
- 2400: dark red

*Fun fact: If a 2400 rated player fought an 800 rated player, the 2400 player would take just barely over 3 times as much damage!*

## Inner details

The initial *internal* rating is `1500` with rating deviation of `350`.

The rating displayed in game is modified: `displayed rating = rating - 4 * max(RD-100, 0)`.
This is so that new players don't see their ratings decrease greatly at the beginning.
Under the current systems, even players who lose lots of games will only lose around 10 points per game.
However, once you reach an RD of 100 or below, the displayed rating is the same as the actual rating.

For each game, the average (arithmetic mean) of the ratings is calculated.
For every 200 points a player is above the average, they take 15% more damage, exponentially.
Players take less damage if they're below the average.
(*Nerdy note: this sets the scale of the rating unit - if we had a 100 point difference make you take 15% more damage, then rating gaps would be smaller, and vice versa.*)

Rating is adjusted using Bayesian max a-posteriori estimation.
My algorithm is somewhat reminiscent of Glicko and TrueSkill, but (maybe biased) I think mine is better.
I wrote a more detailed paper about it [here](https://typst.app/project/r77r2YOHlFsQsYR08KdsBi) if you're interested in statistics and machine learning.
