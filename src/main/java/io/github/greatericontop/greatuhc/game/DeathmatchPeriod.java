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

import io.github.greatericontop.greatuhc.util.GameUtils;
import io.github.greatericontop.greatuhc.util.OpenSimplex2;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
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
    private static final double ANGLE_CONVERSION = Math.PI / 16.0; // convert number (0-31) to radians (0-2pi)
    private static final BlockFace[] faces = {BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH, BlockFace.NORTH};
    private static final Material[] stoneTopMaterials = {
            Material.STONE, Material.STONE, Material.STONE, Material.STONE, Material.STONE,
            Material.MOSSY_COBBLESTONE, Material.COBBLESTONE, Material.COBBLESTONE, Material.COBBLESTONE,
            Material.STONE_BRICKS
    };
    private static final Material[] sandTopMaterials = {
            Material.SANDSTONE, Material.SANDSTONE, Material.SANDSTONE, Material.SANDSTONE,
            Material.SAND, Material.SAND, Material.SAND, Material.SAND, Material.SAND,
            Material.RED_SAND, Material.RED_SANDSTONE,
    };

    private static final int XZ_OFFSET = 2000; // (0, 0) of deathmatch arena is (2000, 2000) in the overworld
    private static final int MAX_WORLD_HEIGHT = 319;

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        Random random = new Random();
        FileConfiguration config = gameManager.getPlugin().getConfig();

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
        overworld.getWorldBorder().setCenter(XZ_OFFSET, XZ_OFFSET);
        overworld.getWorldBorder().setSize(config.getDouble("deathmatch_border_start"));
        new BukkitRunnable() {
            public void run() {
                overworld.getWorldBorder().setSize(
                        config.getDouble("deathmatch_border_end"),
                        config.getLong("deathmatch_border_shrink_time"));
            }
        }.runTaskLater(gameManager.getPlugin(), config.getLong("deathmatch_border_shrink_after"));

        // Setup world for deathmatch
        // OpenSimplex Noise: generate bedrock that transitions into stone
        // (config values)
        int RADIUS = config.getInt("deathmatch_gen.radius");
        double XZ_SCALE = config.getDouble("deathmatch_gen.xz_noise_scale");
        int EXTRA_DISTANCE_THRESHOLD = config.getInt("deathmatch_gen.extra_distance_threshold");
        double BLOCK_SEL_SCALE = config.getDouble("deathmatch_gen.block_selection_noise_scale");
        double DIAMOND_CHANCE = config.getDouble("deathmatch_gen.diamond_chance");
        int DEATHMATCH_WORLD_HEIGHT = config.getInt("deathmatch_gen.deathmatch_world_height");
        int PYRAMID_HEIGHT = config.getInt("deathmatch_gen.pyramid_height");
        int TERRAIN_HEIGHT = config.getInt("deathmatch_gen.terrain_height");
        double EXTRA_A = config.getDouble("deathmatch_gen.extra_a");
        double EXTRA_B = config.getDouble("deathmatch_gen.extra_b");
        double EXTRA_DISTANCE_SCALE = RADIUS - EXTRA_DISTANCE_THRESHOLD;
        // (end config values)
        long seedTerrain = random.nextLong();
        long seedTop = random.nextLong();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                double noise = OpenSimplex2.noise2(seedTerrain, x/XZ_SCALE, z/XZ_SCALE) * 0.5 + 0.5;
                // height suddenly begins to increase when past the world border (70 to 90 by default)
                // noise gets increased (the highest point will touch world height) over the span of 20 blocks by default
                int extraDistance = Math.max(Math.abs(x), Math.abs(z)) - EXTRA_DISTANCE_THRESHOLD;
                if (extraDistance > 0) {
                    double distMul = extraDistance / EXTRA_DISTANCE_SCALE;
                    // max noise: (319 - 307) / 5 = 2.4 (use 319 because it's rounded down)
                    // 1 + 0.77 + 0.63 = 2.4
                    noise += EXTRA_A*Math.pow(distMul, 1.4) + EXTRA_B*distMul;
                }
                int height = (int) (TERRAIN_HEIGHT * noise) + 1; // height will be from 1 to :TERRAIN_HEIGHT:
                for (int y = 0; y <= (MAX_WORLD_HEIGHT - DEATHMATCH_WORLD_HEIGHT); y++) {
                    Material mat;
                    if (y == height) {
                        // place top blocks depending on the noise value
                        // one of: mossy/cobblestone/stone; or grass; or sand/sandstone
                        double noiseTop = OpenSimplex2.noise2(seedTop, x/BLOCK_SEL_SCALE, z/BLOCK_SEL_SCALE) * 0.5 + 0.5;
                        if (noiseTop < 0.4 || (Math.random() < 0.5 && noiseTop < 0.5)) { // 45% (+/- 5%)
                            mat = stoneTopMaterials[random.nextInt(stoneTopMaterials.length)];
                        } else if (noiseTop < 0.75 || (Math.random() < 0.5 && noiseTop < 0.85)) { // 35%, appears between the other 2
                            mat = Material.GRASS_BLOCK;
                        } else { // 20%
                            mat = sandTopMaterials[random.nextInt(sandTopMaterials.length)];
                        }
                    } else {
                        mat = y <= height ? Material.BEDROCK : Material.AIR;
                    }
                    overworld.getBlockAt(x + XZ_OFFSET, y + DEATHMATCH_WORLD_HEIGHT, z + XZ_OFFSET).setType(mat, false);
                }
            }
        }
        int eligibleAmountPlaced = 0, debugAmountDiamond = 0;
        for (int x = -PYRAMID_HEIGHT+1; x <= PYRAMID_HEIGHT-1; x++) {
            for (int z = -PYRAMID_HEIGHT+1; z <= PYRAMID_HEIGHT-1; z++) {
                int yMax = PYRAMID_HEIGHT - Math.max(Math.abs(x), Math.abs(z));
                for (int deltaY = 1; deltaY <= yMax; deltaY++) {
                    Block block = overworld.getBlockAt(x + XZ_OFFSET, DEATHMATCH_WORLD_HEIGHT + deltaY, z + XZ_OFFSET);
                    if (block.getType() != Material.BEDROCK) {
                        boolean isOre = deltaY != yMax && random.nextDouble() < DIAMOND_CHANCE;
                        block.setType(isOre ? Material.DIAMOND_ORE : Material.SMOOTH_SANDSTONE, false);
                        debugAmountDiamond += isOre ? 1 : 0;
                        eligibleAmountPlaced += deltaY != yMax ? 1 : 0;
                    }
                }
            }
        }
        Bukkit.broadcastMessage(String.format("§7Placed §e%d§7, §b%d §7diamonds, expectation: §6%.1f", eligibleAmountPlaced, debugAmountDiamond, eligibleAmountPlaced*0.014));
        // Middle Chests
        int chestWithGoldApple = random.nextInt(4);
        for (int chestNum = 0; chestNum < 4; chestNum++) {
            Block chestBlock = overworld.getBlockAt(dx[chestNum] + XZ_OFFSET, DEATHMATCH_WORLD_HEIGHT + PYRAMID_HEIGHT - 1, dz[chestNum] + XZ_OFFSET);
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

        // Player initialization & spreading
        double SPREAD_RADIUS = config.getDouble("deathmatch_spread_radius");
        if (Bukkit.getOnlinePlayers().size() > 32) {
            Bukkit.broadcastMessage("§cToo many players! Falling back to normal spread algorithm.");
            String spreadCommand = String.format("spreadplayers %d %d %s %s false @a",
                    XZ_OFFSET, XZ_OFFSET, 8, (int) SPREAD_RADIUS);
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), spreadCommand);
        } else {
            int[] positions = GameUtils.shufflePositions(random);
            int i = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));

                int x = (int) Math.round(SPREAD_RADIUS * Math.cos(positions[i]*ANGLE_CONVERSION));
                int z = (int) Math.round(SPREAD_RADIUS * Math.sin(positions[i]*ANGLE_CONVERSION));
                Location top = new Location(overworld, x + XZ_OFFSET, overworld.getHighestBlockYAt(x + XZ_OFFSET, z + XZ_OFFSET), z + XZ_OFFSET);
                top.getBlock().setType(Material.QUARTZ_BLOCK);
                player.teleport(top.add(0.5, 1.0, 0.5));

                i++;
            }
        }

        GameUtils.freezePlayers(gameManager.getPlugin(), 200);
    }

}
