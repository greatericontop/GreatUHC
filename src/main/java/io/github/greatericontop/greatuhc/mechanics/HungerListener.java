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

import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExhaustionEvent;

public class HungerListener implements Listener {
    private static final double BASE_HUNGER_MULTI = 0.75;

    @EventHandler()
    public void onExhaustion(EntityExhaustionEvent event) {
        Player player = (Player) event.getEntity();
        // from 20 points to 0, hunger exhaustion up to 150% faster (2.5x)
        double healthPenalty = 1 + 0.075 * Math.max(0, 20 - player.getHealth() - player.getAbsorptionAmount());
        // penalty for being too far from 0, 0
        double distancePenalty = 1;
        WorldBorder border = player.getWorld().getWorldBorder();
        double directDistance = Math.max(Math.abs(player.getLocation().getX()-border.getCenter().getX()), Math.abs(player.getLocation().getZ()-border.getCenter().getZ()));
        double percentageToBorder = directDistance / border.getSize();
        if (directDistance >= 200 && percentageToBorder >= 0.8) {
            // 80% to 100% of the border (if you're at least 200 blocks out) exhaustion up to 50% faster (1.5x)
            distancePenalty = 1 + 2.5 * (percentageToBorder - 0.8);
        }
        // (the theoretical maximum penalty is 3.75x, or 2.8125x vanilla)
        event.setExhaustion(event.getExhaustion() * (float) (BASE_HUNGER_MULTI * healthPenalty * distancePenalty));
    }

}
