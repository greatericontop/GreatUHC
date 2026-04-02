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
    public static double calcHandicap(double rating, double ratingAverage, double damageMultiplier) {
        double delta = rating - ratingAverage;
        return Math.pow(damageMultiplier, delta/200.0);
    }

    private static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /*
     * Perform update using our own in-house rating calculation :)
     */
    public static void estimate(double[] winner, double[] loser, double beta) {
        double g_winner = Math.sqrt(beta*beta + Math.PI/8.0 * loser[1]*loser[1]);
        double g_loser = Math.sqrt(beta*beta + Math.PI/8.0 * winner[1]*winner[1]);
        double winner_delta = 1.0 / (  (2.0*g_winner)/(winner[1]*winner[1]) + 1.0/(2.0*g_winner)  );
        double loser_delta = 1.0 / (  (2.0*g_loser)/(loser[1]*loser[1]) + 1.0/(2.0*g_loser)  );
        double winner_rd = 1.0 / Math.sqrt(  1.0/(winner[1]*winner[1]) + sigmoid(winner_delta/g_winner)*(1-sigmoid(winner_delta/g_winner))/(g_winner*g_winner)  );
        double loser_rd = 1.0 / Math.sqrt(  1.0/(loser[1]*loser[1]) + sigmoid(loser_delta/g_loser)*(1-sigmoid(loser_delta/g_loser))/(g_loser*g_loser)  );
        winner[0] += winner_delta;
        loser[0] -= loser_delta;
        winner[1] = winner_rd;
        loser[1] = loser_rd;
    }

}
