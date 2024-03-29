package io.github.greatericontop.greatuhc.util;

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
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class GameUtils {

    public static void freezePlayers(GreatUHCMain plugin, int ticks) {
        Map<UUID, Location> locations = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            locations.put(player.getUniqueId(), player.getLocation());
        }
        new BukkitRunnable() {
            int counter = ticks / 5;
            public void run() {
                if (counter <= 0) {
                    cancel();
                    return;
                }
                counter--;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location previousLoc = locations.get(player.getUniqueId());
                    if (previousLoc == null) {
                        continue;
                    }
                    Location playerLoc = player.getLocation();
                    // only teleport when XYZ changes, allow changes to yaw/pitch
                    if (Math.abs(playerLoc.getX()-previousLoc.getX()) > 0.5
                            || Math.abs(playerLoc.getY() - previousLoc.getY()) > 1.25
                            || Math.abs(playerLoc.getZ() - previousLoc.getZ()) > 0.5) {
                        player.teleport(previousLoc);
                    }
                }
                if (counter % 4 == 0 && counter != 0) {
                    int seconds = counter / 4;
                    Bukkit.broadcastMessage(String.format("§7You'll be able to move in §e%d§7 seconds.", seconds));
                }
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }

    public static @Nullable Player getWinner() {
        Player winner = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.SPECTATOR && player.getHealth() > 0) {
                if (winner != null) {
                    return null; // returns null if there are multiple players left
                } else {
                    winner = player;
                }
            }
        }
        return winner;
    }

    public static int[] shuffleChest(Random random) {
        // Durstenfeld Shuffle
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int i = arr.length-1; i >= 1; i--) {
            int other = random.nextInt(i+1); // 0 to i, inclusive
            int temp = arr[other];
            arr[other] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    public static int[] shufflePositions(Random random) {
        // Durstenfeld Shuffle
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        for (int i = arr.length-1; i >= 1; i--) {
            int other = random.nextInt(i+1); // 0 to i, inclusive
            int temp = arr[other];
            arr[other] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

}
