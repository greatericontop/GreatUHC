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

    private double DAMAGE_AMOUNT;
    private long DAMAGE_EVERY;
    private double BUFFER;
    private boolean IGNORE_BUFFER_WHEN_BORDER_STOPS;
    private double MIN_DEATHMATCH_BORDER_R;

    private final GreatUHCMain plugin;
    public WorldBorderDamage(GreatUHCMain plugin) {
        this.plugin = plugin;
        reloadFromConfig();
    }

    public void reloadFromConfig() {
        this.DAMAGE_AMOUNT = plugin.getConfig().getDouble("border_damage.damage_amount", 2.0);
        this.DAMAGE_EVERY = plugin.getConfig().getLong("border_damage.damage_every", 15L);
        this.BUFFER = plugin.getConfig().getDouble("border_damage.buffer", 1.0);
        this.IGNORE_BUFFER_WHEN_BORDER_STOPS = plugin.getConfig().getBoolean("border_damage.ignore_buffer_when_border_stops", true);
        this.MIN_DEATHMATCH_BORDER_R = plugin.getConfig().getDouble("deathmatch_border_end", 0.0) / 2.0;
    }

    public boolean shouldApplyDamage(Player player) {
        double playerX = player.getLocation().getX();
        double playerZ = player.getLocation().getZ();
        double borderRadius = player.getWorld().getWorldBorder().getSize() / 2.0;
        double centerX = player.getWorld().getWorldBorder().getCenter().getX();
        double centerZ = player.getWorld().getWorldBorder().getCenter().getZ();
        double dx = Math.abs(playerX - centerX);
        double dz = Math.abs(playerZ - centerZ);
        return (dx > MIN_DEATHMATCH_BORDER_R || dz > MIN_DEATHMATCH_BORDER_R)
                && (dx > (borderRadius - BUFFER) || dz > (borderRadius - BUFFER));
    }

    public void registerRunnable() {
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)  continue;
                    if (shouldApplyDamage(player)) {
                        player.sendActionBar("§cYou are outside the world; turn back!");
                        TrueDamageHelper.dealTrueDamage(player, DAMAGE_AMOUNT);
                    }
                }
            }
        }.runTaskTimer(plugin, 200L, DAMAGE_EVERY);
    }

}
