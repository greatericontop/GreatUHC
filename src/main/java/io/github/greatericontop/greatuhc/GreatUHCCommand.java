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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GreatUHCCommand implements CommandExecutor, TabCompleter {

    private final GreatUHCMain plugin;
    public GreatUHCCommand(GreatUHCMain plugin) {
        this.plugin = plugin;
    }

    private Component generateMsg(String modName, boolean state, String command) {
        return Component
                .text(String.format("§e%s§7: §f[%s§f]  ", modName, state ? "§2ON" : "§4OFF"))
                .append(Component.text("§7[toggle]")
                        .clickEvent(ClickEvent.runCommand(String.format("/greatuhc %s", command)))
                        .hoverEvent(Component.text("§7Click to toggle this modifier."))
                );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§9--------------------------------------------------");
            sender.sendMessage("§3GreatUHC by greateric");
            sender.sendMessage("");
            sender.sendMessage(generateMsg("Double Heads", plugin.uhcDoubleHeads, "double-heads"));
            sender.sendMessage(generateMsg("Powerful Heads", plugin.uhcPowerfulHeads, "powerful-heads"));
            sender.sendMessage(generateMsg("Survivalism", plugin.uhcSurvivalism, "survivalism"));
            sender.sendMessage(generateMsg("Mining Modifier", plugin.uhcMiningModifier, "mining-modifier"));
            sender.sendMessage(generateMsg("Starting Heads", plugin.uhcStartingHeads, "starting-heads"));
            sender.sendMessage(generateMsg("Random Ultimate", plugin.uhcRandomUltimate, "random-ultimate"));
            sender.sendMessage("");
            sender.sendMessage(generateMsg("All Drop Stone", plugin.uhcAllDropStone, "drop-stone"));
            sender.sendMessage(generateMsg("Fast Reflexes", plugin.uhcFastReflexes, "fast-reflexes"));
            sender.sendMessage(generateMsg("Fate Kit", plugin.uhcFateKit, "fate-kit"));
            sender.sendMessage(generateMsg("Enhanced Kits", plugin.uhcEnhancedKits, "enhanced-kits"));
            sender.sendMessage("");
            sender.sendMessage(generateMsg("Debug Mode", plugin.debugMode, "debug-mode"));
            sender.sendMessage("");
            sender.sendMessage("§9--------------------------------------------------");
            return true;
        }
        if (args[0].equals("crafts")) {
            plugin.craftLimiter.clearCrafts();
            sender.sendMessage("§aCrafts cleared!");
            return true;
        }
        if (args[0].equals("extend-clock")) {
            int seconds;
            if (args.length == 1) {
                seconds = 30;
            } else {
                try {
                    seconds = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid number!");
                    return true;
                }
            }
            plugin.gameManager.extend(seconds);
            Bukkit.broadcastMessage(String.format("§aThe clock was extended by %d seconds!", seconds));
            return true;
        }
        if (args[0].equals("double-heads")) {
            plugin.uhcDoubleHeads = !plugin.uhcDoubleHeads;
            sender.sendMessage(String.format("§cDouble Heads §3is now %s", plugin.uhcDoubleHeads ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("powerful-heads")) {
            plugin.uhcPowerfulHeads = !plugin.uhcPowerfulHeads;
            sender.sendMessage(String.format("§cPowerful Heads §3is now %s", plugin.uhcPowerfulHeads ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("survivalism")) {
            plugin.uhcSurvivalism = !plugin.uhcSurvivalism;
            sender.sendMessage(String.format("§cSurvivalism §3is now %s", plugin.uhcSurvivalism ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("mining-modifier")) {
            plugin.uhcMiningModifier = !plugin.uhcMiningModifier;
            sender.sendMessage(String.format("§cMining Modifier §3is now %s", plugin.uhcMiningModifier ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("debug-mode")) {
            plugin.debugMode = !plugin.debugMode;
            sender.sendMessage(String.format("§cDebug Mode §3is now %s", plugin.debugMode ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("starting-heads")) {
            plugin.uhcStartingHeads = !plugin.uhcStartingHeads;
            sender.sendMessage(String.format("§cStarting Heads §3is now %s", plugin.uhcStartingHeads ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("random-ultimate")) {
            plugin.uhcRandomUltimate = !plugin.uhcRandomUltimate;
            sender.sendMessage(String.format("§cRandom Ultimate §3is now %s", plugin.uhcRandomUltimate ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("all-drop-stone")) {
            plugin.uhcAllDropStone = !plugin.uhcAllDropStone;
            sender.sendMessage(String.format("§cAll Drop Stone §3is now %s", plugin.uhcAllDropStone ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("fast-reflexes")) {
            plugin.uhcFastReflexes = !plugin.uhcFastReflexes;
            sender.sendMessage(String.format("§cFast Reflexes §3is now %s", plugin.uhcFastReflexes ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("fate-kit")) {
            plugin.uhcFateKit = !plugin.uhcFateKit;
            sender.sendMessage(String.format("§cFate Kit §3is now %s", plugin.uhcFateKit ? "§2ON" : "§4OFF"));
            return true;
        }
        if (args[0].equals("enhanced-kits")) {
            plugin.uhcEnhancedKits = !plugin.uhcEnhancedKits;
            sender.sendMessage(String.format("§cEnhanced Kits §3is now %s", plugin.uhcEnhancedKits ? "§2ON" : "§4OFF"));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> mainCommands = Arrays.asList(
                    "crafts", "extend-clock",

                    "double-heads", "powerful-heads", "survivalism",
                    "mining-modifier", "starting-heads", "random-ultimate",

                    "all-drop-stone", "fast-reflexes", "fate-kit", "enhanced-kits",

                    "debug-mode"
            );
            return StringUtil.copyPartialMatches(args[0], mainCommands, new ArrayList<>(mainCommands.size()));
        }
        if (args.length == 2 && args[0].equals("extend-clock")) {
            return List.of("<seconds>");
        }
        return null;
    }

}
