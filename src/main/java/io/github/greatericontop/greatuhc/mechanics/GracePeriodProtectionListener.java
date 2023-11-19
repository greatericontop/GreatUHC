package io.github.greatericontop.greatuhc.mechanics;/*
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

import io.github.greatericontop.greatuhc.game.GameManager;
import io.github.greatericontop.greatuhc.util.TrueDamageHelper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class GracePeriodProtectionListener implements Listener {

    private final GameManager gameManager;
    public GracePeriodProtectionListener(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // All run last so cancellation has highest priority & we know the final damage
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMeleeDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))  return;
        if (!(event.getEntity() instanceof Player))  return; // victim check
        if (gameManager.getCurrentPhase() == GameManager.GamePhase.GRACE_PERIOD) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot damage players during the grace period!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerProjectileDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile projectile))  return;
        if (!(projectile.getShooter() instanceof Player player))  return;
        if (!(event.getEntity() instanceof Player))  return; // victim check
        if (gameManager.getCurrentPhase() == GameManager.GamePhase.GRACE_PERIOD) {
            event.setCancelled(true);
            player.sendMessage("§cYou cannot damage players during the grace period!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTakeEnvironmentalDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player victim))  return;
        if (!(
                gameManager.getCurrentPhase() == GameManager.GamePhase.GRACE_PERIOD
                && gameManager.getPlugin().getConfig().getBoolean("grace_period_extra_protection")
        )) {
            return;
        }
        if (event instanceof EntityDamageByEntityEvent event1) {
            // If this damage was caused by an entity, EXCLUDE projectiles & melee damage from LivingEntity-s
            if (event1.getDamager() instanceof Projectile || event1.getDamager() instanceof LivingEntity) {
                return;
            }
        }

        // Damage all players within 1.5 X/Z (square box) and 3 Y
        for (Entity e : victim.getNearbyEntities(1.5, 3.0, 1.5)) {
            if (!(e instanceof Player nearbyPlayer))  continue;
            if (nearbyPlayer == victim)  continue;
            TrueDamageHelper.dealTrueDamage(nearbyPlayer, event.getFinalDamage());
            nearbyPlayer.sendMessage("§cYou are too close to this player!");
        }
    }


}
