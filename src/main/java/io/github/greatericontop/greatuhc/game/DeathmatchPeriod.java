package io.github.greatericontop.greatuhc.game;

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
    private static final double ANGLE_CONVERSION = Math.PI / 15.0; // convert number (0-29) to radians (0-2pi)
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

    private static final int DEATHMATCH_WORLD_HEIGHT = 307;
    private static final int MAX_WORLD_HEIGHT = 319;
    private static final int PYRAMID_HEIGHT = 12;
    private static final int TERRAIN_HEIGHT = 5;

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
        }.runTaskLater(gameManager.getPlugin(), 1200L); // border shrinks to +/- 10 from 14 to 4 minutes remaining

        // Setup world for deathmatch
        // OpenSimplex Noise: generate bedrock that transitions into stone
        long seedTerrain = random.nextLong();
        long seedTop = random.nextLong();
        for (int x = -90; x <= 90; x++) {
            for (int z = -90; z <= 90; z++) {
                double noise = OpenSimplex2.noise2(seedTerrain, x/35.0, z/35.0) * 0.5 + 0.5;
                // height suddenly begins to increase when past the world border (70 to 90)
                // noise gets increased (the highest point will touch world height) over the span of 20 blocks
                int extraDistance = Math.max(Math.abs(x), Math.abs(z)) - 70;
                if (extraDistance > 0) {
                    double distMul = extraDistance / 20.0;
                    // max noise: (319 - 307) / 5 = 2.4 (use 319 because it's rounded down)
                    // 1 + 0.77 + 0.63 = 2.4
                    noise += 0.77*Math.pow(distMul, 1.4) + 0.63*distMul;
                }
                int height = (int) (TERRAIN_HEIGHT * noise) + 1; // height will be from 1 to :TERRAIN_HEIGHT:
                for (int y = 0; y <= (MAX_WORLD_HEIGHT - DEATHMATCH_WORLD_HEIGHT); y++) {
                    Material mat;
                    if (y == height) {
                        // place top blocks depending on the noise value
                        // one of: mossy/cobblestone/stone; or grass; or sand/sandstone
                        double noiseTop = OpenSimplex2.noise2(seedTop, x/30.0, z/30.0) * 0.5 + 0.5;
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
                    overworld.getBlockAt(x, y + DEATHMATCH_WORLD_HEIGHT, z).setType(mat, false);
                }
            }
        }
        int eligibleAmountPlaced = 0, debugAmountDiamond = 0;
        for (int x = -PYRAMID_HEIGHT+1; x <= PYRAMID_HEIGHT-1; x++) {
            for (int z = -PYRAMID_HEIGHT+1; z <= PYRAMID_HEIGHT-1; z++) {
                int yMax = PYRAMID_HEIGHT - Math.max(Math.abs(x), Math.abs(z));
                for (int deltaY = 1; deltaY <= yMax; deltaY++) {
                    Block block = overworld.getBlockAt(x, DEATHMATCH_WORLD_HEIGHT + deltaY, z);
                    if (block.getType() != Material.BEDROCK) {
                        boolean isOre = deltaY != yMax && random.nextDouble() < 0.013;
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

        // Player initialization & spreading
        if (Bukkit.getOnlinePlayers().size() > 30) {
            Bukkit.broadcastMessage("§cToo many players! Falling back to normal spread algorithm.");
            String spreadCommand = String.format("spreadplayers 0 0 %s %s false @a",
                    20, 60);
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), spreadCommand);
        } else {
            int[] positions = GameUtils.shufflePositions(random);
            int i = 0;
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));

                int x = (int) Math.round(64.0 * Math.cos(positions[i]*ANGLE_CONVERSION));
                int z = (int) Math.round(64.0 * Math.sin(positions[i]*ANGLE_CONVERSION));
                Location top = new Location(overworld, x, overworld.getHighestBlockYAt(x, z), z);
                top.getBlock().setType(Material.QUARTZ_BLOCK);
                player.teleport(top.add(0.5, 1.0, 0.5));

                i++;
            }
        }

        GameUtils.freezePlayers(gameManager.getPlugin(), 200);
    }

}
