package io.github.greatericontop.greatuhc.rating;

/*
 * Copyright (C) 2023-present greateric.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RatingGameHistoryManager {
    private int MAX_GAME_HISTORY;

    private final GreatUHCMain plugin;
    public RatingGameHistoryManager(GreatUHCMain plugin) {
        this.plugin = plugin;
        reloadHyperparameters();
    }

    public void reloadHyperparameters() {
        MAX_GAME_HISTORY = plugin.getConfig().getInt("rating_settings.max_game_history", 200);
    }


    private void saveEntry(UUID uuid, GameHistoryEntry entry) {
        int counter = plugin.ratingConfig.getInt("%s.game_history_counter".formatted(uuid.toString()), 0);
        plugin.ratingConfig.set("%s.game_history.%d".formatted(uuid.toString(), counter), entry);
        plugin.ratingConfig.set("%s.game_history_counter".formatted(uuid.toString()), counter + 1);
    }

    public void pruneOldData(UUID uuid) {
        int latest = plugin.ratingConfig.getInt("%s.game_history_counter".formatted(uuid.toString()), 0) - 1;
        int earliest = plugin.ratingConfig.getInt("%s.game_history_earliest".formatted(uuid.toString()), 0);
        while (latest - earliest + 1 > MAX_GAME_HISTORY) {
            plugin.getLogger().info("Pruned game history entry #%d for player %s".formatted(earliest, uuid.toString()));
            plugin.ratingConfig.set("%s.game_history.%d".formatted(uuid.toString(), earliest), null);
            earliest++;
        }
        plugin.ratingConfig.set("%s.game_history_earliest".formatted(uuid.toString()), earliest);
    }

    public void saveGame(Player winner, Player[] players, Map<Player, Double> previousRatings, Map<Player, Double> deltas) {
        for (Player p : players) {
            boolean won = p.getUniqueId().equals(winner.getUniqueId());
            double yourRatingBefore = previousRatings.get(p);
            double ratingDelta = deltas.get(p);
            List<String> oppNames = new ArrayList<>();
            List<Double> oppRatings = new ArrayList<>();
            for (Player opp : players) {
                if (!opp.getUniqueId().equals(p.getUniqueId())) {
                    oppNames.add(opp.getName());
                    oppRatings.add(previousRatings.get(opp));
                }
            }
            GameHistoryEntry entry = new GameHistoryEntry(won, yourRatingBefore, oppNames, oppRatings, ratingDelta);
            saveEntry(p.getUniqueId(), entry);
            pruneOldData(p.getUniqueId());
        }
    }


    /* Return an array of the last up to :n: games played by this player, most recent in [0]. */
    public GameHistoryEntry[] getLatestEntries(UUID uuid, int n) {
        int counter = plugin.ratingConfig.getInt("%s.game_history_counter".formatted(uuid.toString()), 0) - 1;
        int earliest = plugin.ratingConfig.getInt("%s.game_history_earliest".formatted(uuid.toString()), 0);
        GameHistoryEntry[] entries = new GameHistoryEntry[Math.min(n, counter - earliest + 1)];
        for (int i = 0; i < entries.length; i++) {
            entries[i] = plugin.ratingConfig.getObject("%s.game_history.%d".formatted(uuid.toString(), counter - i), GameHistoryEntry.class);
        }
        return entries;
    }


}
