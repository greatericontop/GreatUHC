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
import io.github.greatericontop.greatuhc.util.GameUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class RatingManager implements Listener {
    private static final double DEFAULT_RATING = 1500.0; // arbitrary, I just picked a number I liked
    private static final double DEFAULT_RD = 350.0;

    private final Map<UUID, Double> handicaps = new HashMap<>();

    private final Random random;
    private final GreatUHCMain plugin;
    public RatingManager(GreatUHCMain plugin) {
        this.plugin = plugin;
        this.random = new Random();
    }


    public double getRating(UUID target) {
        return plugin.ratingConfig.getDouble("ratings.%s.rating".formatted(target.toString()), DEFAULT_RATING);
    }
    public void setRating(UUID target, double rating) {
        plugin.ratingConfig.set("ratings.%s.rating".formatted(target.toString()), rating);
    }
    public double getRD(UUID target) {
        return plugin.ratingConfig.getDouble("ratings.%s.rd".formatted(target.toString()), DEFAULT_RD);
    }
    public void setRD(UUID target, double rd) {
        plugin.ratingConfig.set("ratings.%s.rd".formatted(target.toString()), rd);
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void processHandicaps(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim))  return;
        double damageFactor = handicaps.getOrDefault(victim.getUniqueId(), 1.0);
        event.setDamage(event.getDamage() * damageFactor);
    }


    public void clearHandicaps() {
        handicaps.clear();
    }

    public void applyHandicaps(Player[] players) {
        double totalRating = 0.0;
        for (Player p : players) {
            totalRating += getRating(p.getUniqueId());
        }
        double averageRating = totalRating / players.length;
        for (Player p : players) {
            double rating = getRating(p.getUniqueId());
            double handicap = RatingCalc.calcHandicap(rating, averageRating);
            handicaps.put(p.getUniqueId(), handicap);
            Bukkit.broadcastMessage("§3Player §b%s §3has damage multiplier §b%.0f%%§3!".formatted(p.getName(), handicap*100.0));
        }
    }

    public Map<Player, Double> processGame(Player winner, Player[] players) {
        Map<Player, Double> deltas = new HashMap<>();
        GameUtils.shuffle(random, players);
        for (Player p1 : players) {
            if (p1.getUniqueId().equals(winner.getUniqueId())) continue;
            UUID uuidWinner = winner.getUniqueId();
            UUID uuidLoser = p1.getUniqueId();
            double[] winnerRating = {getRating(uuidWinner), getRD(uuidWinner)};
            double[] loserRating = {getRating(uuidLoser), getRD(uuidLoser)};
            deltas.put(winner, deltas.getOrDefault(winner, 0.0) - winnerRating[0]);
            deltas.put(p1, deltas.getOrDefault(p1, 0.0) - loserRating[0]);
            RatingCalc.estimate(winnerRating, loserRating);
            deltas.put(winner, deltas.get(winner) + winnerRating[0]);
            deltas.put(p1, deltas.get(p1) + loserRating[0]);
            setRating(uuidWinner, winnerRating[0]);
            setRD(uuidWinner, winnerRating[1]);
            setRating(uuidLoser, loserRating[0]);
            setRD(uuidLoser, loserRating[1]);
        }
        return deltas;
    }

}
