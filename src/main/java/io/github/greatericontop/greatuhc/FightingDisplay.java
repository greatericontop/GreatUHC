package io.github.greatericontop.greatuhc;

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

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class FightingDisplay implements Listener {

    private int clock;
    private final Map<Player, Player> targets = new HashMap<>();
    private final Map<Player, Integer> expiry = new HashMap<>();
    private final GreatUHCMain plugin;
    public FightingDisplay(GreatUHCMain plugin) {
        this.plugin = plugin;
        clock = 0;
        new BukkitRunnable() {
            public void run() {
                clock++;
            }
        }.runTaskTimer(plugin, 5L, 5L); // don't need to be super precise
    }

    /*
     * Return the information that a placeholder would use for this.
     */
    public String getInformation(Player player) {
        Player target = targets.get(player);
        if (target == null)  return null;
        if (expiry.get(player) < clock || target.isDead()) {
            targets.remove(player);
            expiry.remove(player);
            return null;
        }
        return String.format("§e%s §f- §c%.0f §fHP", target.getDisplayName(), target.getHealth()+target.getAbsorptionAmount());
    }

    private void doFightingDisplay(Player attacker, Player victim) {
        targets.put(attacker, victim);
        expiry.put(attacker, clock + 32); // 8 seconds
    }

    @EventHandler()
    public void onDamageByEntityPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker))  return;
        if (!(event.getEntity() instanceof Player victim))  return;
        doFightingDisplay(attacker, victim);
    }

    @EventHandler()
    public void onDamageByEntityProjectile(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Projectile projectile))  return;
        if (!(projectile.getShooter() instanceof Player shooter))  return;
        if (!(event.getEntity() instanceof Player victim))  return;
        doFightingDisplay(shooter, victim);
    }

}
