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

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@SerializableAs("GameHistoryEntry")
public record GameHistoryEntry(
        boolean won,
        double yourRatingBefore,
        List<String> oppNames,
        List<Double> oppRatings,
        double ratingDelta
) implements ConfigurationSerializable {

    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "won", won,
                "yourRatingBefore", yourRatingBefore,
                "oppNames", oppNames,
                "oppRatings", oppRatings,
                "ratingDelta", ratingDelta
        );
    }

    public static GameHistoryEntry deserialize(Map<String, Object> data) {
        String opponentName = (String) data.get("opponentName");
        boolean won = (boolean) data.get("won");
        double yourRatingBefore = (double) data.get("yourRatingBefore");
        List<String> oppNames = (List<String>) data.get("oppNames");
        List<Double> oppRatings = (List<Double>) data.get("oppRatings");
        double ratingDelta = (double) data.get("ratingDelta");
        return new GameHistoryEntry(won, yourRatingBefore, oppNames, oppRatings, ratingDelta);
    }

}
