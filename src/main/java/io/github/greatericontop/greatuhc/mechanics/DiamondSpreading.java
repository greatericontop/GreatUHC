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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Random;

public class DiamondSpreading implements Listener {

    private static final int[] dx = {1, -1, 0, 0, 0, 0};
    private static final int[] dy = {0, 0, 1, -1, 0, 0};
    private static final int[] dz = {0, 0, 0, 0, 1, -1};

    private final Random random;
    public DiamondSpreading() {
        this.random = new Random();
    }

    public boolean isExposed(Location loc) {
        for (int i = 0; i < 6; i++) {
            Material mat = loc.clone().add(dx[i], dy[i], dz[i]).getBlock().getType();
            if (mat.isAir() || !mat.isSolid()) {
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Material blockType = event.getBlock().getType();
        if (blockType != Material.DIAMOND_ORE && blockType != Material.DEEPSLATE_DIAMOND_ORE) {
            return;
        }
        if (Math.random() >= 0.24) {
            return;
        }
        // Make one attempt to spawn a diamond ore.
        Location loc = event.getBlock().getLocation();
        int offset = random.nextInt(6);
        loc.add(dx[offset], dy[offset], dz[offset]);
        // the target block must be covered (unexposed to air)
        if (isExposed(loc)) {
            return;
        }
        // replace
        Material mat = loc.getBlock().getType();
        if (mat == Material.STONE || mat == Material.ANDESITE || mat == Material.DIORITE || mat == Material.GRANITE) {
            loc.getBlock().setType(Material.DIAMOND_ORE);
        } else if (mat == Material.DEEPSLATE || mat == Material.TUFF) {
            loc.getBlock().setType(Material.DEEPSLATE_DIAMOND_ORE);
        }
    }

}
