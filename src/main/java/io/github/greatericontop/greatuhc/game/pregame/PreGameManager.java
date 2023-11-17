package io.github.greatericontop.greatuhc.game.pregame;

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

import io.github.greatericontop.greatuhc.GreatUHCMain;
import io.github.greatericontop.greatuhc.game.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PreGameManager {

    public enum Kit {
        ARMORER, STONE_GEAR, ECOLOGIST, ENCHANTER, ARCHER, // FIRE_LORD,
        FATE,
    }

    private final Random random;

    private final GreatUHCMain plugin;
    public PreGameManager(GreatUHCMain plugin) {
        this.plugin = plugin;
        random = new Random();
    }

    private Map<UUID, Kit> playerKits = new HashMap<>();
    public Kit getPlayerKit(UUID player) {
        return playerKits.getOrDefault(player, Kit.ARMORER);
    }
    public void setPlayerKit(UUID player, Kit kit) {
        playerKits.put(player, kit);
    }

    public void giveKitTo(Player player, boolean isEnhanced) {
        switch (getPlayerKit(player.getUniqueId())) {
            case ARMORER -> giveArmorerTo(player, isEnhanced);
            case STONE_GEAR -> giveStoneGearTo(player, isEnhanced);
            case ECOLOGIST -> giveEcologistTo(player, isEnhanced);
            case ENCHANTER -> giveEnchanterTo(player, isEnhanced);
            case ARCHER -> giveArcherTo(player, isEnhanced);
            case FATE -> giveFateTo(player, isEnhanced);
        }
    }


    private void giveArmorerTo(Player player, boolean isEnhanced) {
        ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);
        ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);
        if (isEnhanced) {
            switch (random.nextInt(4)) {
                case 0 -> leatherHelmet = new ItemStack(Material.IRON_HELMET, 1);
                case 1 -> leatherChestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
                case 2 -> leatherLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);
                case 3 -> leatherBoots = new ItemStack(Material.IRON_BOOTS, 1);
            }
        }
        leatherHelmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leatherHelmet.addEnchantment(Enchantment.DURABILITY, 1);
        leatherChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leatherChestplate.addEnchantment(Enchantment.DURABILITY, 1);
        leatherLeggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leatherLeggings.addEnchantment(Enchantment.DURABILITY, 1);
        leatherBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        leatherBoots.addEnchantment(Enchantment.DURABILITY, 1);
        player.getInventory().addItem(leatherHelmet, leatherChestplate, leatherLeggings, leatherBoots);
    }
    private void giveStoneGearTo(Player player, boolean isEnhanced) {
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        ItemStack shovel = new ItemStack(Material.STONE_SHOVEL);
        if (isEnhanced) {
            switch (random.nextInt(4)) {
                case 0 -> sword = new ItemStack(Material.IRON_SWORD);
                case 1 -> pick = new ItemStack(Material.IRON_PICKAXE);
                case 2 -> axe = new ItemStack(Material.IRON_AXE);
                case 3 -> shovel = new ItemStack(Material.IRON_SHOVEL);
            }
        }
        sword.addEnchantment(Enchantment.DURABILITY, 1);
        pick.addEnchantment(Enchantment.DIG_SPEED, 2);
        pick.addEnchantment(Enchantment.DURABILITY, 1);
        axe.addEnchantment(Enchantment.DIG_SPEED, 2);
        axe.addEnchantment(Enchantment.DURABILITY, 1);
        shovel.addEnchantment(Enchantment.DIG_SPEED, 2);
        shovel.addEnchantment(Enchantment.DURABILITY, 1);
        player.getInventory().addItem(sword, pick, axe, shovel);
    }
    private void giveEcologistTo(Player player, boolean isEnhanced) {
        ItemStack lilyPad = new ItemStack(Material.LILY_PAD, 64);
        ItemStack oakLog = new ItemStack(Material.OAK_LOG, 20);
        ItemStack apple = new ItemStack(Material.APPLE, 3);
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, 3);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        player.getInventory().addItem(lilyPad, oakLog, apple, sugarCane, stonePickaxe);
        if (isEnhanced) {
            double d = random.nextDouble();
            if (d < 0.15) { // 15%
                player.getInventory().addItem(new ItemStack(Material.CARROT, 3));
            } else if (d < 0.425) { // 27.5%
                player.getInventory().addItem(new ItemStack(Material.COW_SPAWN_EGG, 6));
            } else if (d < 0.7) { // 27.5%
                player.getInventory().addItem(new ItemStack(Material.CHICKEN_SPAWN_EGG, 6));
            } else { // 30%
                player.getInventory().addItem(new ItemStack(Material.COAL_BLOCK, 5));
            }
        }
    }
    private void giveEnchanterTo(Player player, boolean isEnhanced) {
        ItemStack leather = new ItemStack(Material.LEATHER, 6);
        ItemStack sugarCane = new ItemStack(Material.SUGAR_CANE, 12);
        ItemStack experienceBottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 30);
        ItemStack stonePickaxe = new ItemStack(Material.STONE_PICKAXE);
        stonePickaxe.addEnchantment(Enchantment.DIG_SPEED, 1);
        player.getInventory().addItem(leather, sugarCane, experienceBottle, stonePickaxe);
        if (isEnhanced) {
            switch (random.nextInt(3)) {
                case 0 -> player.getInventory().addItem(new ItemStack(Material.OBSIDIAN, 2));
                case 1 -> player.getInventory().addItem(new ItemStack(Material.BOOK, 4));
                case 2 -> {
                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
                    EnchantmentStorageMeta im = (EnchantmentStorageMeta) enchantedBook.getItemMeta();
                    Enchantment[] enchants = {
                            Enchantment.DAMAGE_ALL, Enchantment.KNOCKBACK, Enchantment.SWEEPING_EDGE,
                            Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_PROJECTILE,
                            Enchantment.ARROW_DAMAGE, Enchantment.ARROW_KNOCKBACK};
                    im.addStoredEnchant(enchants[random.nextInt(enchants.length)], 1, true);
                    enchantedBook.setItemMeta(im);
                    player.getInventory().addItem(enchantedBook);
                }
            }
        }
    }
    private void giveArcherTo(Player player, boolean isEnhanced) {
        ItemStack string = new ItemStack(Material.STRING, 3);
        ItemStack feather = new ItemStack(Material.FEATHER, 7);
        ItemStack shovel = new ItemStack(Material.STONE_SHOVEL);
        shovel.addEnchantment(Enchantment.DIG_SPEED, 1);
        player.getInventory().addItem(string, feather, shovel);
        if (isEnhanced) {
            switch (random.nextInt(3)) {
                case 0 -> player.getInventory().addItem(new ItemStack(Material.FLINT, 5));
                case 1 -> player.getInventory().addItem(new ItemStack(Material.BONE, 4));
                case 2 -> player.getInventory().addItem(new ItemStack(Material.ARROW, random.nextInt(33) + 32)); // 32-64
            }
        }
    }
    private void giveFateTo(Player player, boolean isEnhanced) {
        if (!plugin.uhcFateKit) {
            player.getInventory().addItem(new ItemStack(Material.DIRT, 1));
            player.sendMessage("§cYou get what you deserve.");
            return;
        }
        player.getInventory().addItem(
                new ItemStack(Material.MOSSY_COBBLESTONE, isEnhanced ? 126 : 63),
                new ItemStack(Material.JUKEBOX, isEnhanced ? 18 : 9),
                new ItemStack(Material.PLAYER_HEAD, isEnhanced ? 20 : 11),
                new ItemStack(Material.GOLD_INGOT, isEnhanced ? 40 : 24),
                new ItemStack(Material.APPLE, isEnhanced ? 4 : 2),
                new ItemStack(Material.OAK_PLANKS, 64),
                new ItemStack(Material.IRON_INGOT, 32),
                new ItemStack(Material.ANVIL, 1),
                new ItemStack(Material.EXPERIENCE_BOTTLE, isEnhanced ? 160 : 64),
                new ItemStack(Material.PAPER, isEnhanced ? 12 : 6)
        );
    }


    public static void startPreGame(GameManager gameManager) {
        World overworld = gameManager.getOverworld();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text("§4§lGreatUHC"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§aThe game will start in §e45 §aseconds."));
        Bukkit.broadcast(Component.text("§aChoose a kit and get ready to fight!"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        overworld.getWorldBorder().setSize(30.0);
        overworld.getWorldBorder().setCenter(2000.0, 2000.0);
        overworld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        overworld.setTime(6000L);

        // TODO: what stuff do we do in pregame vs start of grace period

        for (int x = 1985; x < 2015; x++) {
            for (int z = 1985; z < 2015; z++) {
                overworld.getBlockAt(x, 319, z).setType(Material.AIR);
                overworld.getBlockAt(x, 318, z).setType(Material.BEDROCK);
            }
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            ItemStack kitSelector = new ItemStack(Material.DIAMOND, 1);
            ItemMeta im = kitSelector.getItemMeta();
            im.setDisplayName("§6Kit Selector");
            im.setLore(List.of("Kit Selector"));
            im.addEnchant(Enchantment.LUCK, 1, true);
            kitSelector.setItemMeta(im);
            ItemStack sword = new ItemStack(Material.NETHERITE_SWORD, 1);
            ItemStack craftingTable = new ItemStack(Material.CRAFTING_TABLE, 1);
            player.getInventory().addItem(kitSelector, sword, craftingTable);

            // extra effects for pre-game fighting
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 999999, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 0));

            player.setGameMode(GameMode.SURVIVAL);
            player.undiscoverRecipes(player.getDiscoveredRecipes());
            player.chat("/uhcrecipes");
            player.teleport(new Location(overworld, 2000.0, 320.0, 2000.0));
        }

    }

}
