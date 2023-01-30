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

import java.util.Random;

public class DeathmatchPeriod {
    private static final int DEATHMATCH_WORLD_HEIGHT = 308;
    private static final int MAX_WORLD_HEIGHT = 319;

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        Random random = new Random();

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
        // OpenSimplex Noise: generate bedrock that transitions into stone
        long seed = random.nextLong();
        for (int x = -80; x <= 80; x++) {
            for (int z = -80; z <= 80; z++) {
                double noise = OpenSimplex2.noise2(seed, x/35.0, z/35.0);
                int height = (int) (6 * (noise * 0.5 + 0.5)); // height will be from 0 to 5
                for (int y = 0; y <= (MAX_WORLD_HEIGHT - DEATHMATCH_WORLD_HEIGHT); y++) {
                    Material mat = y <= height ? Material.BEDROCK : Material.AIR;
                    overworld.getBlockAt(x, y+DEATHMATCH_WORLD_HEIGHT, z).setType(mat, false);
                }
            }
        }
        for (int x = -8; x <= 8; x++) {
            for (int z = -8; z <= 8; z++) {
                int yMax = 9 - Math.max(Math.abs(x), Math.abs(z));
                for (int deltaY = 1; deltaY <= yMax; deltaY++) {
                    Block block = overworld.getBlockAt(x, DEATHMATCH_WORLD_HEIGHT + deltaY, z);
                    if (block.getType() == Material.AIR) {
                        boolean isOre = deltaY != yMax && random.nextDouble() < 0.0175;
                        block.setType(isOre ? Material.DIAMOND_ORE : Material.SMOOTH_SANDSTONE, false);
                    }
                }
            }
        }
        Block chestBlock = overworld.getBlockAt(0, DEATHMATCH_WORLD_HEIGHT + 10, 0);
        chestBlock.setType(Material.CHEST, false);
        Chest chest = (Chest) chestBlock.getState(); // org.bukkit.block.Chest
        Inventory inv = chest.getInventory();
        inv.setItem(random.nextInt(27), new ItemStack(Material.GOLDEN_APPLE, 1));

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
