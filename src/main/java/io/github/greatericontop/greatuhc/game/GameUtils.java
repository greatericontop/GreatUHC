package io.github.greatericontop.greatuhc.game;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameUtils {

    public static void freezePlayers(GreatUHCMain plugin, int ticks) {
        Map<UUID, Location> locations = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            locations.put(player.getUniqueId(), player.getLocation());
        }
        new BukkitRunnable() {
            int counter = ticks / 5;
            public void run() {
                if (counter <= 0) {
                    cancel();
                    return;
                }
                counter--;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location previousLoc = locations.get(player.getUniqueId());
                    if (previousLoc == null) {
                        continue;
                    }
                    Location playerLoc = player.getLocation();
                    // only teleport when XYZ changes, allow changes to yaw/pitch
                    if (playerLoc.getX() != previousLoc.getX() || playerLoc.getY() != previousLoc.getY() || playerLoc.getZ() != previousLoc.getZ()) {
                        player.teleport(previousLoc);
                    }
                }
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }

}
