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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class InstaboomTNT implements Listener {

    private final GreatUHCMain plugin;
    public InstaboomTNT(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onBlockPlace(BlockPlaceEvent event) {
        if (plugin.gameManager.getCurrentPhase() != GameManager.GamePhase.DEATHMATCH)  return;
        if (event.getBlockPlaced().getType() != Material.TNT)  return;
        Player player = event.getPlayer();
//        event.setCancelled(true);
//        if (event.getHand() == EquipmentSlot.HAND) {
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
//        } else {
//            player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
//        }
        event.getBlockPlaced().setType(Material.AIR);
        event.getBlockPlaced().getWorld().createExplosion(event.getBlockPlaced().getLocation(), 4.0F, false, true, player);
    }

}
