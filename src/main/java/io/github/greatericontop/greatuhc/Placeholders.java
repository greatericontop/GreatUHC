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

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    private final GreatUHCMain plugin;
    public Placeholders(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "greateric";
    }

    @Override
    public String getIdentifier() {
        return "GreatUHC";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String args) {

        if (args.startsWith("border_")) {
            String worldName = args.substring(7);
            World world;
            if (worldName.equals("0") && player != null) {
                world = player.getWorld();
            } else {
                 world = plugin.getServer().getWorld(worldName);
            }
            if (world == null) {
                return String.format("§cWorld §7%s §ccouldn't be found!", worldName);
            }
            WorldBorder border = world.getWorldBorder();
            int borderRadius = (int) (border.getSize() / 2);
            if (player != null) {
                int playerRadius = (int) Math.max(Math.abs(player.getLocation().getX()-border.getCenter().getX()), Math.abs(player.getLocation().getZ()-border.getCenter().getZ()));
                String color;
                if (borderRadius - playerRadius <= 30) {
                    color = "§c";
                } else if (borderRadius - playerRadius <= 60) {
                    color = "§6";
                } else if (borderRadius - playerRadius <= 100) {
                    color = "§e";
                } else {
                    color = "§a";
                }
                return String.format("%s-%d, +%d", color, borderRadius, borderRadius);
            } else {
                return String.format("§7-%d, +%d", borderRadius, borderRadius);
            }
        }

        if (args.equals("playersalive")) {
            return String.valueOf(getPlayersAliveCount());
        }

        if (args.equals("currentlyfighting")) {
            String info = plugin.fightingDisplay.getInformation(player);
            return (info == null) ? "§7-" : info;
        }

        if (args.equals("timeleft1")) {
            return plugin.gameManager.getMessageLine1();
        }

        if (args.equals("timeleft2")) {
            return plugin.gameManager.getMessageLine2();
        }

        return null;
    }

    public static int getPlayersAliveCount() {
        int count = 0;
        // Players alive in this case means the number of non-spectator players
        for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            if (p1.getGameMode() != GameMode.SPECTATOR) {
                count++;
            }
        }
        return count;
    }

}
