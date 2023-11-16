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

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.Placeholders;
import io.github.greatericontop.greatuhc.game.pregame.PreGameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {

    public enum GamePhase {
        INACTIVE, PRE_GAME, GRACE_PERIOD, PVP, DEATHMATCH
    }

    private GamePhase currentPhase;
    public GamePhase getCurrentPhase() {
        return currentPhase;
    }
    private int ticksLeft;
    public int getTicksLeft() {
        return ticksLeft;
    }

    private World overworld;
    public World getOverworld() {
        return overworld;
    }
    private World nether;
    public World getNether() {
        return nether;
    }

    private final GreatUHCMain plugin;
    public GreatUHCMain getPlugin() {
        return plugin;
    }

    private final PreGameManager preGameManager;
    public PreGameManager getPreGameManager() {
        return preGameManager;
    }

    public GameManager(GreatUHCMain plugin, PreGameManager preGameManager) {
        this.plugin = plugin;
        this.preGameManager = preGameManager;
        currentPhase = GamePhase.INACTIVE;
        ticksLeft = -1;

        overworld = Bukkit.getWorld(plugin.getConfig().getString("overworld_name"));
        nether = Bukkit.getWorld(plugin.getConfig().getString("nether_name"));
    }

    public void registerRunnable() {
        new BukkitRunnable() {
            public void run() {
                if (currentPhase == GamePhase.INACTIVE) {
                    return;
                }
                ticksLeft--;
                if (ticksLeft <= 0) {
                    switch (currentPhase) {
                        case PRE_GAME -> {
                            currentPhase = GamePhase.GRACE_PERIOD;
                            ticksLeft = plugin.getConfig().getInt("grace_period_time");
                            GracePeriod.start(GameManager.this);
                        }
                        case GRACE_PERIOD -> {
                            currentPhase = GamePhase.PVP;
                            ticksLeft = plugin.getConfig().getInt("pvp_time");;
                            PVPPeriod.start(GameManager.this);
                        }
                        case PVP -> {
                            currentPhase = GamePhase.DEATHMATCH;
                            ticksLeft = plugin.getConfig().getInt("deathmatch_time");
                            DeathmatchPeriod.start(GameManager.this);
                        }
                        case DEATHMATCH -> {
                            currentPhase = GamePhase.INACTIVE;
                            ticksLeft = 0;
                        }
                    }
                } else if (ticksLeft % 20 == 0) {
                    // note: since `ticksLeft--` was already called, this will NOT execute on the first tick when a clock starts
                    int seconds = ticksLeft / 20;
                    if (seconds == 30 || seconds == 45 || seconds == 60) { // putting this first makes "60 seconds" run rather than "1 minute"
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                        }
                        Bukkit.broadcastMessage(String.format("§7| §b%d §6seconds left", seconds));
                    } else if (seconds % 60 == 0) {
                        int minutes = seconds / 60;
                        if (minutes <= 8 || minutes % 5 == 0) {
                            Bukkit.broadcastMessage(String.format("§7| §b%d §eminutes left", minutes));
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                            }
                        }
                    } else if (seconds <= 20) {
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                        }
                        if (seconds <= 10 || seconds == 15 || seconds == 20) {
                            Bukkit.broadcastMessage(String.format("§7| §b%d §cseconds left", seconds));
                        }
                    }
                }
                // PVP: game shortener
                if (currentPhase == GamePhase.PVP
                        && ticksLeft > plugin.getConfig().getInt("game_shortener.decrease_to")
                        && Placeholders.getPlayersAliveCount() <= plugin.getConfig().getInt("game_shortener.players_left")
                ) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("game_shortener.message")));
                    for (Player p1 : Bukkit.getOnlinePlayers()) {
                        p1.playSound(p1.getLocation(), Sound.ENTITY_WITHER_SPAWN, 1.0F, 1.0F);
                    }
                    ticksLeft = 6000;
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    public void start() {
        currentPhase = GamePhase.PRE_GAME;
        ticksLeft = plugin.getConfig().getInt("pre_game_time");
        PreGameManager.startPreGame(this);
    }

    public String getMessageLine1() {
        switch (currentPhase) {
            case INACTIVE -> {
                return "§7No running game";
            }
            case PRE_GAME -> {
                return "§fGame Starts in";
            }
            case GRACE_PERIOD -> {
                return "§fPVP Enables in";
            }
            case PVP -> {
                return "§fDeathmatch in";
            }
            case DEATHMATCH -> {
                return "§fGame End in";
            }
        }
        return "§cInactive";
    }

    public String getMessageLine2() {
        if (currentPhase == GamePhase.INACTIVE) {
            return "§7-----";
        }
        int seconds = (ticksLeft+19) / 20; // rounds up
        if (seconds <= 60) {
            // flashes red and green when below 30 seconds
            return String.format("§%s%d seconds", (seconds >= 30 || seconds % 2 == 0) ? "a" : "c", seconds);
        }
        return String.format("§a%d minutes", (seconds+30) / 60);
    }

    public void extend(int seconds) {
        ticksLeft += seconds * 20;
    }

}
