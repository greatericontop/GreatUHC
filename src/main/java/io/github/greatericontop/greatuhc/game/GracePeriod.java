package io.github.greatericontop.greatuhc.game;

import io.github.greatericontop.greatuhc.customitems.Crafts;
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
        overworld.getWorldBorder().setSize(1400.0);
        overworld.getWorldBorder().setCenter(0.0, 0.0);
        overworld.getWorldBorder().setDamageAmount(0.25);
        overworld.getWorldBorder().setDamageBuffer(1.0);
        nether.getWorldBorder().setSize(350.0);
        nether.getWorldBorder().setCenter(0.0, 0.0);
        nether.getWorldBorder().setDamageAmount(0.25);
        nether.getWorldBorder().setDamageBuffer(1.0);

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

            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18_000, 0));
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 18_000, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 4));

            gameManager.getPreGameManager().giveKitTo(player, false);
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
