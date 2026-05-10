package io.github.greatericontop.greatuhc.mechanics;

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
import io.github.greatericontop.greatuhc.util.TrueDamageHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldBorderDamage {

    // Players will begin to take damage even if they are inside the border but close to the edge.
    private static final double BUFFER = 1.0;

    public static boolean shouldApplyDamage(Player player) {
        double playerX = player.getLocation().getX();
        double playerZ = player.getLocation().getZ();
        double borderRadius = player.getWorld().getWorldBorder().getSize() / 2.0;
        double centerX = player.getWorld().getWorldBorder().getCenter().getX();
        double centerZ = player.getWorld().getWorldBorder().getCenter().getZ();
        double dx = Math.abs(playerX - centerX);
        double dz = Math.abs(playerZ - centerZ);
        // TODO: make it configurable
        return (dx > 10.0 || dz > 10.0) && (dx > (borderRadius - BUFFER) || dz > (borderRadius - BUFFER));
    }

    public static void registerRunnable(GreatUHCMain plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)  continue;
                    if (shouldApplyDamage(player)) {
                        player.sendActionBar("§cYou are outside the world; turn back!");
                        TrueDamageHelper.dealTrueDamage(player, 1.0);
                    }
                }
            }
        }.runTaskTimer(plugin, 200L, 15L);
    }

}
