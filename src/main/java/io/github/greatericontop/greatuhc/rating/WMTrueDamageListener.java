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

import io.github.greatericontop.weaponmaster.api.TrueDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class WMTrueDamageListener implements Listener {

    private RatingManager ratingManager;
    public WMTrueDamageListener(RatingManager ratingManager) {
        this.ratingManager = ratingManager;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTrueDamage(TrueDamageEvent event) {
        if (!(event.getTarget() instanceof Player victim))  return;
        if (event.getCause().equals("wm:scythe")) {
            // Do not change Death's Scythe damage
            // Mainly Excalibur and Ares
            // Also GreatUHC's true damage doesn't get affected either since it doesn't call the event
            //   Things like border damage, chest of fate
            return;
        }
        double damageFactor = ratingManager.handicaps.getOrDefault(victim.getUniqueId(), 1.0);
        event.setAmount(event.getAmount() * damageFactor);
    }


}
