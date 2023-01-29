package io.github.greatericontop.greatuhc.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class DeathmatchPeriod {
    private static final int DEATHMATCH_WORLD_HEIGHT = 311;

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§bDeathmatch has started!"));
        Bukkit.broadcast(Component.text("§eFight to the death!"));
        Bukkit.broadcast(Component.text("§7The game will end in 15 minutes."));
        Bukkit.broadcast(Component.text("§7You'll be able to move in 10 seconds."));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        // Re-enable day-night cycle
        overworld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);

        // Borders
        overworld.getWorldBorder().setSize(140.0);
        new BukkitRunnable() {
            public void run() {
                overworld.getWorldBorder().setSize(20.0, 600L);
            }
        }.runTaskLater(gameManager.getPlugin(), 1200L);

        // Setup world for deathmatch
        for (int x = -80; x <= 80; x++) {
            for (int z = -80; z <= 80; z++) {
                overworld.getBlockAt(x, DEATHMATCH_WORLD_HEIGHT, z).setType(Material.BEDROCK, false);
            }
        }
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                int yMax = 5 - Math.max(Math.abs(x), Math.abs(z));
                for (int deltaY = 1; deltaY <= yMax; deltaY++) {
                    overworld.getBlockAt(x, DEATHMATCH_WORLD_HEIGHT + deltaY, z).setType(Material.SMOOTH_SANDSTONE, false);
                }
            }
        }
        Block chestBlock = overworld.getBlockAt(0, DEATHMATCH_WORLD_HEIGHT + 6, 0);
        chestBlock.setType(Material.CHEST, false);
        Chest chest = (Chest) chestBlock.getState(); // org.bukkit.block.Chest
        Inventory inv = chest.getInventory();
        inv.setItem(ThreadLocalRandom.current().nextInt(0, 27), new ItemStack(Material.GOLDEN_APPLE, 1));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            // Spreading
            player.teleport(new Location(overworld, 0.0, 350.0, 0.0));
        }

        String spreadCommand = String.format("spreadplayers 0 0 %s %s false @a",
                20, 60);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), spreadCommand);

        GameUtils.freezePlayers(gameManager.getPlugin(), 200);
    }

}
