package io.github.greatericontop.greatuhc.game;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DeathmatchPeriod {
    private static final int[] dx = {2, -2, 0, 0};
    private static final int[] dz = {0, 0, 2, -2};
    private static final BlockFace[] faces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH};
    private static final Material[] topBlocks = {Material.STONE, Material.STONE, Material.STONE, Material.COBBLESTONE};

    private static final int DEATHMATCH_WORLD_HEIGHT = 308;
    private static final int MAX_WORLD_HEIGHT = 319;
    private static final int PYRAMID_HEIGHT = 9;

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
                int height = (int) (6 * (noise * 0.5 + 0.5)) + 1; // height will be from 1 to 6
                                                                  // (bedrock layer 0 to 5)
                for (int y = 0; y <= (MAX_WORLD_HEIGHT - DEATHMATCH_WORLD_HEIGHT); y++) {
                    Material mat;
                    if (y == height) {
                        mat = topBlocks[random.nextInt(topBlocks.length)];
                    } else {
                        mat = y <= height ? Material.BEDROCK : Material.AIR;
                    }
                    overworld.getBlockAt(x, y + DEATHMATCH_WORLD_HEIGHT, z).setType(mat, false);
                }
            }
        }
        for (int x = -PYRAMID_HEIGHT+1; x <= PYRAMID_HEIGHT-1; x++) {
            for (int z = -PYRAMID_HEIGHT+1; z <= PYRAMID_HEIGHT-1; z++) {
                int yMax = PYRAMID_HEIGHT - Math.max(Math.abs(x), Math.abs(z));
                for (int deltaY = 1; deltaY <= yMax; deltaY++) {
                    Block block = overworld.getBlockAt(x, DEATHMATCH_WORLD_HEIGHT + deltaY, z);
                    if (block.getType() == Material.AIR) {
                        boolean isOre = deltaY != yMax && random.nextDouble() < 0.015;
                        block.setType(isOre ? Material.DIAMOND_ORE : Material.SMOOTH_SANDSTONE, false);
                    }
                }
            }
        }
        // Middle Chests
        int chestWithGoldApple = random.nextInt(4);
        for (int chestNum = 0; chestNum < 4; chestNum++) {
            Block chestBlock = overworld.getBlockAt(dx[chestNum], DEATHMATCH_WORLD_HEIGHT + PYRAMID_HEIGHT - 1, dz[chestNum]);
            chestBlock.setType(Material.CHEST, false);
            org.bukkit.block.Chest chest = (org.bukkit.block.Chest) chestBlock.getState();
            org.bukkit.block.data.type.Chest chestData = (org.bukkit.block.data.type.Chest) chestBlock.getBlockData();
            chestData.setFacing(faces[chestNum]);
            chestBlock.setBlockData(chestData);
            Inventory inv = chest.getInventory();
            int[] chestItems = GameUtils.shuffleChest(random);
            if (chestNum == chestWithGoldApple) {
                inv.setItem(chestItems[0], new ItemStack(Material.GOLDEN_APPLE, 1));
            }
            inv.setItem(chestItems[1], new ItemStack(Material.APPLE, 1));
            inv.setItem(chestItems[2], new ItemStack(Material.OAK_LOG, 8));
            inv.setItem(chestItems[3], new ItemStack(Material.COAL, 1));
            inv.setItem(chestItems[4], new ItemStack(Material.STICK, 1));
            inv.setItem(chestItems[5], new ItemStack(Material.ARROW, 12));
            inv.setItem(chestItems[6], new ItemStack(Material.ARROW, 12));
        }

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
