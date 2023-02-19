package io.github.greatericontop.greatuhc.mechanics;

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.util.TrueDamageHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WorldBorderDamage {

    public static void registerRunnable(GreatUHCMain plugin) {
        new BukkitRunnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)  continue;
                    if (!player.getWorld().getWorldBorder().isInside(player.getLocation())) {
                        player.sendActionBar("§cYou are outside the world; turn back!");
                        TrueDamageHelper.dealTrueDamage(player, 1.0);
                    }
                }
            }
        }.runTaskTimer(plugin, 200L, 15L);
    }

}
