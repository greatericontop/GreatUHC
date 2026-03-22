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

public class RatingCalc {

    /*
     * Calculate the handicap for a player rated :rating: in a game with average rating :ratingAverage:.
     * The higher the rating, the higher the handicap (exponentially).
     */
    public static double calcHandicap(double rating, double ratingAverage) {
        double delta = rating - ratingAverage;
        // 10% more per 200 rating points
        return Math.pow(1.1, delta/200.0);
    }

    /*
     * Perform update based off TrueSkill update formula
     */
    public static void estimate(double[] winner, double[] loser) {
        // Hyperparameter: 76% chance of winning if handicap is this many points
        double beta = 200.0;
        double c = Math.sqrt(winner[1]*winner[1] + loser[1]*loser[1] + 2*beta*beta);
        // z (or t) = 0 because they should both be equally likely to win
        double v = 0.398942 / 0.5; // normalpdf(0) / normalcdf(0)
        double w = v * v;
        winner[0] += winner[1] * winner[1] / c * v;
        loser[0] -= loser[1] * loser[1] / c * v;
        winner[1] *= Math.sqrt(1 - winner[1]*winner[1]/(c*c) * w);
        loser[1] *= Math.sqrt(1 - loser[1]*loser[1]/(c*c) * w);
    }

}
