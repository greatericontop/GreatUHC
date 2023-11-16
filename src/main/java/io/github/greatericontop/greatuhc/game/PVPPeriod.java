package io.github.greatericontop.greatuhc.game;

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

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PVPPeriod {

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        World nether = gameManager.getNether();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§cPVP is now enabled!"));
        Bukkit.broadcast(Component.text("§eThe border will now begin to shrink."));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 1.0F);
        }

        double overworldEndSize = gameManager.getPlugin().getConfig().getDouble("overworld_main_border_end");
        double netherEndSize = gameManager.getPlugin().getConfig().getDouble("nether_main_border_end");
        long shrinkTime = gameManager.getPlugin().getConfig().getLong("main_border_shrink_time");
        overworld.getWorldBorder().setSize(overworldEndSize, shrinkTime);
        nether.getWorldBorder().setSize(netherEndSize, shrinkTime);

    }

}
