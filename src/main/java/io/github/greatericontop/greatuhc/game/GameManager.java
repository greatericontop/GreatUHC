package io.github.greatericontop.greatuhc.game;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.game.pregame.PreGameManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {
    public static final boolean SHORT_GAMES = true;
    public static final int PRE_GAME_TIME = 900; // 45 seconds
    public static final int GRACE_TIME = 18_000; // 15 minutes
    public static final int PVP_TIME = 30_000; // 25 minutes
    public static final int DEATHMATCH_TIME = 18_000; // 15 minutes

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

        overworld = Bukkit.getWorld("world");
        nether = Bukkit.getWorld("world_nether");
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
                            ticksLeft = SHORT_GAMES ? 300 : GRACE_TIME;
                            GracePeriod.start(GameManager.this);
                        }
                        case GRACE_PERIOD -> {
                            currentPhase = GamePhase.PVP;
                            ticksLeft = SHORT_GAMES ? 1200 : PVP_TIME;
                            PVPPeriod.start(GameManager.this);
                        }
                        case PVP -> {
                            currentPhase = GamePhase.DEATHMATCH;
                            ticksLeft = DEATHMATCH_TIME;
                            DeathmatchPeriod.start(GameManager.this);
                        }
                        case DEATHMATCH -> {
                            currentPhase = GamePhase.INACTIVE;
                            ticksLeft = 0;
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

    public void start() {
        currentPhase = GamePhase.PRE_GAME;
        ticksLeft = SHORT_GAMES ? 100 : PRE_GAME_TIME;
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

    public void extendOneMinute() {
        ticksLeft += 1200;
    }

}
