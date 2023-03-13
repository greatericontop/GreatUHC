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

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class OldPVP implements Listener {

    @EventHandler()
    public void onCraft(CraftItemEvent event) {
        Material resultType = event.getRecipe().getResult().getType();
        if (resultType == Material.SHIELD) {
            event.getWhoClicked().sendMessage("§cShields are not available in UHC.");
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.HIGH) // runs later
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        ItemStack meleeItem = player.getInventory().getItemInMainHand();
        Material mat = meleeItem.getType();
        if (mat == Material.WOODEN_AXE || mat == Material.STONE_AXE || mat == Material.IRON_AXE || mat == Material.GOLDEN_AXE || mat == Material.DIAMOND_AXE || mat == Material.NETHERITE_AXE) {
            event.setDamage(event.getDamage() * 0.01);
            player.sendActionBar("§cYou shouldn't use axes to attack in UHC.");
        }
    }

}
