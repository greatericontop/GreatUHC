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

import io.github.greatericontop.greatuhc.customitems.Crafts;
import io.github.greatericontop.greatuhc.util.GameUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GracePeriod {

    public static void start(GameManager gameManager) {
        World overworld = gameManager.getOverworld();
        World nether = gameManager.getNether();

        Bukkit.broadcast(Component.text("§9------------------------------"));
        Bukkit.broadcast(Component.text("§4§lGreatUHC"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§aThe grace period has started!"));
        Bukkit.broadcast(Component.text("§aYou have 15 minutes to gather resources!"));
        Bukkit.broadcast(Component.text(""));
        Bukkit.broadcast(Component.text("§9------------------------------"));

        // Initialization stuff
        overworld.setGameRule(GameRule.NATURAL_REGENERATION, false);
        nether.setGameRule(GameRule.NATURAL_REGENERATION, false);
        overworld.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        overworld.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        nether.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);

        // Borders
        overworld.getWorldBorder().setSize(gameManager.getPlugin().getConfig().getDouble("overworld_main_border_start"));
        overworld.getWorldBorder().setCenter(0.0, 0.0);
        overworld.getWorldBorder().setDamageAmount(0.0);
        nether.getWorldBorder().setSize(gameManager.getPlugin().getConfig().getDouble("nether_main_border_start"));
        nether.getWorldBorder().setCenter(0.0, 0.0);
        nether.getWorldBorder().setDamageAmount(0.0);

        // Modifiers
        ItemStack randomUltimate = null;
        if (gameManager.getPlugin().uhcRandomUltimate) {
            randomUltimate = Crafts.getRandomUltimate();
        }
        ItemStack startingHeads = null;
        if (gameManager.getPlugin().uhcStartingHeads) {
            startingHeads = new ItemStack(Material.PLAYER_HEAD, 3);
            SkullMeta im = (SkullMeta) startingHeads.getItemMeta();
            im.setOwningPlayer(Bukkit.getOfflinePlayer("MHF_Villager"));
            im.setDisplayName("§eStarting Head");
            startingHeads.setItemMeta(im);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40.0);
            if (gameManager.getPlugin().uhcFastReflexes) {
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(99.0);
            } else {
                player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0);
            }

            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 18_000, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 4));

            gameManager.getPreGameManager().giveKitTo(player, true);
            if (randomUltimate != null) {
                player.sendMessage("§bYou have been given a random ultimate!");
                player.getInventory().addItem(randomUltimate);
            }
            if (startingHeads != null) {
                player.sendMessage("§bYou have been given starting heads!");
                player.getInventory().addItem(startingHeads);
            }
        }


        // We just use the command here. There's no real reason for implementing it manually.
        String spreadCommand = String.format("spreadplayers 0 0 %s %s false @a",
                150, 650);
        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), spreadCommand);

        // Don't let players move for 10 seconds (slowness doesn't work due to the momentum gained by jumping)
        GameUtils.freezePlayers(gameManager.getPlugin(), 200);
    }

}
