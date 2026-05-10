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
import io.github.greatericontop.greatuhc.game.GameManager;
import io.github.greatericontop.greatuhc.util.XYZ;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;
import java.util.Map;

public class DeathmatchBlockDespawn implements Listener {

    private final Map<XYZ, Long> nonces = new HashMap<>();
    private long nonce = 0;
    private long TICKS_TO_DESPAWN;

    private final GreatUHCMain plugin;
    public DeathmatchBlockDespawn(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    public void reset() {
        if (!nonces.isEmpty()) {
            plugin.getLogger().warning(String.format("Possible memory leak! %d blocks placed during deathmatch were never cleared!", nonces.size()));
        }
        nonces.clear();
        nonce = 0;
        TICKS_TO_DESPAWN = plugin.getConfig().getLong("deathmatch_blocks_despawn_after", 100L);
    }

    @EventHandler()
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.gameManager.getCurrentPhase() != GameManager.GamePhase.DEATHMATCH)  return;
        XYZ xyz = new XYZ(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        long cur_nonce = nonce++;
        nonces.put(xyz, cur_nonce);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (nonces.getOrDefault(xyz, -1L) == cur_nonce) {
                event.getBlock().setType(Material.AIR);
                nonces.remove(xyz);
            }
        }, TICKS_TO_DESPAWN);
    }

    @EventHandler()
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.gameManager.getCurrentPhase() != GameManager.GamePhase.DEATHMATCH)  return;
        XYZ xyz = new XYZ(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
        nonces.remove(xyz);
    }

}
