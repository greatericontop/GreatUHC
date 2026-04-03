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

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.util.GreatCommands;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RatingCommand implements CommandExecutor, TabCompleter {

    private final GreatUHCMain plugin;
    public RatingCommand(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflinePlayer p = GreatCommands.argumentOfflinePlayer(0, args);
        double rating;
        double rd;
        double displayedRating;
        double peakRating;
        String name;
        if (p == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou must be a player to check your rating!");
                return false;
            }
            rating = plugin.ratingManager.getRating(player.getUniqueId());
            rd = plugin.ratingManager.getRD(player.getUniqueId());
            displayedRating = plugin.ratingManager.getDisplayedRating(player.getUniqueId());
            peakRating = plugin.ratingManager.getPeakRating(player.getUniqueId());
            name = player.getName();
        } else {
            rating = plugin.ratingManager.getRating(p.getUniqueId());
            rd = plugin.ratingManager.getRD(p.getUniqueId());
            displayedRating = plugin.ratingManager.getDisplayedRating(p.getUniqueId());
            peakRating = plugin.ratingManager.getPeakRating(p.getUniqueId());
            name = p.getName();
        }
        sender.sendMessage("§9------------------------------");
        sender.sendMessage("%s§3's rating: %s".formatted(plugin.ratingManager.renderName(name, displayedRating), plugin.ratingManager.renderRating(displayedRating)));
        sender.sendMessage("§7Performance: %.0f    RD: %.0f".formatted(rating, rd));
        sender.sendMessage("§3Peak rating: %s".formatted(plugin.ratingManager.renderRating(peakRating)));

        GameHistoryEntry[] history = plugin.ratingManager.historyManager.getLatestEntries(p == null ? ((Player) sender).getUniqueId() : p.getUniqueId(), 10);
        if (history.length > 0) {
            sender.sendMessage("§3Recent games:");
            for (GameHistoryEntry entry : history) {
                String result = entry.won() ? "§aWon vs" : "§7Lost vs";
                StringBuilder opponentDisplay = new StringBuilder();
                for (int i = 0; i < entry.oppNames().size(); i++) {
                    String oppName = entry.oppNames().get(i);
                    double oppRating = entry.oppRatings().get(i);
                    opponentDisplay.append(plugin.ratingManager.renderName(oppName, oppRating));
                    if (i < entry.oppNames().size() - 1) {
                        opponentDisplay.append("§7, ");
                    }
                }
                String delta = "%s%.0f".formatted(entry.ratingDelta() >= 0 ? "§a+" : "§7", entry.ratingDelta());
                sender.sendMessage("§7- %s %s  §7(%s§7)".formatted(result, opponentDisplay, delta));
            }
        }
        sender.sendMessage("§9------------------------------");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> playerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerNames.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0], playerNames, new ArrayList<>(playerNames.size()));
        }
        return null;
    }

}
