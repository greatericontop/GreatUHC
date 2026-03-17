package io.github.greatericontop.greatuhc.worldgentweaks;

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
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class CustomSugarcane implements Listener {
    private static final int WATER_LVL = 62;
    private static final int[] DX = {1, -1, 0, 0};
    private static final int[] DZ = {0, 0, 1, -1};

    private final GreatUHCMain plugin;
    public CustomSugarcane(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!event.isNewChunk())  return;
        double increaseAmount = plugin.getConfig().getDouble("world_gen_tweaks.custom_sugarcane_freq");
        if (increaseAmount <= 0.0)  return;

        Chunk c = event.getChunk();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Material floorType = c.getBlock(x, WATER_LVL, z).getType();
                Material curType = c.getBlock(x, WATER_LVL + 1, z).getType();

                // Remove old sugar cane
                if (curType == Material.SUGAR_CANE) {
                    c.getBlock(x, WATER_LVL + 1, z).setType(Material.REDSTONE_BLOCK);
                    for (int y = WATER_LVL + 2; y <= WATER_LVL + 6; y++) {
                        if (c.getBlock(x, y, z).getType() == Material.SUGAR_CANE) {
                            c.getBlock(x, y, z).setType(Material.REDSTONE_BLOCK);
                            break;
                        }
                    }
                }

                if (floorType != Material.DIRT && floorType != Material.GRASS_BLOCK && floorType != Material.SAND)  continue;
                if (curType != Material.AIR)  continue; // Already cane, or obstructed
                boolean hasWaterNeighbor = false;
                for (int neighbor = 0; neighbor < 4; neighbor++) {
                    // Not going across chunk borders is a little suboptimal but that's ok
                    double x1 = x + DX[neighbor];
                    double z1 = z + DZ[neighbor];
                    if (x1 < 0 || x1 >= 16 || z1 < 0 || z1 >= 16)  continue;
                    if (c.getBlock(x + DX[neighbor], WATER_LVL, z + DZ[neighbor]).getType() == Material.WATER) {
                        hasWaterNeighbor = true;
                        break;
                    }
                }
                if (!hasWaterNeighbor)  continue;
                if (Math.random() < increaseAmount) {
                    c.getBlock(x, WATER_LVL + 1, z).setType(Material.DIAMOND_BLOCK);
                    int y = WATER_LVL + 2;
                    int maxHeight = WATER_LVL + 1 + (int)(Math.random() * 4); // uniform 1 to 4
                    while (y <= maxHeight && c.getBlock(x, y, z).getType() == Material.AIR) {
                        c.getBlock(x, y, z).setType(Material.DIAMOND_BLOCK);
                        y++;
                    }
                }
            }
        }
    }

}
