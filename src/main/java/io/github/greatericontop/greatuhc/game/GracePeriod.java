package io.github.greatericontop.greatuhc.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GracePeriod {

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        World nether = gameManager.getNether();

        // Initialization stuff
        overworld.setGameRule(GameRule.NATURAL_REGENERATION, false);
        nether.setGameRule(GameRule.NATURAL_REGENERATION, false);
        overworld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        overworld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        overworld.setTime(6000L);

        // Borders
        overworld.getWorldBorder().setSize(1400.0);
        overworld.getWorldBorder().setCenter(0.0, 0.0);
        overworld.getWorldBorder().setDamageAmount(0.25);
        overworld.getWorldBorder().setDamageBuffer(0.0);
        nether.getWorldBorder().setSize(350.0);
        nether.getWorldBorder().setCenter(0.0, 0.0);
        nether.getWorldBorder().setDamageAmount(0.25);
        nether.getWorldBorder().setDamageBuffer(0.0);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SURVIVAL);
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);

            // Add grace period effects
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 18_000, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 6));

            // TODO: add fully customizable kits
            ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
            stoneSword.addEnchantment(Enchantment.DURABILITY, 1);
            ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
            stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
            stonePickaxe.addEnchantment(Enchantment.DURABILITY, 1);
            ItemStack stoneAxe = new ItemStack(Material.STONE_AXE);
            stoneAxe.addEnchantment(Enchantment.DIG_SPEED, 1);
            stoneAxe.addEnchantment(Enchantment.DURABILITY, 1);
            ItemStack stoneShovel = new ItemStack(Material.STONE_SHOVEL);
            stoneShovel.addEnchantment(Enchantment.DIG_SPEED, 1);
            stoneShovel.addEnchantment(Enchantment.DURABILITY, 1);
            player.getInventory().addItem(stoneSword, stonePickaxe, stoneAxe, stoneShovel);

            // Teleport them to the overworld to prepare for spreading them.
            player.teleport(overworld.getSpawnLocation());
        }


        // We just use the command here. There's no real reason for implementing it manually.
        String spreadCommand = String.format("spreadplayers 0 0 %s %s false @a",
                150, 650);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), spreadCommand);

        // Don't let players move for 10 seconds (slowness doesn't work due to the momentum gained by jumping)
        Map<UUID, Location> locations = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            locations.put(player.getUniqueId(), player.getLocation());
        }
        new BukkitRunnable() {
            int counter = 20;
            public void run() {
                if (counter <= 0) {
                    cancel();
                    return;
                }
                counter--;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Location previousLoc = locations.get(player.getUniqueId());
                    if (previousLoc != null) {
                        player.teleport(previousLoc);
                    }
                }
            }
        }.runTaskTimer(gameManager.getPlugin(), 10L, 10L);
    }

}
