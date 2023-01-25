package io.github.greatericontop.greatuhc;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
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
            int borderRadius = (int) (world.getWorldBorder().getSize() / 2);
            if (player != null) {
                int playerRadius = (int) Math.max(Math.abs(player.getLocation().getX()), Math.abs(player.getLocation().getZ()));
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
            int count = 0;
            // Players alive in this case means the number of non-spectator players
            for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
                if (p1.getGameMode() != GameMode.SPECTATOR) {
                    count++;
                }
            }
            return String.valueOf(count);
        }

        return null;
    }

}
